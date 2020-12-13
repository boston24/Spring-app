package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import java.util.List;

public interface PersonManager {

    void addPerson(Person person);
    void deletePersonById(String id);
    void deletePersonByUsername(String username);
    List<Person> getAllPersonsNotInApp(String domain);
    Person getPersonByUsername(String username);
    void addAppToAppList(Application app, Person p);
    List<Person> getAllPersonsInApp(String domain);
    void removeAppFromList(Application app, Person p);
    void replace(Person edited);
    List<Person> getAllPersons();

}
