package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;

import java.util.List;

public interface ApplicationManager {

    void addApplication(Application application);
    void deleteApplicationByName(String name);
    List<Application> getAllApplications();

}
