package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {


    @Autowired
    private SecurityConfig securityConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login")
    protected String login() {
        return "login.html";
    }

    @PostMapping(value = "/login")
    protected String success(){
        return "/index.html";
    }



}
