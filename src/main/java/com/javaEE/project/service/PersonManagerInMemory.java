package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonManagerInMemory implements PersonManager {

    private static final List<Person> persons = new ArrayList<>();

    @Override
    public void addPerson(Person person){
        person.setId(UUID.randomUUID().toString());
        persons.add(person);
    }

    @Override
    public List<Person> getAllPersons(){
        return persons;
    }

    @Override
    public void deletePersonById(String id){
        Person personToRemove = null;
        for(Person person: persons){
            if(person.getId().equals(id)){
                personToRemove = person;
            }
        }
        if(personToRemove != null){
            persons.remove(personToRemove);
        }
    }

    @Override
    public void deletePersonByUsername(String username){
        Person personToRemove = null;
        for(Person person: persons){
            if(person.getUsername().equals(username)){
                personToRemove = person;
            }
        }
        if(personToRemove != null){
            persons.remove(personToRemove);
        }
    }

    @Override
    public List<Person> getAllPersonsNotInApp(String domain){
        List<Person> temp = new ArrayList<>();
        for(Person per : persons){
            if(per.getApp_list()==null) {
                temp.add(per);
            }
            else{
                for(Application app : per.getApp_list()){
                    if(app.getDomain()==null){
                        continue;
                    }
                    temp.add(per);
                }
            }

        }
        return temp;
    }

}
