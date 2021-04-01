package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Iterable<Post> findAll(){
        return postRepository.findAll();
    }

    public Iterable<Post> findAllDESC(){
        return postRepository.findAllDESC();
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    public void save(Post post){
        postRepository.save(post);
    }
}
