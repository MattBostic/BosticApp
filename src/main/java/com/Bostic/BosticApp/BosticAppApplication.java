package com.Bostic.BosticApp;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@SpringBootApplication
@PropertySources(value = {
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:auth0.properties")
})
public class BosticAppApplication {
	@Autowired
	AccountCredentialsRepository accountCredentialsRepository;

	public static void main(String[] args) {

		SpringApplication.run(BosticAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {

		AccountCredentials accountCredentials = new AccountCredentials("user",
				"$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi", "USER");
		accountCredentialsRepository.save(accountCredentials);
			System.out.println(accountCredentialsRepository.findByUsername("user").getPassword());
		};
	}

}
