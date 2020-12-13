package com.javaEE.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String id;

    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name is required")
    //@Pattern(regexp = "[A-Za-z]")
    private String first_name;

    @NotNull(message = "Last name is required")
    //@Pattern(regexp = "[A-Za-z]")
    @Size(min = 1, message = "Last name is required")
    private String last_name;

    @NotNull(message = "Email is required")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "That's not an email")
    private String email;

    @NotNull(message = "Country required")
    @Pattern(regexp = "[a-zA-Z]{2,}", message = "Only letters of the alphabet allowed")
    private String country;

    @NotNull(message = "Username required")
    @Size(min = 1, message = "Username is required")
    private String username;

    @NotNull(message = "Password required")
    @Size(min = 6, message = "Password too short (min. 6 characters)")
    private String password;

    private List<Application> app_list = new ArrayList<>();

}
