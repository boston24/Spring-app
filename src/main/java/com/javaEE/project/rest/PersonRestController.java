package com.javaEE.project.rest;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("rest/person")
public class PersonRestController {

    private PersonManager pm;

    @Autowired
    public PersonRestController(PersonManager per){
        this.pm = per;
    }

    @GetMapping("/display")
    public List<HashMap> getAll(){
        List<HashMap> out = new ArrayList<>();

        for(Person person : pm.getAllPersons()) {
            HashMap per = new HashMap();
            per.put("first_name", person.getFirst_name());
            per.put("last_name", person.getLast_name());
            per.put("email", person.getEmail());
            per.put("country", person.getCountry());
            per.put("username", person.getUsername());
            per.put("password", person.getPassword());
            per.put("aplications", pm.getAppNames(person.getId()));
            out.add(per);
        }
            return out;
    }

}
