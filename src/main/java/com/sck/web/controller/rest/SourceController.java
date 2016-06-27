package com.sck.web.controller.rest;

import com.sck.domain.Source;
import com.sck.repository.SourceRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Source", description = "Data source")
@RestController
@RequestMapping(value = "/api/source")
public class SourceController extends BaseRestController<Source, Long> {

    private SourceRepository repository;

    @Autowired
    public SourceController(SourceRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
