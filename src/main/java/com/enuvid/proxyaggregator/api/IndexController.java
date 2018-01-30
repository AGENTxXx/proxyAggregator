package com.enuvid.proxyaggregator.api;

import com.enuvid.proxyaggregator.data.Test;
import com.enuvid.proxyaggregator.data.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private TestRepository testRepo;

    @RequestMapping("/")
    String index() {
        return "/test Hello world";
    }

    @RequestMapping("/tests")
    List<Test> tests() {
        return testRepo.findAll();
    }
}
