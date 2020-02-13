package com.Bostic.BosticApp.domains;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Blob;
import java.util.List;


public interface PostRepository extends CrudRepository<Post, Long> {

    @Query(value = "Select * FROM post_frame ORDER BY id DESC",
            nativeQuery = true)
    List<Post> findAllDESC();

}
