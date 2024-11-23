package com.be.tapchi.pjtapchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//import com.be.tapchi.pjtapchi.fileservice.FileStorageProperties;

@SpringBootApplication
@EnableScheduling // cho phep chay tu dong theo tg
@EnableAsync
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
//@EnableConfigurationProperties(FileStorageProperties.class)
public class PjtapchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PjtapchiApplication.class, args);
	}

}
