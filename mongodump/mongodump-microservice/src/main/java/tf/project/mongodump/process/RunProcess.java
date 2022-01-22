package tf.project.mongodump.process;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import tf.project.mongodump.exceptions.MongoDumperException;

import java.io.InputStream;

@Slf4j
public class RunProcess {

    public static void executeCommand(String command) throws RuntimeException{
        try {
            if(Strings.isBlank(command)){
                log.error("Executing process command failed, since is empty");
            }
            log.debug("command to be executed: {}", command);

            Process runtimeProcess = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            int exitValue = runtimeProcess.waitFor();

            if (exitValue != 0) {
                InputStream error = runtimeProcess.getErrorStream();
                String errorMessage = IOUtils.toString(error, "UTF-8");
                throw new MongoDumperException(errorMessage);
            }

            InputStream message = runtimeProcess.getInputStream();
            log.debug("Command output message: {}", IOUtils.toString(message, "UTF-8"));
        } catch (MongoDumperException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
