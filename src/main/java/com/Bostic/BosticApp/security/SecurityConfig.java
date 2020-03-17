package com.Bostic.BosticApp.security;

import com.Bostic.BosticApp.controller.LogoutController;
import com.Bostic.BosticApp.service.AccountDetailsService;
import com.Bostic.BosticApp.service.AuthorityService;
import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AccountDetailsService accountService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthorityService authorityService;



    public SecurityConfig(AccountDetailsService accountService, AuthorityService authorityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountService = accountService;
        this.authorityService = authorityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutController();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.cors()
            .and()
            .authorizeRequests()
            .antMatchers("/userAccounts/**").hasRole("ADMIN")
            .antMatchers("/**").hasRole("USER")
            .antMatchers("/callback", "/login", "/", "/img/*.jpg", "/css/**", "/js/")
            .permitAll().anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), authorityService))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
