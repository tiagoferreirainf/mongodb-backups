package tf.project.mongodump.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("${mongodumper.scheduler.enabled:true}")
@Slf4j
public class MongoDumperConfiguration {

    private static final String identity = "mongodumper";

    private static final String DEFAULT_CRON_EXPRESSION = "0 0 12 * * ?";

    @Value("${mongodumper.scheduler.cronExpression}")
    private String cronExpression;

    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(MongoDumper.class).withIdentity(identity).storeDurably().build();
    }

    @Bean
    public Trigger jobATrigger(JobDetail jobADetails) {
        String expression = Strings.isBlank(cronExpression) ? DEFAULT_CRON_EXPRESSION : cronExpression;
        log.debug("Enabling Job Execution with identity {} and cron expression {}", identity, expression);

        return TriggerBuilder.newTrigger()
                .forJob(jobADetails)
                .withIdentity(identity)
                .withSchedule(CronScheduleBuilder.cronSchedule(expression.trim()))
                .build();
    }
}
