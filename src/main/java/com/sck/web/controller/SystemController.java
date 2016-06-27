package com.sck.web.controller;

import com.sck.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by KINCERS on 6/12/2015.
 */
@RestController
@RequestMapping("/")
public class SystemController {

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping(
            value = "ping",
            method = RequestMethod.GET
    )
    public ResponseEntity<String> ping() {

        return new ResponseEntity<>("pong", HttpStatus.OK);

    }

}
