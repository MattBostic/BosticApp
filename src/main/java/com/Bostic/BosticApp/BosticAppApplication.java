package com.Bostic.BosticApp;

import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication

public class BosticAppApplication {
	@Autowired
	private
	AccountCredentialsRepository accountCredentialsRepository;
	@Autowired
	private JWTBlacklistRepository jwtBlacklistRepository;

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

		};
	}

}
