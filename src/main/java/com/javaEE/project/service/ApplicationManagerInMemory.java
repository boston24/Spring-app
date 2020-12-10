package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationManagerInMemory implements ApplicationManager {

    private static final List<Application> applications = new ArrayList<>();

    @Override
    public List<Application> getAllApplications(){ return applications; }

    @Override
    public void addApplication(Application application){
        application.setId(UUID.randomUUID().toString());
        applications.add(application);
    }

    @Override
    public void deleteApplicationByDomain(String domain){
        Application appToRemove = null;
        for(Application app: applications){
            if(app.getDomain().equals(domain)){
                appToRemove = app;
            }
        }
        if(appToRemove != null){
            applications.remove(appToRemove);
        }
    }


}
