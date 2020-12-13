package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import java.util.List;

public interface PersonManager {

    void addPerson(Person person);
    void deletePersonById(String id);
    void deletePersonByUsername(String username);
    List<Person> getAllPersonsNotInApp(String id);
    Person getPersonByUsername(String username);
    void addAppToAppList(Application app, Person p);
    List<Person> getAllPersonsInApp(String id);
    void removeAppFromList(Application app, Person p);
    void replace(Person edited);
    Person getPersonById(String id);
    List<String> getAppNames(String id);
    List<Person> getAllPersons();
    boolean isEmailTaken(Person person);
    boolean isUsernameTaken(Person person);

}
