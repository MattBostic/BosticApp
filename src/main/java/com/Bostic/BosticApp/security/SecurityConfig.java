package com.Bostic.BosticApp.security;

import com.Bostic.BosticApp.controller.LogoutController;
import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value(value = "${com.auth0.domain}")
    private String domain;
    @Value(value = "${com.auth0.clientId}")
    private String clientId;
    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutController();
    }
    @Bean
    public AuthenticationController authenticationController() {
        JwkProvider jwkProvider = new JwkProviderBuilder(domain).build();
        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .withJwkProvider(jwkProvider)
                .build();

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http
            .authorizeRequests()
            .antMatchers("/callback", "/login", "/", "/*.png", "/css/**", "/js/")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();
    }

    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    //    @Autowired
//    private
//    UserDetailServiceImpl userDetailsService;
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/login").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new LoginFilter("/login",
//                        authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//// Filter for the api/login requests
//                .addFilterBefore(new LoginFilter("/login ",
//                                authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//// Filter for other requests to check JWT in header
//                .addFilterBefore(new AuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
//    }
//
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowCredentials(true);
//        config.applyPermitDefaultValues();
//
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}
