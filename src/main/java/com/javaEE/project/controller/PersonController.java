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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

    @GetMapping("/personAdd")
    public String createPerson(Model model){
        model.addAttribute("person", new Person());
        return "person-add";
    }

    @PostMapping("/personAdd")
    public String addCreated(@Valid Person person, Errors errors){
        if(errors.hasErrors()){
            return "persons-add";
        }
        pm.addPerson(person);
        log.info("Person created: " + person);
        return "redirect:/";
    }

    @RequestMapping("/personDelete")
    public String deletePerson(@RequestParam String username){
        pm.deletePersonByUsername(username);
        log.info("Deleted person: "+ username);
        return "redirect:/";
    }

    @GetMapping("/addUsersToApp")
    public String addToApp(@RequestParam String domain){
        return "poop";
    }

    @RequestMapping("appAll/selectUser")
    public String addUser(@RequestParam String domain, Model model){
        model.addAttribute("persons",pm.getAllPersonsNotInApp(domain));
        model.addAttribute("domain",domain);
        return "app-addUsers";
    }

    @RequestMapping("appAll/selectUserToRemove")
    public String searchUser(@RequestParam String domain, @RequestParam(value="username", required = false) String username, Model model){
        if(username==null || username==""){
            model.addAttribute("persons", pm.getAllPersonsInApp(domain));
            model.addAttribute("domain",domain);
            return "app-removeUsers";
        }

        for(Person per : pm.getAllPersonsInApp(domain)){
            if(per.getUsername().equals(username)){
                List<Person> out = new ArrayList<>();
                out.add(pm.getPersonByUsername(username));
                model.addAttribute("persons", out);
                log.info("Znalazłem: "+username);
                model.addAttribute("domain",domain);
                return "app-removeUsers";
            }
        }

        List<Person> empty = new ArrayList<>();
        model.addAttribute("persons",empty);
        log.info("Brak wynikow");
        model.addAttribute("domain",domain);
        return "app-removeUsers";

    }


    @RequestMapping("person/showApps")
    public String showApps(@RequestParam String username, @RequestParam(value="app_name", required = false) String app_name, Model model){
        if(app_name==null || app_name==""){
            model.addAttribute("apps", pm.getPersonByUsername(username).getApp_list());
            model.addAttribute("username",username);
            log.info("Pokazuje wszystkie aplikacje uzytkownika");
            return "persons-appList";
        }

        for(Application app : pm.getPersonByUsername(username).getApp_list()){
            if(app.getName().equals(app_name)){
                List<Application> temp = new ArrayList<>();
                temp.add(app);
                model.addAttribute("apps",temp);
                model.addAttribute("username",username);
                log.info("Znalazem aplikacje w liscie");
                return "persons-appList";
            }
        }

        List<Application> empty = new ArrayList<>();
        model.addAttribute("apps",empty);
        model.addAttribute("username",username);
        log.info("Nie znaleziono aplikacji");
        return "persons-appList";

    }


}
