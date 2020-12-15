package com.javaEE.project.domain;

import com.opencsv.bean.CsvBindByName;
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

    @CsvBindByName
    private String id;

    @CsvBindByName
    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name is required")
    //@Pattern(regexp = "[A-Za-z]")
    private String first_name;

    @CsvBindByName
    @NotNull(message = "Last name is required")
    //@Pattern(regexp = "[A-Za-z]")
    @Size(min = 1, message = "Last name is required")
    private String last_name;

    @CsvBindByName
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Required email example: xyz@site.com")
    private String email;

    @CsvBindByName(column = "Country")
    @NotNull(message = "Country required")
    @Pattern(regexp = "[a-zA-Z]{2,}", message = "Only letters of the alphabet allowed")
    private String country;

    @CsvBindByName(column = "Username")
    @NotNull(message = "Username required")
    @Size(min = 1, message = "Username is required")
    private String username;

    @CsvBindByName(column = "Password")
    @NotNull(message = "Password required")
    @Size(min = 6, message = "Password too short (min. 6 characters)")
    private String password;

    private List<Application> app_list = new ArrayList<>();

}
