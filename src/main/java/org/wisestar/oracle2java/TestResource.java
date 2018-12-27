package org.wisestar.oracle2java;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestResource {

    @RequestMapping("/hello")
    public String test()  {
        System.out.println("HelloWorld!!");
        return "HelloWorld";
    }
}
