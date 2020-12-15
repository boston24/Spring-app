package com.javaEE.project.controller;

import com.javaEE.project.csvreaders.GeneratePersonsCSV;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class PersonController {

    @Autowired
    PersonManager pm;

    @Autowired
    ApplicationManager am;

    @GetMapping("/personAll")
    public String showPersons(Model model){
        model.addAttribute("persons", pm.getAllPersons());
        return "persons";
    }

    @GetMapping("/personsExport")
    public String toCSV(Model model) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException, InterruptedException{
        GeneratePersonsCSV.export(pm.getAllPersons());
        model.addAttribute("persons",pm.getAllPersons());
        return "persons";
    }

    @GetMapping("/personAll/edit")
    public String editPerson(@RequestParam String id, Model model){
        model.addAttribute("person", pm.getPersonById(id));
        return "persons-edit";
    }

    @PostMapping("/personAll/edit")
    public String editPersonHelper(@Valid Person person, Errors errors, Model model){
        if(errors.hasErrors()){
            return "persons-edit";
        }
        if(pm.isUsernameTaken(person)){
            errors.rejectValue("username","error.person","Username taken");
            return "persons-edit";
        }
        if(pm.isEmailTaken(person)){
            errors.rejectValue("email","error.person","Email taken");
            return "persons-edit";
        }
        pm.replace(person);
        model.addAttribute("persons",pm.getAllPersons());
        return "persons";
    }

    @GetMapping("/personAdd")
    public String createPerson(Model model){
        model.addAttribute("person", new Person());
        return "person-add";
    }

    @PostMapping("/personAdd")
    public String addCreated(@Valid Person person, Errors errors){
        if(errors.hasErrors()){
            return "person-add";
        }
        if(pm.isUsernameTaken(person)){
            errors.rejectValue("username","error.person","Username taken");
            return "person-add";
        }
        if(pm.isEmailTaken(person)){
            errors.rejectValue("email","error.person","Email taken");
            return "person-add";
        }
        pm.addPerson(person);
        log.info("Person created: " + person);
        return "redirect:/";
    }

    @RequestMapping("/personDelete")
    public String deletePerson(@RequestParam String username, Model model){
        for(Application app : pm.getPersonByUsername(username).getApp_list()){
            am.removeFromUserList(app,pm.getPersonByUsername(username));
        }
        pm.deletePersonByUsername(username);
        log.info("Deleted person: "+ username);
        model.addAttribute("persons",pm.getAllPersons());
        return "persons";
    }

    @RequestMapping("appAll/selectUser")
    public String addUser(@RequestParam String id, Model model){
        model.addAttribute("persons",pm.getAllPersonsNotInApp(id));
        model.addAttribute("id",id);
        return "app-addUsers";
    }

    @RequestMapping("appAll/selectUserToRemove")
    public String searchUser(@RequestParam String id, @RequestParam(value="username", required = false) String username, Model model){
        if(username==null || username==""){
            model.addAttribute("persons", pm.getAllPersonsInApp(id));
            model.addAttribute("id",id);
            model.addAttribute("data",am.getUserCountryData(id));
            return "app-removeUsers";

        }

        for(Person per : pm.getAllPersonsInApp(id)){
            if(per.getUsername().equals(username)){
                List<Person> out = new ArrayList<>();
                out.add(pm.getPersonByUsername(username));
                model.addAttribute("persons", out);
                log.info("Znalazłem: "+username);
                model.addAttribute("id",id);
                //model.addAttribute("data",am.getUserCountryData(id));
                return "app-removeUsers";
            }
        }

        List<Person> empty = new ArrayList<>();
        //model.addAttribute("data",am.getUserCountryData(id));
        model.addAttribute("persons",empty);
        log.info("Brak wynikow");
        model.addAttribute("id",id);
        return "app-removeUsers";

    }


    @RequestMapping("person/showApps")
    public String showApps(@RequestParam String id, @RequestParam(value="app_name", required = false) String app_name, Model model){
        if(app_name==null || app_name==""){
            model.addAttribute("apps", am.getAllAppsInUser(id));
            model.addAttribute("id",id);
            log.info("Pokazuje wszystkie aplikacje uzytkownika");
            return "persons-appList";
        }

        for(Application app : pm.getPersonById(id).getApp_list()){
            if(app.getName().equals(app_name)){
                List<Application> temp = new ArrayList<>();
                temp.add(app);
                model.addAttribute("apps",temp);
                model.addAttribute("id",id);
                log.info("Znalazem aplikacje w liscie");
                return "persons-appList";
            }
        }

        List<Application> empty = new ArrayList<>();
        model.addAttribute("apps",empty);
        model.addAttribute("id",id);
        log.info("Nie znaleziono aplikacji");
        return "persons-appList";

    }


}
