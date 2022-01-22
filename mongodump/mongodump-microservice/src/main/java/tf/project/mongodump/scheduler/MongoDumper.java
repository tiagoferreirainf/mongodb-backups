package tf.project.mongodump.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.model.Command;
import tf.project.mongodump.process.RunProcess;
import tf.project.mongodump.utils.StringUtil;

import java.net.URL;
import java.sql.Timestamp;

@Slf4j
@Component
public class MongoDumper implements Job{

    private static final Command action = Command.MONGODUMP;

    @Value("${mongodumper.custom.command}")
    private String customCommand;

    @Value("${mongodumper.archive.path}")
    private String archivePath;

    @Value("${mongodumper.mongodb.uri}")
    private String uri;

    private static final String DEFAULT_COMMAND = "%s --out=%s --uri=%s --gzip";

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("JobScheduler initiating");
        String archive = StringUtil.getFileName(archivePath);
        log.debug("JobScheduler properties: \n\t uri: {} \n\t archive: {} \n\t custom command: {}", uri, archive, customCommand);

        String command = Strings.isBlank(customCommand) ? String.format(DEFAULT_COMMAND, action.getValue(), archive, uri) : customCommand;
        RunProcess.executeCommand(command);
    }
}
