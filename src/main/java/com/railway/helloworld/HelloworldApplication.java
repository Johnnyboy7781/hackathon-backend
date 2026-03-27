package com.railway.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railway.helloworld.repositories.TestRepo;

@SpringBootApplication
@RestController
public class HelloworldApplication {

    @Autowired
    TestRepo testRepo;

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        var idList = testRepo.findAll();
        System.out.println(idList);
        return String.format("Hello world from Java Spring Boot!");
    }

}
