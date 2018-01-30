package com.enuvid.proxyaggregator.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "/test Hello world";
    }
}
