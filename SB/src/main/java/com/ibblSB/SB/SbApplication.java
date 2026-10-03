package com.ibblSB.SB;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ibblSB.SB.repository")
public class SbApplication {


	public static void main(String[] args) {

		SpringApplication.run(SbApplication.class, args);

	}

}