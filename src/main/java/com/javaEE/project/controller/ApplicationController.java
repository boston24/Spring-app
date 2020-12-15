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
        model.addAttribute("application",am.findById(id));
        return "app-edit";
    }

    @PostMapping("/appAll/edit")
    public String editAppHelp(@Valid Application application, Errors errors, Model model){
        if(errors.hasErrors()){
            return "app-edit";
        }
        if(am.isAppNameTaken(application)){
            errors.rejectValue("name","error.application","App name taken");
            return "app-edit";
        }
        if(am.isDomainTaken(application)){
            errors.rejectValue("domain","error.application","Domain is taken");
            return "app-edit";
        }
        am.replace(application);
        model.addAttribute("apps",am.getAllApplications());
        return "apps";
    }

    @GetMapping("/appAdd")
    public String createApp(Model model){
        model.addAttribute("application", new Application());
        return "apps-add";
    }

    @PostMapping("/appAdd")
    public String addCreated(@Valid Application application, Errors errors){
        if(errors.hasErrors()){
            return "apps-add";
        }
        if(am.isAppNameTaken(application)){
            errors.rejectValue("name","error.application","Domain taken");
            return "apps-add";
        }
        if(am.isDomainTaken(application)){
            errors.rejectValue("domain","error.application","App name taken");
            return "apps-add";
        }
        am.addApplication(application);
        log.info("App created: "+application);
        return "redirect:/";
    }

    @RequestMapping("/appDelete")
    public String deletePerson(@RequestParam String domain, Model model){
        am.deleteApplicationByDomain(domain);
        model.addAttribute("apps",am.getAllApplications());
        return "apps";
    }

    @RequestMapping("appAll/addUser")
    public String addPersonToApp(@RequestParam(value = "id") String id_app, @RequestParam(value = "perid") String id_per, Model model){
        Person p = pm.getPersonById(id_per);
        Application app = am.findById(id_app);
        pm.addAppToAppList(app,p);
        am.addToUserList(app,p);
        model.addAttribute("persons",pm.getAllPersonsNotInApp(id_app));
        model.addAttribute("id",id_app);
        return "app-addUsers";
    }

    @RequestMapping("appAll/removeUser")
    public String removePersonFromApp(@RequestParam String id, @RequestParam String username, Model model){
        Person p = pm.getPersonByUsername(username);
        Application app = am.findById(id);
        am.removeFromUserList(id,p);
        pm.removeAppFromList(app,p);
        model.addAttribute("persons",pm.getAllPersonsInApp(id));
        model.addAttribute("id",id);
        model.addAttribute("data",am.getUserCountryData(id));
        return "app-removeUsers";
    }


}
