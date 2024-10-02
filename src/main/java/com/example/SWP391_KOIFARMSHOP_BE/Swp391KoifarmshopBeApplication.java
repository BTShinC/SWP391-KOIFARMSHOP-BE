package com.example.SWP391_KOIFARMSHOP_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.example.SWP391_KOIFARMSHOP_BE.controller","com.example.SWP391_KOIFARMSHOP_BE.service",
		"com.example.SWP391_KOIFARMSHOP_BE.config","com.example.SWP391_KOIFARMSHOP_BE.model",
		"com.example.SWP391_KOIFARMSHOP_BE.exception","com.example.SWP391_KOIFARMSHOP_BE.api"})
@EnableJpaRepositories(basePackages = "com.example.SWP391_KOIFARMSHOP_BE.repository")
@EntityScan(basePackages = "com.example.SWP391_KOIFARMSHOP_BE.pojo")
@SpringBootApplication
public class Swp391KoifarmshopBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Swp391KoifarmshopBeApplication.class, args);
	}

}
