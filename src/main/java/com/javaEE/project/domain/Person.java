package com.javaEE.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Person {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String country;
    private String username;
    private String password;
    private List<Application> app_list;

}
