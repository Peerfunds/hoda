package com.bizz_team.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.bizz_team.userservice.repository")
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(UserServiceApplication.class, args);

	}

}