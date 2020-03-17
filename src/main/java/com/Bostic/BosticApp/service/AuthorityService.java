package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthorityService {
    private AccountCredentialsRepository accountRepo;
    public AuthorityService() {
    }

    public ArrayList<GrantedAuthority> setAuthorities(AccountCredentials credentials){
        if(credentials == null){return new ArrayList<>();}
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (credentials.getRole().equals("admin")){

            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return grantedAuthorities;
    }

    public ArrayList<GrantedAuthority> setAuthorities(boolean isAdmin){
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (isAdmin){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return grantedAuthorities;
    }

}
