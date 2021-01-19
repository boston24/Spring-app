package com.javaEE.project.controller;


import com.javaEE.project.csvreaders.GenerateAppsCSV;
import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.service.ApplicationManager;
import com.javaEE.project.service.PersonManager;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@Controller
public class ApplicationController {

    @Autowired
    ApplicationManager am;

    @Autowired
    PersonManager pm;

    @GetMapping("/admin/appAll")
    public String showApps(Model model) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException{
        GenerateAppsCSV.export(am.getAllApplications());
        model.addAttribute("apps", am.getAllApplications());
        return "admin/apps";
    }

    @GetMapping("/admin/appAll/edit")
    public String editApp(@RequestParam String id, Model model){
        model.addAttribute("application",am.findById(id));
        return "admin/app-edit";
    }

    @GetMapping("/admin/appsExport")
    public String toCSV(Model model) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException{
        GenerateAppsCSV.export(am.getAllApplications());
        model.addAttribute("apps",am.getAllApplications());
        return "admin/apps";
    }

    @PostMapping("/admin/appAll/edit")
    public String editAppHelp(@Valid Application application, Errors errors, Model model){
        if(errors.hasErrors()){
            return "admin/app-edit";
        }
        if(am.isAppNameTaken(application)){
            errors.rejectValue("name","error.application","App name taken");
            return "admin/app-edit";
        }
        if(am.isDomainTaken(application)){
            errors.rejectValue("domain","error.application","Domain is taken");
            return "admin/app-edit";
        }
        am.replace(application);
        model.addAttribute("apps",am.getAllApplications());
        return "admin/apps";
    }

    @GetMapping("/admin/appAdd")
    public String createApp(Model model){
        model.addAttribute("application", new Application());
        return "admin/apps-add";
    }

    @PostMapping("/admin/appAdd")
    public String addCreated(@Valid Application application, Errors errors){
        if(errors.hasErrors()){
            return "admin/apps-add";
        }
        if(am.isAppNameTaken(application)){
            errors.rejectValue("name","error.application","Domain taken");
            return "admin/apps-add";
        }
        if(am.isDomainTaken(application)){
            errors.rejectValue("domain","error.application","App name taken");
            return "admin/apps-add";
        }
        am.addApplication(application);
        log.info("App created: "+application);
        return "admin/home";
    }

    @RequestMapping("/admin/appDelete")
    public String deletePerson(@RequestParam String domain, Model model){
        for(Person per : am.findByDomain(domain).getUser_list()){
            pm.removeAppFromList(am.findByDomain(domain),per);
        }
        am.deleteApplicationByDomain(domain);
        model.addAttribute("apps",am.getAllApplications());
        return "admin/apps";
    }

    @RequestMapping("/admin/appAll/addUser")
    public String addPersonToApp(@RequestParam(value = "id") String id_app, @RequestParam(value = "perid") String id_per, Model model){
        Person p = pm.getPersonById(id_per);
        Application app = am.findById(id_app);
        pm.addAppToAppList(app,p);
        //am.addToUserList(app,p);
        model.addAttribute("persons",pm.getAllPersonsNotInApp(id_app));
        model.addAttribute("id",id_app);
        return "admin/app-addUsers";
    }

    @RequestMapping("/admin/appAll/removeUser")
    public String removePersonFromApp(@RequestParam String id, @RequestParam String perid, Model model){
        Person p = pm.getPersonById(perid);
        log.info("TO DELETE: "+p.getUsername());
        Application app = am.findById(id);
        //am.removeFromUserList(app,p);
        pm.removeAppFromList(app,p);
        for(Person per : app.getUser_list()){
            log.info("users after deletion: " + per.getUsername());
        }
        model.addAttribute("persons",pm.getAllPersonsInApp(id));
        model.addAttribute("id",id);
        model.addAttribute("data",am.getUserCountryData(id));
        return "admin/app-removeUsers";
    }

    @GetMapping("/user/createapp")
    public String createAppByUser(Model model){
        model.addAttribute("application", new Application());
        return "user/user-createapp";
    }

    @PostMapping("/user/createapp")
    public String addCreated(@Valid Application application, Errors errors, Principal principal){
        if(errors.hasErrors()){
            return "user/user-createapp";
        }
        if(am.isAppNameTaken(application)){
            errors.rejectValue("name","error.application","Domain taken");
            return "user/user-createapp";
        }
        if(am.isDomainTaken(application)){
            errors.rejectValue("domain","error.application","App name taken");
            return "user/user-createapp";
        }
        am.addApplication(application);
        pm.addAppToAppList(application,pm.getPersonByUsername(principal.getName()));
        log.info("App created: "+application);
        return "user/homeUser";
    }

    @GetMapping("/user/browseapps")
    public String showApps(Model model, Principal principal){
        model.addAttribute("apps", am.getAllApplicationsNotUser(pm.getPersonByUsername(principal.getName()).getId()));
        return "user/user-browseapps";
    }

    @RequestMapping("/user/browseapps/add")
    public String addPersonToApp(@RequestParam(value = "id") String id_app, Model model, Principal principal){
        pm.addAppToAppList(am.findById(id_app),pm.getPersonByUsername(principal.getName()));
        model.addAttribute("apps", am.getAllApplicationsNotUser(pm.getPersonByUsername(principal.getName()).getId()));
        return "user/user-browseapps";
    }

}
