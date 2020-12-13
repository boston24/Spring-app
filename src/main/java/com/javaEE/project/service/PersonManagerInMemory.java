package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
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
    public Person getPersonById(String id){
        for(Person per : persons){
            if(per.getId().equals(id)){
                return per;
            }
        }
        return null;
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
    public List<String> getAppNames(String id){
        List<String> out = new ArrayList<>();
        for(Person per : persons){
            for(Application app : per.getApp_list()){
                out.add(app.getName());
            }
        }
        return out;
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
    public List<Person> getAllPersonsNotInApp(String id){
        List<Person> temp = new ArrayList<>();
        for(Person per : persons){
            boolean check = false;
            if(per.getApp_list().isEmpty()) {
                temp.add(per);
            }
            else{
                for(Application app : per.getApp_list()){
                    if(app.getId().equals(id)){
                        check = true;
                    }
                }
                if(check == false){
                    temp.add(per);
                }
            }

        }
        return temp;
    }

    @Override
    public void replace(Person edited){
        for(Person per : persons){
            if(per.getId().equals(edited.getId())){
                edited.setApp_list(per.getApp_list());
                persons.set(persons.indexOf(per),edited);
            }
        }
    }

    @Override
    public List<Person> getAllPersonsInApp(String id){
        List<Person> temp = new ArrayList<>();
        for(Person per : persons){
            if(per.getApp_list().isEmpty()) {
                continue;
            }
            else{
                for(Application app : per.getApp_list()){
                    if(app.getId().equals(id)){
                        temp.add(per);
                    }
                }
            }

        }
        return temp;
    }


    @Override
    public Person getPersonByUsername(String username){
        for(Person p : persons){
            log.info("Szukam "+username);
            if(p.getUsername().equals(username)){
                log.info("Znalazłem");
                return p;
            }
        }
        return null;
    }

    @Override
    public void addAppToAppList(Application app, Person p){
        log.info("Przypisuje aplikacje to listy aplikacji użytkownika");
        p.getApp_list().add(app);
    }

    @Override
    public void removeAppFromList(Application app, Person p){
        p.getApp_list().remove(app);
    }

}
