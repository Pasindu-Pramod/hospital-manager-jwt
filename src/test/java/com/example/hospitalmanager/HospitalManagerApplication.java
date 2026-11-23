package com.example.hospitalmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.hospitalmanager.repository")
public class HospitalManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagerApplication.class, args);
    }

}