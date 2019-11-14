package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Class will handle all aspects of blog page mapping
@RestController
public class PostController {
    //
    @Autowired
    private PostRepository postRepository;

    @RequestMapping(value = "/blog")
    public Iterable<Post> getPosts(){
        return postRepository.findAll();
    }
}
