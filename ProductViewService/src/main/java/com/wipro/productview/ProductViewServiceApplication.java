package com.wipro.productview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.wipro.productview.configure.RibbonConfiguration;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableCircuitBreaker
@RibbonClient(name="server", configuration=RibbonConfiguration.class)
public class ProductViewServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductViewServiceApplication.class, args);
		System.out.println("### Product View Service is up and running ###");
	}

}
