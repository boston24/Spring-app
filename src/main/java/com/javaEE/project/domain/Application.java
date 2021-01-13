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
@Table(name="application")
public class Application {

    @Id
    @CsvBindByName
    private String id;

    @Column(name="name")
    @CsvBindByName(column = "Aplication name")
    @NotNull(message = "App name is required")
    @Size(min = 1, message = "Name is required")
    private String name;

    @Column(name="domain")
    @CsvBindByName(column = "Domain name")
    @NotNull(message = "Domain is required")
    @Size(min = 1, message = "Domain is required")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9-]{1,61}[a-zA-Z0-9]\\.[a-zA-Z]{2,}$", message = "Required format example: domainname.com")
    private String domain;

    @Column(name="users")
    private List<Person> user_list = new ArrayList<>();

}
