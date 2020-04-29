package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;

public abstract class AccountManagementService {
    AccountCredentials account;

    public AccountManagementService() {
    }

    public AccountManagementService(AccountCredentials account) {
        this.account = account;
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
