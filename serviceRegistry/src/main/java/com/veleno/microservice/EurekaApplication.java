package com.veleno.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

	public static void main(String[] args) {
		
		// fix -Deureka.enableSelfPreservation=false
		
		//System.setProperty("eureka.enableSelfPreservation", "false");
		SpringApplication.run(EurekaApplication.class, args);
	}
}
