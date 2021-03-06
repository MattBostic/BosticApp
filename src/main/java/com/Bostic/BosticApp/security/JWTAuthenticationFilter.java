package com.Bostic.BosticApp.security;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.service.AuthorityService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static com.Bostic.BosticApp.security.SecurityConstants.EXPIRATION_TIME;
import static com.Bostic.BosticApp.security.SecurityConstants.SECRET;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;
    private AuthorityService authorityService;

    public JWTAuthenticationFilter(AuthenticationManager authManager, AuthorityService authorityService){
        this.authManager = authManager;
        this.authorityService = authorityService;
    }

    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        super.setDetails(request, authRequest);
    }



    //Try block gets the HTTP request's input stream and uses AccountsCredentials.class to build an object
    //of the user account submitted.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            // Request is sent as formData. The request is split by username/password then
            // substringed. This has opportunity for improvement.
            String[] list;
            String string = request.getReader().lines().collect(Collectors.joining());
           list = string.split("&");

            AccountCredentials credentials  = new AccountCredentials(list[0].substring(9), list[1].substring(9));

            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            authorityService.setAuthorities(credentials)
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    //When Account is validated this method is called to create a new JsonWebToken for the user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withClaim("isAdmin",
                        authResult.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .withExpiresAt(new Date(System.currentTimeMillis() +  EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.addCookie(new Cookie("Authorization", token));
        response.sendRedirect("/home");
    }
}
