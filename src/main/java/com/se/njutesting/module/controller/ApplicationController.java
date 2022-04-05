package com.se.njutesting.module.controller;

import com.se.njutesting.module.entity.Application;
import com.se.njutesting.module.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RestController
@RequestMapping("/app")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("")
    public List<Application> findByName(@RequestParam String name) {
        return applicationService.selectAppByName(name);
    }

    @PostMapping("/insert")
    public String insertApp(@RequestBody Application app) {
        applicationService.insert(app);
        return "success!";
    }

}
