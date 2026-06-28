package com.example.ms_seguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsSeguridadApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSeguridadApplication.class, args);
	}

}
