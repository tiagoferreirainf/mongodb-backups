package tf.project.mongodump.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.model.Command;
import tf.project.mongodump.model.CommandDTO;
import tf.project.mongodump.process.RunProcess;
import tf.project.mongodump.utils.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Component
public class CommandHandler {

    @Value("${mongodumper.mongodb.uri}")
    private String uri;

    @Value("${mongodumper.archive.path}")
    private String archivePath;

    private static final String GZIP_OPTION = "--gzip";
    private static final String OUT_OPTION = "--out=%s";
    private static final String URI_OPTION = "--uri=%s";

    private void backupMongoDBData(CommandDTO commandDTO) throws MalformedURLException {
        log.debug(commandDTO.toString());

        StringBuilder builder = new StringBuilder("%s").append(" ")
                .append(OUT_OPTION).append(" ").append(URI_OPTION);

        if(commandDTO.getEnableGZIP()){
            builder.append(GZIP_OPTION);
        }

        if(Strings.isNotBlank(commandDTO.getOptions())){
            builder.append(commandDTO.getOptions());
        }

        String tempCommand = builder.toString();

        String action = Command.MONGODUMP.getValue();
        String path = StringUtil.getFileName(commandDTO.getArchivePath());
        String command = String.format(tempCommand, action, path, uri);
        RunProcess.executeCommand(command);
    }

}
