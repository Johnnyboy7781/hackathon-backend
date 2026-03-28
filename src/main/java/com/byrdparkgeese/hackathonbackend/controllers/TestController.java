package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.Rva311Service;

@RestController
public class TestController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    Rva311Service rva311Service;

    @GetMapping("/")
    public GetReportsData hello() {
        return rva311Service.getReportWithRadius(
                37.556163000000005,
                -77.473413
        );
    }

}
