package com.github.manedev79.timesheet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController()
@RequestMapping("helloworld")
public class SampleController {

    @RequestMapping(method = GET)
    public String getHelloWorld() {
        return "Hello Matze & Jan";
    }
}
