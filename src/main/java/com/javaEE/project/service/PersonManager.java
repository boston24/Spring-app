package com.javaEE.project.service;

import com.javaEE.project.domain.Person;
import java.util.List;

public interface PersonManager {

    void addPerson(Person person);
    void deletePersonById(String id);
    List<Person> getAllPersons();

}
