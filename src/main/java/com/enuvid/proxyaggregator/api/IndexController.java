package com.enuvid.proxyaggregator.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @RequestMapping("/")
    String index() {
        return "/test Hello world";
    }
}
