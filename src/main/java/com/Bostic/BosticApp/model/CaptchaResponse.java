package com.Bostic.BosticApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/*
 *  Model used to crate object of Google ReCAPTCHA API response.
 */

public class CaptchaResponse {
    private Boolean success;
    private float score;
    private Date challenge_ts;
    private String hostname;
    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public CaptchaResponse() {
    }

    public CaptchaResponse(Boolean success, Date challenge_ts, String hostname, List<String> errorCodes) {
        this.success = success;
        this.challenge_ts = challenge_ts;
        this.hostname = hostname;
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "CaptchaResponse{" +
                "success=" + success +
                ", score=" + score +
                ", challenge_ts=" + challenge_ts +
                ", hostname='" + hostname + '\'' +
                ", errorCodes=" + errorCodes +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public Date getTimeStamp() {
        return challenge_ts;
    }

    public float getScore(){
        return score;
    }

    public String getHostname() {
        return hostname;
    }



    public List<String> getErrorCodes() {
        return errorCodes;
    }

}
