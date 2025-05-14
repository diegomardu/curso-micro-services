package io.github.diegomardu.clientapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApiApplication.class, args);
	}

}
