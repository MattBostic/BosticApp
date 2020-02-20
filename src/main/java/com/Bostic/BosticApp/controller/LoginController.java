package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.security.SecurityConfig;
import com.auth0.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
@Controller
public class LoginController {

    @Autowired
    private AuthenticationController controller;

    @Autowired
    private SecurityConfig securityConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(final HttpServletRequest request, final HttpServletResponse res) {
        String redirectUri = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80) ||
                (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            redirectUri += ":" + request.getServerPort();
        }
        redirectUri += "/callback";

        String authorizeUrl = controller.buildAuthorizeUrl(request, res, redirectUri)
                .withScope("openid profile email")
                .build();
        return "redirect:" + authorizeUrl;
    }

}
