package com.wipro.eurekaservicedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
//@EnableDiscoveryClient
public class EurekaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceApplication.class, args);
		System.out.println("---Eureka Service 1 is up and running at port number 6600---");
	}

}
