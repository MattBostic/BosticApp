package com.Bostic.BosticApp.controller;

import com.Bostic.BosticApp.domains.JWTBlacklist;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import com.Bostic.BosticApp.security.TokenAuthentication;
import com.Bostic.BosticApp.service.CookieService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

import static com.Bostic.BosticApp.security.SecurityConstants.SECRET;

// TODO: change direct dependency for the JWT repository.
//  Repo service needs to be injected to decouple the
//  controller from direct database calls.
@Controller
public class LogoutController implements LogoutHandler {

    private JWTBlacklistRepository jwtBlacklistRepository;
    private TokenAuthentication tokenAuthentication;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public LogoutController(JWTBlacklistRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    @Override
    public void logout (HttpServletRequest req, HttpServletResponse res,
                        Authentication authentication) {
        try {
            invalidateSession(req);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void invalidateSession(HttpServletRequest request) throws ServletException {
        if (request.getSession() != null && request.getCookies().length > 1) {
            String token = new CookieService(request.getCookies()).getValue();

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
            .build()
            .verify(token);
            jwtBlacklistRepository.save(new JWTBlacklist(decodedJWT.getToken(), new Date(System.currentTimeMillis())));
            request.getSession().invalidate();
            request.logout();

        }
    }


}
