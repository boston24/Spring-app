package com.javaEE.project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    private String id;
    private String name;
    private String domain;
    private List<Person> user_list;

}
