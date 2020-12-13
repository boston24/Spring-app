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
public class Application {

    private String id;

    @NotNull(message = "App name is required")
    @Size(min = 1, message = "Name is required")
    private String name;

    @NotNull(message = "Domain is required")
    @Size(min = 1, message = "Domain is required")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9]\\.[a-zA-Z]{2,}$", message = "Wrong format")
    private String domain;

    private List<Person> user_list = new ArrayList<>();

}
