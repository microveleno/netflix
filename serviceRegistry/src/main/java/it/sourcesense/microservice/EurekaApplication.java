package it.sourcesense.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceRegistryServerApplication {

	public static void main(String[] args) {
		
		// fix -Deureka.enableSelfPreservation=false
		
		//System.setProperty("eureka.enableSelfPreservation", "false");
		SpringApplication.run(EurekaServiceRegistryServerApplication.class, args);
	}
}
