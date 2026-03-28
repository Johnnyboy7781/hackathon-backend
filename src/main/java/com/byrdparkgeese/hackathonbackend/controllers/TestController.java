package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.data.repositories.TestRepository;
import com.byrdparkgeese.hackathonbackend.services.Rva411Service;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    Rva411Service rva411Service;

    @GetMapping("/")
    public String hello() {
        var res = rva411Service.getReports();
        System.out.println(res);
        return String.format(res);
    }

}
