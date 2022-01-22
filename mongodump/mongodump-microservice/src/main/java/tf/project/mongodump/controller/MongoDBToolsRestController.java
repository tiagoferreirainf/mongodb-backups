package tf.project.mongodump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class MongoDBToolsRestController {

    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String getEcho() {
        return "Hello!!!";
    }
}
