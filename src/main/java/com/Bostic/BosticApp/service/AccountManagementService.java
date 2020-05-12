package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;


public abstract class AccountManagementService {
    protected AccountCredentialsService accountService;
    protected AccountCredentials account;

    AccountManagementService(){
    }


    public void setAccount(AccountCredentials account) {
        this.account = account;
    }

    AccountCredentials getAccount() {
            return account;
    }

    boolean hasAccount(){
        return account != null;
    }

    abstract boolean isInDatabase();
}
