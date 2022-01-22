package tf.project.mongodump.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.model.*;
import tf.project.mongodump.model.dto.Backup;
import tf.project.mongodump.model.dto.BackupsDTO;
import tf.project.mongodump.model.dto.CommandDTO;
import tf.project.mongodump.process.RunProcess;
import tf.project.mongodump.utils.StringUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommandHandler {

    @Value("${mongodumper.mongodb.uri}")
    private String uri;

    @Value("${mongodumper.defaults.backups.path}")
    private String archivePath;

    private static final String GZIP_OPTION = "--gzip";
    private static final String OUT_OPTION = "--out=%s";
    private static final String URI_OPTION = "--uri=%s";

    public String backupMongoDBData(CommandDTO commandDTO) {
        log.debug(commandDTO.toString());

        final ApiCommand apiCommand = ApiCommand.builder()
                .overridePath(commandDTO.getOverridePath())
                .overrideURI(commandDTO.getOverrideMongoUri())
                .useArchives(commandDTO.getUseArchive())
                .useGZIP(commandDTO.getEnableGZIP())
                .useOut(commandDTO.getUseOut())
                .additionalOptions(commandDTO.getOptions())
                .build();

        apiCommand.setUri(uri);
        apiCommand.setBackupPath(archivePath);

        String command = apiCommand.getCommand(MongoCommand.MONGODUMP);
        String generatedFile = apiCommand.getFinalPath();
        RunProcess.executeCommand(command);

        return generatedFile;
    }

    public BackupsDTO listMongoDBBackups() {
        final String message = RunProcess.executeCommand("ls " + archivePath);
        final List<String> archiveList = StringUtil.parseLsMessage(message, true);
        List<Backup> backupList = new ArrayList<>();

        for(String archive : archiveList){
            backupList.add(new Backup(archive, StringUtil.convertToDate(archive)));
        }

        return BackupsDTO.builder().backupList(backupList).backupPath(archivePath).build();
    }

    public byte[] downloadBackup(String id) throws IOException {
        Path path = Paths.get(archivePath + "/" + id);
        return Files.readAllBytes(path);
    }

    public void deleteBackup(String id) {
        RunProcess.executeCommand("rm -f " + archivePath + "/" + id);
    }

    public void deleteAllBackup() {
        RunProcess.executeCommand("rm -rf " + archivePath);
    }
}
