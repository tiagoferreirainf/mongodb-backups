package tf.project.mongodump.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tf.project.mongodump.handler.CommandHandler;
import tf.project.mongodump.model.dto.BackupsDTO;
import tf.project.mongodump.model.dto.CommandDTO;
import org.springframework.lang.NonNull;

import java.io.IOException;


@RestController
@Slf4j
@RequestMapping(value = "/")
public class MongoDBRestController {

    @Autowired
    private CommandHandler executeCommand;

    @RequestMapping(value = "backup/generate", method = RequestMethod.POST)
    public ResponseEntity<String> generateNewBackup(@RequestBody @NonNull CommandDTO commandDTO) {
        return new ResponseEntity<>(this.executeCommand.backupMongoDBData(commandDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "backup/list", method = RequestMethod.GET)
    public ResponseEntity<BackupsDTO> generateNewBackup() {
        return new ResponseEntity<>(this.executeCommand.listMongoDBBackups(), HttpStatus.OK);
    }

    @RequestMapping(value = "backup/download/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadBackup(@PathVariable("id") @NonNull String id) throws IOException {

        final byte[] file = this.executeCommand.downloadBackup(id);
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(file.length);
        respHeaders.setContentType(new MediaType("text", "archive"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + id);

        return new ResponseEntity<>(file, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "backup/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBackup(@PathVariable("id") @NonNull String id) {
        this.executeCommand.deleteBackup(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "backup/delete/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllBackups() {
        this.executeCommand.deleteAllBackup();
        return new ResponseEntity(HttpStatus.OK);
    }
}
