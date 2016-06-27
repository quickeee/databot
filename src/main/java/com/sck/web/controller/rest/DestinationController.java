package com.sck.web.controller.rest;

import com.sck.domain.Destination;
import com.sck.repository.DestinationRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Destination", description = "Data destination")
@RestController
@RequestMapping(value = "/api/destination")
public class DestinationController extends BaseRestController<Destination, Long> {

    private DestinationRepository repository;

    @Autowired
    public DestinationController(DestinationRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
