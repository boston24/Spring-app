package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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
            if(app.getDomain()==null){continue;}
            if(app.getDomain().equals(domain)){
                appToRemove = app;
            }
        }
        if(appToRemove != null){
            applications.remove(appToRemove);
        }
    }

    @Override
    public Application findByDomain(String domain){
        for(Application app : applications){
            if(app.getDomain().equals(domain)){
                return app;
            }
        }
        return null;
    }

    @Override
    public Application findById(String id){
        for(Application app : applications){
            if(app.getId().equals(id)){
                return app;
            }
        }
        return null;
    }

    @Override
    public void replace(Application edited){
        for(Application app : applications){
            if(app.getId().equals(edited.getId())){
                edited.setUser_list(app.getUser_list());
                applications.set(applications.indexOf(app),edited);
            }
        }
    }

    @Override
    public void addToUserList(String domain, Person per){
        log.info("I'm here");
        //List<Person> temp = findByDomain(domain).getUser_list().add(per);
        //temp.removeAll(Collections.singletonList(null));
        //temp.add(per);
        //findByDomain(domain).setUser_list(temp);
        findByDomain(domain).getUser_list().add(per);
    }


}
