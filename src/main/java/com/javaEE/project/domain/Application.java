package com.javaEE.project.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Application {

    private String id;
    private String name;
    private String domain;
    private List<Person> user_list;

}
