package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;

import java.util.List;

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
    List<Application> getAllAppsInUser(String id);

}
