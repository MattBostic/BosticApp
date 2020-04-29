package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;

public class RevokeAccount extends AccountManagementService {
    private AccountCredentialsRepository accountRepo;

    public RevokeAccount(AccountCredentialsRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public RevokeAccount(AccountCredentials account, AccountCredentialsRepository accountRepo) {
        super(account);

        this.accountRepo = accountRepo;
    }

    @Override
    boolean isInDatabase() {
        if(hasAccount() && account.getId() != 0) return accountRepo.existsById(account.getId());
        return false;
    }

    public void deleteAccount(){
        if (isInDatabase()){
            accountRepo.delete(getAccount());
        }
    }

    public void removeAdminAuthority(){
        if (hasAccount() && getAccount().getRole().equals("admin")){
             if(isInDatabase()){
                 getAccount().setRole("user");
                 accountRepo.save(getAccount());
             }
        }
    }
}
