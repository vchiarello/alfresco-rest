package it.sara.alfrescorest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AlfrescoRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlfrescoRestApplication.class, args);
	}

}
