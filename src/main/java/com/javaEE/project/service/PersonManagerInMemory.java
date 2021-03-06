package com.javaEE.project.service;

import com.javaEE.project.domain.Application;
import com.javaEE.project.domain.Person;
import com.javaEE.project.email.EmailConfig;
import com.javaEE.project.repository.ApplicationRepository;
import com.javaEE.project.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class PersonManagerInMemory implements PersonManager {

    @Autowired
    PersonRepository pr;

    private static final List<Person> persons = new ArrayList<>();

    @Override
    public void loadData(List<Person> data){
        for(Person person : data){
            if(person.getId().equals("admin")){
                person.setRoles("ROLE_ADMIN");
                person.setActive(true);
                pr.save(person);
            }
            else if(isEmailTaken(person)==false && isUsernameTaken(person)==false){
                //log.info("Dodaje uzytkownika: " + person);
                //persons.add(person);
                person.setRoles("ROLE_USER");
                person.setActive(true);
                pr.save(person);
            }
        }
    }

    @Override
    public void addPerson(Person person){
        person.setId(UUID.randomUUID().toString());
        pr.save(person);
        //persons.add(person);
    }

    @Override
    public List<Person> getAllPersons(){
        List<Person> out = new ArrayList<>();
        for(Person per : pr.findAll()){
            if(!per.getId().equals("admin")){
                out.add(per);
            }
        }
        return out;
    }


    @Override
    public Person getPersonById(String id){
        for(Person per : pr.findAll()){
            if(per.getId().equals(id)){
                return per;
            }
        }
        return null;
    }

    @Override
    public void deletePersonById(String id){
        Person personToRemove = null;
        for(Person person: pr.findAll()){
            if(person.getId().equals(id)){
                personToRemove = person;
            }
        }
        if(personToRemove != null){
            pr.findAll().remove(personToRemove);
        }
    }

    @Override
    public List<String> getAppNames(String id){
        List<String> out = new ArrayList<>();
        for(Person per : pr.findAll()){
            for(Application app : per.getApp_list()){
                out.add(app.getName());
            }
        }
        return out;
    }

    @Override
    public void deletePersonByUsername(String username){
        Person personToRemove = null;
        for(Person person: pr.findAll()){
            if(person.getUsername().equals(username)){
                personToRemove = person;
            }
        }
        if(personToRemove != null){
            //pr.findAll().remove(personToRemove);
            pr.delete(personToRemove);
            log.info("Deleted "+personToRemove.getUsername());
        }
    }


    @Override
    public List<Person> getAllPersonsNotInApp(String id){
        List<Person> temp = new ArrayList<>();
        for(Person per : pr.findAll()){
            boolean check = false;
            if(per.getApp_list().isEmpty() && !per.getId().equals("admin")) {
                temp.add(per);
            }
            else{
                for(Application app : per.getApp_list()){
                    if(app.getId().equals(id)){
                        check = true;
                    }
                }
                if(check == false && !per.getId().equals("admin")){
                    temp.add(per);
                }
            }

        }
        return temp;
    }

    @Override
    public void replace(Person edited){
        for(Person per : pr.findAll()){
            if(per.getId().equals(edited.getId())){
                edited.setApp_list(per.getApp_list());
                //pr.findAll().set(pr.findAll().indexOf(per),edited);
                pr.save(edited);
            }
        }
    }

    @Override
    public List<Person> getAllPersonsInApp(String id){
        List<Person> temp = new ArrayList<>();
        for(Person per : pr.findAll()){
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
        for(Person p : pr.findAll()){
            //log.info("Szukam "+username);
            if(p.getUsername().equals(username)){
                //log.info("Znalazłem");
                return p;
            }
        }
        return null;
    }

    @Override
    public void addAppToAppList(Application app, Person p){
        log.info("Przypisuje aplikacje to listy aplikacji użytkownika");
        p.getApp_listAsSet().add(app);
        app.getUser_listAsSet().add(p);
        pr.save(p);
        //p.getApp_list().add(app);
    }

    @Override
    public void removeAppFromList(Application app, Person p){
        /*List<Application> out = new ArrayList<>();
        for(Application application : p.getApp_list()){
            if(!application.getId().equals(app.getId())){
                out.add(application);
            }
        }
        p.setApp_list(out);
        */
        log.info("DELETING"+p.getUsername());
        p.getApp_listAsSet().remove(app);
        app.getUser_listAsSet().remove(p);
        pr.save(p);
    }

    @Override
    public boolean isUsernameTaken(Person newbie){
        if(newbie.getUsername().equals("admin")){
            return true;
        }
        for(Person per : pr.findAll()){
            if(per.getUsername().equals(newbie.getUsername()) && !per.getId().equals(newbie.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmailTaken(Person newbie){
        for(Person per : pr.findAll()){
            if(per.getEmail().equals(newbie.getEmail()) && !per.getId().equals(newbie.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isStrong(String password){
        Pattern pattern = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{6,}$");
        Matcher matcher = pattern.matcher(password);
        if(matcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void sendMail(Person person){

        EmailConfig emcfg = new EmailConfig();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emcfg.getHost());
        mailSender.setPort(emcfg.getPort());
        mailSender.setUsername(emcfg.getUsername());
        mailSender.setPassword(emcfg.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("project@gmail.com");
        mailMessage.setTo(person.getEmail());
        mailMessage.setSubject("Welcome "+person.getUsername());
        mailMessage.setText("You have been registered!");

        mailSender.send(mailMessage);

    }

}
