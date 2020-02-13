package com.Bostic.BosticApp.domains;

import com.Bostic.BosticApp.ImageSaver;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

//Post will hold all information pertaining to blog posts
@Entity(name = "postFrame")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //private Date date;
    private String body;
    private String imageFormat;
    private Blob image;





    public Post() {
    }

    public Post(String body, MultipartFile file, String imageFormat) throws IOException, SQLException {
        ImageSaver imageSaver = new ImageSaver();
        this.body = body;
        this.image = imageSaver.imageArray(file);
        this.imageFormat = imageFormat;
    }



    public long getId(){
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageFormat() { return imageFormat; }

    public void setImageFormat(String imageFormat) { this.imageFormat = imageFormat; }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}


