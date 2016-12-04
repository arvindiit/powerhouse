package com.powerhouse.assignment.controller;

import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.model.Profile;
import com.powerhouse.assignment.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FractionController {


    @Autowired
    private GenericService genericService;

    @GetMapping("/fraction/{profileId}")
    public Profile get(@PathVariable("profileId") String profileId) {
        return genericService.getProfile(profileId);
    }

    @PostMapping("/fraction")
    public String postProfile(@RequestBody Profile profile) throws ValidationException {
        return genericService.updateProfile(profile);
    }
}
