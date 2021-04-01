package com.Bostic.BosticApp.security;

import com.Bostic.BosticApp.controller.LogoutController;
import com.Bostic.BosticApp.domains.JWTBlacklistRepository;
import com.Bostic.BosticApp.service.AccountDetailsService;
import com.Bostic.BosticApp.service.AuthorityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JWTBlacklistRepository jwtBlacklistRepo;
    private AccountDetailsService accountService;
    private AuthorityService authorityService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public SecurityConfig(JWTBlacklistRepository jwtBlacklistRepo, AccountDetailsService accountService,
                          AuthorityService authorityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtBlacklistRepo = jwtBlacklistRepo;
        this.accountService = accountService;
        this.authorityService = authorityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public LogoutHandler logoutHandler(){
        return new LogoutController( jwtBlacklistRepo);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().cors()

            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/userAccounts/**", "/imgUpload/**").hasRole("ADMIN")
            .antMatchers("/home").hasRole("USER")
            .anyRequest()
            .authenticated()
            .and()

            .addFilter(new JWTAuthenticationFilter(authenticationManager(), authorityService))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtBlacklistRepo))
            .addFilterAfter(new BlackListFilter(jwtBlacklistRepo), JWTAuthorizationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .formLogin().loginPage("/login")
            .defaultSuccessUrl("/home", true)
            .permitAll()

            .and()
            .logout().addLogoutHandler(logoutHandler())
            .logoutSuccessUrl("/").deleteCookies("Authorization");

    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity.ignoring().antMatchers( "/css/**", "/js/**", "/img/**");
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
