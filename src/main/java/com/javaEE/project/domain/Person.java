package com.javaEE.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String country;
    private String username;
    private String password;
    private List<Application> app_list = new ArrayList<>();

}
