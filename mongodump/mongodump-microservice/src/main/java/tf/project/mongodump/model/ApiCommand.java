package tf.project.mongodump.model;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tf.project.mongodump.utils.StringUtil;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApiCommand extends Command {
    private Boolean useArchives;

    private Boolean useGZIP;

    private Boolean useOut;

    private String additionalOptions;

    private String overridePath;

    private String overrideURI;

    private String finalPath;

    @Override
    public void setUri(String uri) {
        super.setUri(uri);
    }

    @Override
    public void setBackupPath(String path) {
        super.setBackupPath(path);
    }

    public String getCommand(MongoCommand mongoCommand) {
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
            commandBuilder.append(OUT_OPTION).append(" ");
            ;
        }

        if (Strings.isNotBlank(this.additionalOptions)) {
            commandBuilder.append(additionalOptions);
        }

        log.debug("SchedulerCommand: {}", commandBuilder.toString());
        String action = mongoCommand.getValue();
        this.finalPath = Strings.isBlank(overridePath) ? pathBuilder.toString() : overridePath;
        String finalUri = Strings.isBlank(overrideURI) ? uri : overrideURI;

        final String finalCommand = String.format(commandBuilder.toString(), action, finalUri, finalPath);
        log.debug("SchedulerCommand final: {}", finalCommand);
        return finalCommand;
    }

}
