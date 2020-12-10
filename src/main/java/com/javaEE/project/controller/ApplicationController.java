package com.javaEE.project.controller;


import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.service.ApplicationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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

    @GetMapping("/appAdd")
    public String createApp(Model model){
        model.addAttribute("app", new Application());
        return "apps-add";
    }

    @PostMapping("/appAdd")
    public String addCreated(@Valid Application application, Errors errors){
        if(errors.hasErrors()){
            return "apps-add";
        }
        am.addApplication(application);
        log.info("App created: " + application);
        return "redirect:/";
    }

    @RequestMapping("/appDelete")
    public String deletePerson(@RequestParam String domain){
        am.deleteApplicationByDomain(domain);
        log.info("Deleted app: "+ domain);
        return "redirect:/";
    }


}
