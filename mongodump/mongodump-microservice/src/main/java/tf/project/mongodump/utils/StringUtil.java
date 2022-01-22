package tf.project.mongodump.utils;

import org.apache.logging.log4j.util.Strings;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    private static final String PREFIX = "";
    private static final String SUFIX = ".archive";
    private static final String DEFAULT_ARCHIVE_PATH = "./mongobackups/";

    public static String getFileName(String archivePath) {
        String path = Strings.isBlank(archivePath) ? DEFAULT_ARCHIVE_PATH : archivePath;
        final long time = new Timestamp(System.currentTimeMillis()).getTime();

        return path + PREFIX + time;
    }

    public static List<String> parseLsMessage(String message, boolean filter) {
        String[] entryArray = message.split("\n");
        return Arrays.stream(entryArray).filter(entry -> entry.contains(SUFIX)).collect(Collectors.toList());
    }

    public static String convertToDate(String name){
        final int index = name.indexOf(SUFIX);
        final String substring = name.substring(0, index);
        long time = Long.parseLong(substring);
        Timestamp timestamp = new Timestamp(time);
        return timestamp.toString();
    }

}
