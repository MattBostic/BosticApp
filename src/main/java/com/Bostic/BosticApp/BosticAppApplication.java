package com.Bostic.BosticApp;

import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import com.Bostic.BosticApp.domains.JWTBlacklist;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;


@SpringBootApplication
@PropertySources(value = {
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:auth0.properties")
})
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
			 BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			jwtBlacklistRepository.save(new JWTBlacklist("sdafe", new Date(System.currentTimeMillis())));
			System.out.println(jwtBlacklistRepository.existsById("sdafe"));

		};
	}

}
