package com.wipro.eurekaservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
//@RequestMapping()
public class ServiceController {
	
	//@Autowired
	//RestTemplate restTemplate;
	
	@GetMapping("/greet")
	public String greetString() {
		return "Hello Greeting Controller from service 1 app";
	}

}
