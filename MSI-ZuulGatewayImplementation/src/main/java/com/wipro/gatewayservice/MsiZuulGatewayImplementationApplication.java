package com.wipro.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.wipro.gatewayservice.filter.ErrorFilter;
import com.wipro.gatewayservice.filter.PostFilter;
import com.wipro.gatewayservice.filter.PreFilter;
import com.wipro.gatewayservice.filter.RouteFilter;


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class MsiZuulGatewayImplementationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsiZuulGatewayImplementationApplication.class, args);
		System.out.println("--- Zuul API Gateway Up and Ready---");
	}
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}
	
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}

}
