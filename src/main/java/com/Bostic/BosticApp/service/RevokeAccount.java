package com.Bostic.BosticApp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RevokeAccount extends AccountManagementService {
    private AccountCredentialsDao accountDao;

    @Autowired
    public RevokeAccount( AccountCredentialsDao accountService) {
        super();
        this.accountDao = accountService;
    }

    @Override
    boolean isInDatabase() {
        if(!hasAccount()) return false;
        return accountDao.usernameExists(getAccount());
    }

    public void deleteAccount(){
        if (hasAccount() && isInDatabase()){
            accountDao.delete(getAccount());
        }
    }

    public void removeAdminAuthority(){
        if (hasAccount() && getAccount().getRole().equals("admin")){
             if(isInDatabase()){
                 getAccount().setRole("user");
                 accountDao.save(getAccount());
             }
        }
    }
}
