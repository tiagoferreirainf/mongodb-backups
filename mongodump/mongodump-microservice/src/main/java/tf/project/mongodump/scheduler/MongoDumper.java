package tf.project.mongodump.scheduler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.model.MongoCommand;
import tf.project.mongodump.model.SchedulerCommand;
import tf.project.mongodump.process.RunProcess;

@Slf4j
@Component
public class MongoDumper implements Job{

    private static final MongoCommand action = MongoCommand.MONGODUMP;

    @Value("${mongodumper.scheduler.custom.command}")
    private String customCommand;

    @Autowired
    private SchedulerCommand schedulerCommand;

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("JobScheduler initiating new execution");
        String command = Strings.isBlank(customCommand) ? schedulerCommand.build() : customCommand;
        RunProcess.executeCommand(command);
    }
}
