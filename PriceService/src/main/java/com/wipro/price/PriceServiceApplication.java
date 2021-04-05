package com.wipro.price;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.wipro.price.configuration.RibbonConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@RibbonClient(name="server", configuration=RibbonConfiguration.class)
public class PriceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceServiceApplication.class, args);
		System.out.println("### Price Service Up and Running at port number 6400 ###");
	}

}
