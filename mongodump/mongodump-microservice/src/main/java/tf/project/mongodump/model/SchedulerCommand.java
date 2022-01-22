package tf.project.mongodump.model;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.utils.StringUtil;

@Component
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchedulerCommand extends Command {
    @Value("${mongodumper.scheduler.options.enabled.archive}")
    private Boolean useArchives;

    @Value("${mongodumper.scheduler.options.enabled.gzip}")
    private Boolean useGZIP;

    @Value("${mongodumper.scheduler.options.enabled.out}")
    private Boolean useOut;

    @Value("${mongodumper.scheduler.options.additional}")
    private String additionalOptions;

    public String build() {
        log.debug("SchedulerCommand properties:  \n {}", this.toString());

        //command format ex: "mongodump --uri=X --gzip --archive=X|--out=X --OTHER"
        StringBuilder pathBuilder = new StringBuilder(StringUtil.getFileName(backupPath));
        StringBuilder commandBuilder = new StringBuilder("%s ").append(URI_OPTION).append(" ");

        if (this.useGZIP) {
            commandBuilder.append(GZIP_OPTION).append(" ");
        }

        if (this.useArchives) {
            pathBuilder.append(ARCHIVE_EXTENSION);
            commandBuilder.append(ARCHIVE_OPTION).append(" ");
        } else if (this.useOut) {
            commandBuilder.append(OUT_OPTION).append(" ");;
        }

        if (Strings.isNotBlank(this.additionalOptions)) {
            commandBuilder.append(additionalOptions);
        }

        log.debug("SchedulerCommand: {}",commandBuilder.toString());
        String action = MongoCommand.MONGODUMP.getValue();
        final String finalCommand = String.format(commandBuilder.toString(), action, uri, pathBuilder.toString());
        log.debug("SchedulerCommand final: {}", finalCommand);
        return finalCommand;
    }

}
