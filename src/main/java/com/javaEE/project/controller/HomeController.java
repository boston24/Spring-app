package com.javaEE.project.controller;

import com.javaEE.project.csvreaders.CSVReaderApplications;
import com.javaEE.project.csvreaders.CSVReaderPersons;
import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.repository.ApplicationRepository;
import com.javaEE.project.repository.PersonRepository;
import com.javaEE.project.service.ApplicationManager;
import com.javaEE.project.service.PersonManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

        return "home";
    }

}
