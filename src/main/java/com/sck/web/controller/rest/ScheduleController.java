package com.sck.web.controller.rest;

import com.sck.domain.Schedule;
import com.sck.repository.ScheduleRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Schedule", description = "When a job will run")
@RestController
@RequestMapping(value = "/api/schedule")
public class ScheduleController extends BaseRestController<Schedule, Long> {

    private ScheduleRepository repository;

    @Autowired
    public ScheduleController(ScheduleRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
