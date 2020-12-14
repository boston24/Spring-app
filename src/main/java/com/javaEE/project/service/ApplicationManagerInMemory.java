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
    public void loadData(List<Application> data){
        for(Application app : data){
            //log.info("Dodaje aplikacje: " + app);
            applications.add(app);
        }
    }

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
            log.info("Szukam "+domain);
            if(app.getDomain().equals(domain)){
                log.info("Znalazłem");
                return app;
            }
            log.info("Nie znalazłem");
        }
        log.info("Wychodze z nullem");
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
    public void addToUserList(Application app, Person per){
        app.getUser_list().add(per);
    }

    @Override
    public void removeFromUserList(String domain, Person per){
        findByDomain(domain).getUser_list().remove(per);
    }

    @Override
    public Application findByName(String name){
        for(Application app : applications){
            if(app.getName().equals(name)){
                return app;
            }
        }
        return null;
    }

    @Override
    public List<Application> getAllAppsInUser(String id){
        List<Application> temp = new ArrayList<>();
        for(Application app : applications){
            if(app.getUser_list().isEmpty()){
                continue;
            }
            else{
                for(Person per : app.getUser_list()){
                    if(per.getId().equals(id)){
                        temp.add(app);
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public Map<String,Integer> getUserCountryData(String id){
        Map<String, Integer> out = new HashMap<>();
        for(Person per : findById(id).getUser_list()){
            if(out.containsKey(per.getCountry())){
                out.put(per.getCountry(),out.get(per.getCountry())+1);
            }
            else{
                out.put(per.getCountry(),1);
            }
        }
        return out;
    }

    @Override
    public boolean isDomainTaken(Application newApp){
        for(Application app : applications){
            if(app.getDomain().equals(newApp.getDomain()) && !app.getId().equals(newApp.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppNameTaken(Application newApp){
        for(Application app : applications){
            if(app.getName().equals(newApp.getName()) && !app.getId().equals(newApp.getId())){
                return true;
            }
        }
        return false;
    }



}
