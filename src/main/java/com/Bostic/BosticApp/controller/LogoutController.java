package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.security.SecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoutController implements LogoutSuccessHandler {

    @Autowired
    private SecurityConfig securityConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws ServletException {
        logger.debug("Performing logout");
        invalidateSession(req);
        System.out.println(req.getAuthType());
        String returnTo = req.getScheme() + "://" + req.getServerName();
        if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
            returnTo += ":" + req.getServerPort();
        }
        returnTo += "/";

        try {
            res.sendRedirect(returnTo);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void invalidateSession(HttpServletRequest request) throws ServletException {
        if (request.getSession() != null) {
            request.getSession().invalidate();
            request.logout();
        }
    }

}
