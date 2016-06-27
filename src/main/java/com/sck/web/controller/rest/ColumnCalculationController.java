package com.sck.web.controller.rest;

import com.sck.domain.ColumnCalculation;
import com.sck.repository.ColumnCalculationRepository;
import com.sck.web.controller.BaseRestController;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KINCERS on 4/10/2015.
 */
@Api(value = "Column Calculation", description = "Calculations at the column level")
@RestController
@RequestMapping(value = "/api/columnCalculation")
public class ColumnCalculationController extends BaseRestController<ColumnCalculation, Long> {

    private ColumnCalculationRepository repository;

    @Autowired
    public ColumnCalculationController(ColumnCalculationRepository repository) {
        super(repository);
        this.repository = repository;
    }

}
