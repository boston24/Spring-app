package com.javaEE.project.security;

import com.javaEE.project.domain.Person;
import com.javaEE.project.repository.PersonRepository;
import com.javaEE.project.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersonManager pm;

    @Autowired
    PersonRepository pr;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //Person user = pm.getPersonByUsername(s);
        Optional <Person> user = pr.findById(pm.getPersonByUsername(s).getId());

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));

        return user.map(MyUserDetails::new).get();

    }
}
