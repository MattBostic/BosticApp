package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.model.CaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

   //TODO: Create Component to hold these two values. 
    private String reCAPTCHAEndPoint = "https://www.google.com/recaptcha/api/siteverify";
    @Value("${google.recaptcha.secret")
    private String reCAPTCHASecret = "6LeGXvcUAAAAAMTmbHXu0Rt5n6-0qyMqtjczmArc";

    public boolean validateCaptcha( String captchaResponse){
        RestTemplate template = new RestTemplate();

        // Create Map to send to google API.

        //Simple implementation of MultiValueMap that wraps a LinkedHashMap,
        //storing multiple values in a LinkedList.
        //It is primarily designed for data structures exposed
        // from request objects, for use in a single thread only. - spring.io
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", reCAPTCHASecret);
        requestMap.add("response", captchaResponse);

        CaptchaResponse apiResponse = template.postForObject(reCAPTCHAEndPoint, requestMap, CaptchaResponse.class);
        if (apiResponse == null) return false;

        return Boolean.TRUE.equals(apiResponse.getSuccess());

    }
}
