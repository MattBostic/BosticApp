package com.Bostic.BosticApp.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

//Post will hold all information pertaining to blog posts
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //private Date date;
    private String body, imgURL;

    public Post() {
    }

    public Post(String body, String imgURL) {
        this.body = body;
        this.imgURL = imgURL;
    }

    public Post(Date date, String body, String imgURL) {
        //this.date = date;
        this.body = body;
        this.imgURL = imgURL;
    }

    public long getId(){
        return id;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
