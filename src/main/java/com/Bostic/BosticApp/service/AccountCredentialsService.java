package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AccountCredentialsService {

    private AccountCredentialsRepository accountRepo;

    @Autowired
    public AccountCredentialsService(AccountCredentialsRepository accountRepo) {
        this.accountRepo = accountRepo;
    }


    public Iterable<AccountCredentials> findAll(){
        return this.accountRepo.findAll();
    }

    public Optional<AccountCredentials> findById(Long id){
        return accountRepo.findById(id);
    }

    Boolean usernameExists(AccountCredentials accountCredentials){
        return accountRepo.existsByUsername(accountCredentials.getUsername());
    }

    void save(final AccountCredentials account){
        accountRepo.save(account);
    }

    void delete(AccountCredentials account) {
        accountRepo.delete(account);
    }
}
