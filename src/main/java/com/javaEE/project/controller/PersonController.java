package com.javaEE.project.controller;

import com.javaEE.project.domain.Person;
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

import javax.validation.Valid;

@Slf4j
@Controller
public class PersonController {

    @Autowired
    PersonManager pm;

    @GetMapping("/personAll")
    public String showPersons(Model model){
        Person p = new Person();
        p.setUsername("boop");
        pm.addPerson(p);
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

}
