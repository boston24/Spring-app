package com.javaEE.project.controller;

import com.javaEE.project.csvreaders.CSVReaderApplications;
import com.javaEE.project.csvreaders.CSVReaderPersons;
import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.service.ApplicationManager;
import com.javaEE.project.service.PersonManager;
import com.sun.mail.iap.ConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.security.Principal;
import java.util.ArrayList;
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
    public String admin(Principal principal){
        log.info(principal.getName());
        return "admin/home";
    }

    @GetMapping("/user")
    public String user(Principal principal){
        log.info(principal.getName());
        return "user/homeUser";
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
        if(!pm.isStrong(person.getPassword())){
            errors.rejectValue("password","error.person","Password too weak. Requirements: 2 uppercase letters, 1 special case letter, 2 digits, 3 lowercase letters, length of 6.");
            return "all/register";
        }
        person.setActive(true);
        person.setRoles("ROLE_USER");
        pm.addPerson(person);
        //pm.sendMail(person);
        try{
            pm.sendMail(person);
            log.info("Email sent");
        } catch(Exception e){
            System.err.println("Can't connect to the email server");
        }

        log.info("Person created: " + person);
        return "redirect:/";
    }

    @GetMapping("/apptable")
    public String showApps(Model model){
        for(Application app : am.getAllApplications()){
            app.setNumberOfUsers();
        }
        model.addAttribute("apps", am.getAllApplications());
        return "all/allApps";
    }

}
