package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.repositories.TestRepository;

@RestController
public class TestController {

    @Autowired
    TestRepository testRepository;

    @GetMapping("/")
    public String hello() {
        var idList = testRepository.findAll();
        System.out.println(idList.get(0).id);
        return String.format("Hello world from Java Spring Boot!");
    }

}
