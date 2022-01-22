package tf.project.mongodump.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Command {
    @Value("${mongodumper.defaults.backups.path}")
    protected String backupPath;

    @Value("${mongodumper.mongodb.uri}")
    protected String uri;

    protected MongoCommand action;

    protected static final String GZIP_OPTION = "--gzip";
    protected static final String OUT_OPTION = "--out=%s";
    protected static final String URI_OPTION = "--uri=%s";
    protected static final String ARCHIVE_OPTION = "--archive=%s";
    protected static final String ARCHIVE_EXTENSION = ".archive";
}
