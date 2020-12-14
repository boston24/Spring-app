package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;

import java.util.List;
import java.util.Map;

public interface ApplicationManager {

    void addApplication(Application application);
    void deleteApplicationByDomain(String name);
    Application findByDomain(String domain);
    Application findById(String id);
    void replace(Application edited);
    void addToUserList(Application app, Person per);
    void removeFromUserList(String domain, Person per);
    Application findByName(String name);
    List<Application> getAllApplications();
    Map<String,Integer> getUserCountryData(String id);
    List<Application> getAllAppsInUser(String id);
    boolean isDomainTaken(Application newApp);
    boolean isAppNameTaken(Application newApp);
    void loadData(List<Application> data);

}
