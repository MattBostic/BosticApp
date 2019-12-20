package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//Class will handle all aspects of blog page mapping
@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;


    @GetMapping(value = "/posts")
    @CrossOrigin(origins = "http://localhost:3000")
    public Iterable<Post> getPosts(){
        return postRepository.findAll();
    }

    //Add new post elements to database.
    @RequestMapping(value = "/posts",
        method = RequestMethod.POST)
    public void update(@RequestBody Post post) {
        postRepository.save(post);
    }

}
