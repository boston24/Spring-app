package com.javaEE.project;

import com.javaEE.project.domain.Person;
import com.javaEE.project.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories
public class ProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjectApplication.class, args);

	}

}
