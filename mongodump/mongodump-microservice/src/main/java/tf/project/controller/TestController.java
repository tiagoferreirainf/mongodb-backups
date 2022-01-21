package tf.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test/")
public class TestController {

    @RequestMapping(value = "echo", method = RequestMethod.GET)
    public String getEcho() {
        return "Hello!!!";
    }
}
