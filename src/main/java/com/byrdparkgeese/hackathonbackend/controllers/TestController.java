package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.data.repositories.TestRepository;
import com.byrdparkgeese.hackathonbackend.services.Rva311Service;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    Rva311Service rva311Service;

    @GetMapping("/")
    public String hello() {
        var res = rva311Service.getReports();
        return res.data()[0].description();
    }

}
