package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImplementAccount extends AccountManagementService {
    private AccountCredentialsDao accountService;

    @Autowired
    public ImplementAccount( AccountCredentialsDao accountService) {
        super();
        this.accountService = accountService;
    }


    @Override
    public boolean isInDatabase() {
        if(!hasAccount()) return false;
        return accountService.usernameExists(getAccount());
    }


    public void createNewAccount(){
        if (!isInDatabase()){

            AccountCredentials savedAccount = new AccountCredentials(getAccount().getUsername(),
                    getAccount().getPassword());

            accountService.save(savedAccount);
        }
    }

    public void changePassword(String password){
        if(isInDatabase()){
            getAccount().setPassword(password);
            accountService.save(getAccount());
        }
    }
}
