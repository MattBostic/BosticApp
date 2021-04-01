package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.ImageSaver;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import com.Bostic.BosticApp.domains.Post;
import com.Bostic.BosticApp.domains.PostRepository;
import com.Bostic.BosticApp.security.TokenAuthentication;
import com.Bostic.BosticApp.service.AccountCredentialsDao;
import com.Bostic.BosticApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

// TODO: Change direct dependency for the Post/JWT repository.
//  Repo services needs to be injected to decouple the
//  controller from direct database calls.

// TODO: LogoutController is passed a repo during object construction.
//  note to self -> that is funky, leave it up to Spring.

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository;
    private AccountCredentialsDao credentialsService;
    @Autowired
    private PostService postService;
    @Autowired
    private JWTBlacklistRepository jwtBlacklistRepository;
    private ImageSaver imageSaver = new ImageSaver();
    private LogoutController logout = new LogoutController(jwtBlacklistRepository);


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    protected void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth)
            throws ServletException {
        System.out.println("Logged out");

        logout.logout(request, response, auth);

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    protected String home(final Model model, final Authentication authentication) {
        if (authentication instanceof TokenAuthentication) {
            TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
            model.addAttribute("profile", tokenAuthentication.getClaims());
        }

        return "landing.html";
    }

    @GetMapping(value = "/home")
    public String index(Model model) {
        model.addAttribute("images", postService.findAllDESC());
        return "index.html";

    }

    //Add new post elements to database.

    @PostMapping(value = "/posts")
    public ResponseEntity<Post> update(@RequestBody Post post) {
        postService.save(post);
        return ResponseEntity.accepted().body(post);
    }

    @GetMapping(value = "/image/")
    public ResponseEntity<byte[]> getImage(@RequestParam("image_id") Long imageId) throws SQLException {

        Optional<Post> post = postService.findById(imageId);

        final HttpHeaders headers = new HttpHeaders();
        if( post.isPresent()) {
            Blob image = post.get().getImage();
            byte[] bytes = image.getBytes(1, (int) image.length());
            String format = post.get().getImageFormat();

            if (format.equals("png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else {
                headers.setContentType(MediaType.IMAGE_JPEG);
            }
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }else return null;
    }

    @PostMapping(value = "/imgUpload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("text") String text,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/home";
        }

        String contentType = Objects.requireNonNull(file.getContentType()).split("/")[0];
        String formatType = "." + file.getContentType().split("/")[1];

        if(!contentType.equals("image")){
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/home";
        }

        try {
            Post post = new Post(text, file, formatType);
            postService.save(post);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        return "redirect:/home";
    }

    @PostMapping(value = "remove/{id}")
    public String remove(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        postService.deleteById(id);
        return "redirect:/home";
    }

}
