package com.hackthon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.hackthon.modele"})
@EnableJpaRepositories(basePackages = {"com.hackthon.repository"})
@EnableJpaAuditing
public class hackthonApp {

	public static void main(String[] args) {
		SpringApplication.run(hackthonApp.class, args);
	}
}