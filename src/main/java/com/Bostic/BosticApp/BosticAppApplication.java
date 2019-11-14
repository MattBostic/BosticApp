package com.Bostic.BosticApp;

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
	public static void main(String[] args) {
		SpringApplication.run(BosticAppApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(){
		return args -> {
			Post post = new Post("First post in app", "HappyTimes.jpg");
			Post post1 = new Post("Second post in app", "DeckerCrying.jpg");
			postRepository.save(post);
			postRepository.save(post1);
		};
	}
}
