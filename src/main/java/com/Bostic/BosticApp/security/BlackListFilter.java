package com.Bostic.BosticApp.security;

import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.Bostic.BosticApp.security.SecurityConstants.*;

/*
 * Filter used to check if the authentication token sent was previously
 * Blacklisted. This is needed it invalidate tokens that have not expired yet.
 * Examples could be users logging out or a password change.

 * */


public class BlackListFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(BlackListFilter.class);
    private
    JWTBlacklistRepository jwtBlacklistRepository;

    BlackListFilter(JWTBlacklistRepository jwtBlacklistRepository) {
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
                         throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader(HEADER_STRING);

        if(token != null) {

            try{

                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));

                TokenAuthentication attributes = new TokenAuthentication(decodedJWT, jwtBlacklistRepository);

                if (attributes.isInBlacklist()) {
                    return;
                }

            }catch (TokenExpiredException e){
                logger.debug("Ohh no" + e.getMessage());
                return;

            }catch (JWTDecodeException e){
                logger.debug(e.getMessage());
            }

        }

        chain.doFilter( request, response);
    }


}
