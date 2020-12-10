package com.javaEE.project.controller;


import com.javaEE.project.domain.Application;
import com.javaEE.project.service.ApplicationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ApplicationController {

    @Autowired
    ApplicationManager am;

    @GetMapping("/appAll")
    public String showApps(Model model){
        Application app = new Application();
        app.setName("fejzbug");
        am.addApplication(app);
        model.addAttribute("apps", am.getAllApplications());
        return "apps";
    }


}
