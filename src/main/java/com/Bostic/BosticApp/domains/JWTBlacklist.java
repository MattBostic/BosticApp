package com.Bostic.BosticApp.domains;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Date;



public class JWTBlacklist {

    @Id
    @Column(nullable = false, updatable = false)
    private String JWTsignature;

    @Column(nullable = false)
    private Date blacklistedTime;

    public JWTBlacklist() {
    }

    public JWTBlacklist(String JWTsignature, Date blacklistedTime) {
        this.JWTsignature = JWTsignature;
        this.blacklistedTime = blacklistedTime;
    }

    public String getJWTsignature() {
        return JWTsignature;
    }

    public void setJWTsignature(String JWTsignature) {
        this.JWTsignature = JWTsignature;
    }

    public Date getBlacklistedTime() {
        return blacklistedTime;
    }

    public void setBlacklistedTime(Date blacklistedTime) {
        this.blacklistedTime = blacklistedTime;
    }
}
