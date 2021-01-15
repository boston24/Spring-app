package com.javaEE.project.controller;

import com.javaEE.project.csvreaders.CSVReaderApplications;
import com.javaEE.project.csvreaders.CSVReaderPersons;
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

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    PersonManager pm;

    @Autowired
    ApplicationManager am;

    @GetMapping("/")
    public String homePage(){
        if(pm.getAllPersons().isEmpty()){
            try{
                List<Person> person = new CSVReaderPersons().readCSV();
                pm.loadData(person);
            }
            catch (FileNotFoundException ex){
                log.info("File not found");
            }
        }

        if(am.getAllApplications().isEmpty()){
            try{
                List<Application> app = new CSVReaderApplications().readCSV();
                am.loadData(app);
            }
            catch (FileNotFoundException ex){
                log.info("File not found");
            }
        }

        return "login";
    }

    @GetMapping("/admin")
    public String admin(){
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("person", new Person());
        return "all/register";
    }

    @PostMapping("/register")
    public String addRegistered(@Valid Person person, Errors errors){
        if(errors.hasErrors()){
            return "all/register";
        }
        if(pm.isUsernameTaken(person)){
            errors.rejectValue("username","error.person","Username taken");
            return "all/register";
        }
        if(pm.isEmailTaken(person)){
            errors.rejectValue("email","error.person","Email taken");
            return "all/register";
        }
        pm.addPerson(person);
        log.info("Person created: " + person);
        return "redirect:/";
    }

}
