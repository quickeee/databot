package com.sck.web.controller.rest;

import com.sck.domain.ColumnMap;
import com.sck.repository.ColumnMapRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Column Map", description = "Source to Destination")
@RestController
@RequestMapping(value = "/api/columnMap")
public class ColumnMapController extends BaseRestController<ColumnMap, Long> {

    private ColumnMapRepository repository;

    @Autowired
    public ColumnMapController(ColumnMapRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
