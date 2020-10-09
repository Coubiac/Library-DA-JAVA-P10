package com.library.appliweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@SpringBootApplication
@EnableFeignClients("com.library")
@EnableDiscoveryClient
public class AppliwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppliwebApplication.class, args);
	}

}
