package com.Bostic.BosticApp.security;


import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import com.Bostic.BosticApp.service.AuthorityService;
import com.Bostic.BosticApp.service.CookieService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.Bostic.BosticApp.security.SecurityConstants.SECRET;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private AuthorityService authorityService = new AuthorityService();
    @Autowired
    private JWTBlacklistRepository jwtBlacklistRepository;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTBlacklistRepository jwtBlacklistRepository) {
        super(authManager);
        this.jwtBlacklistRepository = jwtBlacklistRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        Cookie[] header = request.getCookies();

        if (header == null || header.length < 1){
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }



    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){

        String token = null;

        if(request.getCookies().length >= 1){
            token = new CookieService(request.getCookies()).getValue();
        }

        if (token != null){
            try{
                //parse token
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token);

                String user = decodedJWT.getSubject();
                Boolean claims = decodedJWT.getClaim("isAdmin").asBoolean();

                if (user != null){
                    return new UsernamePasswordAuthenticationToken(user,
                            null, authorityService.setAuthorities(claims));
                }
                return null;

            } catch (TokenExpiredException e){

            }catch (JWTVerificationException | IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
