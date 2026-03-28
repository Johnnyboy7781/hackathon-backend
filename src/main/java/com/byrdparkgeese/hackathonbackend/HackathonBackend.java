package com.byrdparkgeese.hackathonbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.repositories.TestRepo;

@SpringBootApplication
@RestController
public class HackathonBackend {

    @Autowired
    TestRepo testRepo;

    public static void main(String[] args) {
        SpringApplication.run(HackathonBackend.class, args);
    }

    @GetMapping("/")
    public String hello() {
        var idList = testRepo.findAll();
        System.out.println(idList.get(0).id);
        return String.format("Hello world from Java Spring Boot!");
    }

}
