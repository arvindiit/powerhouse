package com.powerhouse.assignment.controller;

import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.model.Connection;
import com.powerhouse.assignment.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/reading")
public class MeterReadingController {


    @Autowired
    private GenericService genericService;

    @GetMapping("/{profileId}")
    public Connection get(@PathVariable("profileId") String profileId) {
        return genericService.getConnection(profileId);
    }

    @PostMapping("/")
    public String postProfile(@RequestBody Connection connection) throws ValidationException {

        return genericService.updateConnection(connection);
    }
}
