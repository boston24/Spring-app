package com.javaEE.project.rest;

import com.javaEE.project.controller.ApplicationController;
import com.javaEE.project.domain.Application;
import com.javaEE.project.service.ApplicationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("rest/application")
public class ApplicationRestController {

    private ApplicationManager am;

    @Autowired
    public ApplicationRestController(ApplicationManager app){
        this.am = app;
    }

    @GetMapping("/display")
    public List<HashMap> getAll(){
        List<HashMap> out = new ArrayList<>();

        for(Application application : am.getAllApplications()){
            HashMap app = new HashMap();
            app.put("name",application.getName());
            app.put("domain",application.getDomain());
            app.put("users",application.getUser_list().size());
            out.add(app);
        }

        return out;
    }

}
