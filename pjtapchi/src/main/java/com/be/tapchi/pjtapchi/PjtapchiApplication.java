package com.be.tapchi.pjtapchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // cho phep chay tu dong theo tg
public class PjtapchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjtapchiApplication.class, args);
	}

}
