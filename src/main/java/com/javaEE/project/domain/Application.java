package com.javaEE.project.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.hibernate.annotations.Type;
//import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="application")
//@ToString.Exclude
//@EqualsAndHashCode.Exclude
@EqualsAndHashCode(exclude = "user_list")
@ToString(exclude = "user_list")
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
    @Pattern(regexp = "^((?!-))(xn--)?[a-z0-9][a-z0-9-_]{0,61}[a-z0-9]{0,1}\\.(xn--)?([a-z0-9\\-]{1,61}|[a-z0-9-]{1,30}\\.[a-z]{2,})$", message = "Required format example: domainname.com")
    private String domain;

    @ManyToMany(mappedBy = "app_list")
    private Set<Person> user_list = new HashSet<>();

    private int number_of_users = 0;

    public List<Person> getUser_list() {
        List<Person> out = new ArrayList<>(user_list);
        return out;
    }

    public void setUser_list(List<Person> user_list) {
        Set<Person> out = new HashSet<>(user_list);
        this.user_list = out;
    }

    public Set<Person> getUser_listAsSet(){
        return user_list;
    }

    public void setNumberOfUsers(){
        int count = 0;
        for(Person per : user_list){
            count++;
        }
        this.number_of_users = count;
    }
}
