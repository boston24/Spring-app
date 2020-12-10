package com.javaEE.project.controller;


import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.service.ApplicationManager;
import com.javaEE.project.service.PersonManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ApplicationController {

    @Autowired
    ApplicationManager am;

    @GetMapping("/appAll")
    public String showApps(Model model){
        /*Application app = new Application();
        app.setName("fejzbug");
        am.addApplication(app);*/
        model.addAttribute("apps", am.getAllApplications());
        return "apps";
    }

    @RequestMapping("/appAll/edit")
    public String editApp(@RequestParam String domain, Model model){
        model.addAttribute("app",am.findByDomain(domain));
        return "app-edit";
    }

    @PostMapping("/appAll/edit")
    public String editAppHelp(@Valid Application application, Errors errors){
        if(errors.hasErrors()){
            return "home";
        }
        am.replace(application);
        log.info("App edited: " + application);
        log.info("WSZYSTKIE: "+am.getAllApplications());
        return "redirect:/";
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

    /*@PostMapping("appAll/addUser")
    public String addPersonToApp(@RequestParam String domain, @RequestParam Person person){
        am.addToUserList(domain,person);
        return "home";
    }*/


}
