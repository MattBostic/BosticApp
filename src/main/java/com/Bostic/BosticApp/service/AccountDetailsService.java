package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AccountDetailsService implements UserDetailsService {
    private AccountCredentialsRepository accountRepo;
    private String ROLE_PREFIX = "ROLE_";

    public AccountDetailsService(AccountCredentialsRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountCredentials account = accountRepo.findByUsername(username);

        if (account == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(account.getUsername(), account.getPassword(), getAuthorities(account.getRole().equals("admin")));
    }

    private ArrayList<GrantedAuthority> getAuthorities(boolean isAdmin){
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "USER"));
        if (isAdmin){
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "ADMIN"));
        }
        return  grantedAuthorities;
    }

}
