package com.Bostic.BosticApp.controller;


import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userAccounts")
public class UserController {
    private AccountCredentialsRepository accountRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AccountCredentialsRepository accountRepo,
                          BCryptPasswordEncoder bCryptPasswordEncoder){
        this.accountRepo = accountRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //ADMIN ACCESS ONLY! handle is used to create new accounts for application.
    @PostMapping("/create")
    public void createAccount(HttpServletRequest request, @RequestBody AccountCredentials account){
        System.out.println("Pizza");
//        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
//        accountRepo.save(account);
    }
}
