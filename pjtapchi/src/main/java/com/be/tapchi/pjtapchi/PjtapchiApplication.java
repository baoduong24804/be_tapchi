package com.be.tapchi.pjtapchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

//import com.be.tapchi.pjtapchi.fileservice.FileStorageProperties;

@SpringBootApplication
@EnableScheduling // cho phep chay tu dong theo tg
//@EnableConfigurationProperties(FileStorageProperties.class)
public class PjtapchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjtapchiApplication.class, args);
	}

}
