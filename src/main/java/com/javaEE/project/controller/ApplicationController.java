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

    @Autowired
    PersonManager pm;

    @GetMapping("/appAll")
    public String showApps(Model model){
        model.addAttribute("apps", am.getAllApplications());
        return "apps";
    }

    @GetMapping("/appAll/edit")
    public String editApp(@RequestParam String id, Model model){
        model.addAttribute("app",am.findById(id));
        return "app-edit";
    }

    @PostMapping("/appAll/edit")
    public String editAppHelp(@Valid Application application, Errors errors){
        if(errors.hasErrors()){
            return "redirect:/";
        }
        am.replace(application);
        //pm.replaceInList(application);
        for(Person per : application.getUser_list()){
            log.info("Username " + per.getUsername());
        }
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
        return "redirect:/";
    }

    @RequestMapping("/appDelete")
    public String deletePerson(@RequestParam String domain){
        am.deleteApplicationByDomain(domain);
        return "redirect:/";
    }

    @RequestMapping("appAll/addUser")
    public String addPersonToApp(@RequestParam(value = "id") String id_app, @RequestParam(value = "perid") String id_per){
        Person p = pm.getPersonById(id_per);
        Application app = am.findById(id_app);
        pm.addAppToAppList(app,p);
        am.addToUserList(app,p);
        return "redirect:/";
    }

    @RequestMapping("appAll/removeUser")
    public String removePersonFromApp(@RequestParam String domain, @RequestParam String username){
        Person p = pm.getPersonByUsername(username);
        Application app = am.findByDomain(domain);
        am.removeFromUserList(domain,p);
        pm.removeAppFromList(app,p);
        return "redirect:/";
    }


}
