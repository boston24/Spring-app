package com.javaEE.project.controller;

import com.javaEE.project.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("webpersoncontroller")
public class PersonController {

    @Autowired
    PersonManager pm;

    @GetMapping("/persons")
    public String persons(Model model){
        model.addAttribute("persons", pm.getAllPersons());
        return "persons";
    }

}
