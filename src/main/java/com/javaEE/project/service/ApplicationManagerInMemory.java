package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationManagerInMemory {

    private static final List<Application> applications = new ArrayList<>();

    @Override
    public void addApplication(Application application){
        application.setId(UUID.randomUUID().toString());
        applications.add(application);
    }

    @Override
    public void deleteApplicationByUsername(String name){
        Application appToRemove = null;
        for(Application app: applications){
            if(app.getName()).equals(name)){
                appToRemove = app;
            }
        }
        if(appToRemove != null){
            applications.remove(appToRemove);
        }
    }


}
