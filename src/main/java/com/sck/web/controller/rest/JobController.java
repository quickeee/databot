package com.sck.web.controller.rest;

import com.sck.domain.Job;
import com.sck.engine.DataEngine;
import com.sck.repository.JobRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Job", description = "A Job")
@RestController
@RequestMapping(value = "/api/job")
public class JobController extends BaseRestController<Job, Long> {

    private JobRepository repository;

    @Autowired
    private DataEngine dataEngine;

    @Autowired
    public JobController(JobRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @RequestMapping(
            value = "/{id}/run",
            method = RequestMethod.POST
    )
    public ResponseEntity runJob(@PathVariable Long id) {

        Job job = repository.findOne(id);

        if(job == null) {
            return ResponseEntity.notFound().build();
        }

        dataEngine.runJob(job);
        return ResponseEntity.ok().build();
    }

}
