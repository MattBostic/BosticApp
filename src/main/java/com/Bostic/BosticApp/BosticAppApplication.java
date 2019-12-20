package com.Bostic.BosticApp;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BosticAppApplication {

	@Autowired
	PostRepository postRepository;
	@Autowired
	AccountCredentialsRepository accountCredentialsRepository;
	public static void main(String[] args) {
		SpringApplication.run(BosticAppApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(){
		return args -> {
			//Send data to H2 database
			Post post = new Post("First post in app", "HappyTimes.jpg");
			Post post1 = new Post("Second post in app", "DeckerCrying.jpg");
			postRepository.save(post);
			postRepository.save(post1);


			AccountCredentials accountCredentials = new AccountCredentials(
					"Admin", "Admin", "admin");
			AccountCredentials accountCredentials1 = new AccountCredentials(
					"User", "User", "user");
			accountCredentialsRepository.save(accountCredentials);
			accountCredentialsRepository.save(accountCredentials1);
		};
	}
}
