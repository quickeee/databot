package com.sck.web.controller.rest;

import com.sck.domain.RowCalculation;
import com.sck.repository.RowCalculationRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Row Calculation", description = "Calculations at the row level")
@RestController
@RequestMapping(value = "/api/rowCalculation")
public class RowCalculationController extends BaseRestController<RowCalculation, Long> {

    private RowCalculationRepository repository;

    @Autowired
    public RowCalculationController(RowCalculationRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
