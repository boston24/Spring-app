package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import java.util.List;

public interface ApplicationManager {

    void addApplication(Application application);
    void deleteApplicationByDomain(String name);
    List<Application> getAllApplications();

}
