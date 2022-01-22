package tf.project.mongodump.utils;

import org.apache.logging.log4j.util.Strings;

import java.sql.Timestamp;

public class StringUtil {

    private static final String PREFIX = "archive_";
    private static final String DEFAULT_ARCHIVE_PATH = "./mongobackups/";

    public static String getFileName(String archivePath){
        String path = Strings.isBlank(archivePath) ? DEFAULT_ARCHIVE_PATH : archivePath;
        final long time = new Timestamp(System.currentTimeMillis()).getTime();

        return  path + PREFIX + time;
    }
}
