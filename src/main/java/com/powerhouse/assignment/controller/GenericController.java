package com.powerhouse.assignment.controller;


import com.powerhouse.assignment.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericController {

    @Autowired
    private GenericService genericService;

    @GetMapping("/consumption/{month}")
    public int get(@PathVariable("month") String month) {
        return genericService.retrieveConsumption(month);
    }

}
