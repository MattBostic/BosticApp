package com.Bostic.BosticApp.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;

public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent AuthenticationEvent) {

    }
}
