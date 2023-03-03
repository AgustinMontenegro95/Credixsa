package ar.com.dinamicaonline.credixsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CredixsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredixsaApplication.class, args);
	}

}
