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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@PropertySources(value = {
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:auth0.properties")
})
public class BosticAppApplication {
	@Autowired
	private
	AccountCredentialsRepository accountCredentialsRepository;

	public static void main(String[] args) {

		SpringApplication.run(BosticAppApplication.class, args);
	}
	

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			System.out.println(accountCredentialsRepository.findByUsername("matt").getPassword());
		};
	}

}
