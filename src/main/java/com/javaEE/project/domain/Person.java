package com.javaEE.project.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.tools.nsc.doc.base.comment.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="person")
public class Person {

    @Id
    @CsvBindByName
    private String id;

    @Column(name="first_name")
    @CsvBindByName
    @NotNull(message = "Name is required")
    @Size(min = 1, message = "Name is required")
    //@Pattern(regexp = "[A-Za-z]")
    private String first_name;

    @Column(name="last_name")
    @CsvBindByName
    @NotNull(message = "Last name is required")
    //@Pattern(regexp = "[A-Za-z]")
    @Size(min = 1, message = "Last name is required")
    private String last_name;

    @Column(name="email")
    @CsvBindByName
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Required email example: xyz@site.com")
    private String email;

    @Column(name="country")
    @CsvBindByName(column = "Country")
    @NotNull(message = "Country required")
    @Pattern(regexp = "[a-zA-Z]{2,}", message = "Only letters of the alphabet allowed")
    private String country;

    @Column(name="username")
    @CsvBindByName(column = "Username")
    @NotNull(message = "Username required")
    @Size(min = 1, message = "Username is required")
    private String username;

    @Column(name = "password")
    @CsvBindByName(column = "Password")
    @NotNull(message = "Password required")
    @Size(min = 6, message = "Password too short (min. 6 characters)")
    private String password;

    @Column(name = "apps")
    private List<Application> app_list = new ArrayList<>();

}
