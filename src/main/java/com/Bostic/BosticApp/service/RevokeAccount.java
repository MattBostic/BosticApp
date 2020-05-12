package com.Bostic.BosticApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RevokeAccount extends AccountManagementService {
    private AccountCredentialsService accountService;

    @Autowired
    public RevokeAccount( AccountCredentialsService accountService) {
        super();
        this.accountService = accountService;
    }

    @Override
    boolean isInDatabase() {
        if(!hasAccount()) return false;
        return accountService.usernameExists(getAccount());
    }

    public void deleteAccount(){
        if (hasAccount() && isInDatabase()){
            accountService.delete(getAccount());
        }
    }

    public void removeAdminAuthority(){
        if (hasAccount() && getAccount().getRole().equals("admin")){
             if(isInDatabase()){
                 getAccount().setRole("user");
                 accountService.save(getAccount());
             }
        }
    }
}
