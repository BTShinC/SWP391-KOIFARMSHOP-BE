package com.example.SWP391_KOIFARMSHOP_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.example.SWP391_KOIFARMSHOP_BE.controller","com.example.SWP391_KOIFARMSHOP_BE.service"})
@EnableJpaRepositories(basePackages = "com.example.SWP391_KOIFARMSHOP_BE.repository")
@EntityScan(basePackages = "com.example.SWP391_KOIFARMSHOP_BE.pojo")
@SpringBootApplication
public class Swp391KoifarmshopBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Swp391KoifarmshopBeApplication.class, args);
	}

}
