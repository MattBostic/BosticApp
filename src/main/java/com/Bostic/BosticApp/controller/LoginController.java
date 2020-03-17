package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {


    @Autowired
    private SecurityConfig securityConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/login")
    protected String login(final HttpServletRequest request, final HttpServletResponse res) {
        System.out.println("loggedIn");
        return "Matt";
    }


}
