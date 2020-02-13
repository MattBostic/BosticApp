package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.ImageSaver;
import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Flux;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

//Class will handle all aspects of blog page mapping
@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    private ImageSaver imageSaver = new ImageSaver();
    private String uploadPath = "src\\main\\resources\\static\\images\\";


    @GetMapping(value = "/i")
    @CrossOrigin(origins = "http://localhost:3000")
    public Flux<Post> getPosts(){
        return Flux.fromIterable(postRepository.
                findAll()).map(post -> {
                    System.out.println(post.getBody());
            return post;
        });
    }


    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("images", postRepository.findAllDESC());
        return "index.html";

    }


    //Add new post elements to database.

    @PostMapping(value = "/posts")
    public ResponseEntity<Post> update(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.accepted().body(post);
    }

    @GetMapping(value = "/image/")
    public ResponseEntity<byte[]> getImage(@RequestParam("image_id") Long imageId) throws SQLException {

        Optional<Post> post = postRepository.findById(imageId);
        if(Objects.isNull(post)) return null;

        final HttpHeaders headers = new HttpHeaders();
        Blob image = post.get().getImage();
        byte[] bytes = image.getBytes(1, (int) image.length());
        String format = post.get().getImageFormat();

        if(format.equals("png")){
            headers.setContentType(MediaType.IMAGE_PNG);
        }else {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/imgUpload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("text") String text,
                                   RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/";
        }

        String contentType = file.getContentType().split("/")[0];
        String formatType = "." + file.getContentType().split("/")[1];

        if(!contentType.equals("image")){
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/1";
        }

        try {
            Post post = new Post(text, file, formatType);
            postRepository.save(post);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        return "redirect:/";
    }

    @PostMapping(value = "remove/{id}")
    public String remove(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        postRepository.deleteById(id);
        return "redirect:/";
    }

}
