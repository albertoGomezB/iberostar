package com.agb.w2w_iberostar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class W2wIberostarApplication {

	public static void main(String[] args) {
		SpringApplication.run(W2wIberostarApplication.class, args);
	}

}
