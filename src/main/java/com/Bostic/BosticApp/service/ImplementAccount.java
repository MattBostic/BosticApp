package com.Bostic.BosticApp.service;

import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;

public class ImplementAccount extends AccountManagementService {
     private AccountCredentialsRepository accountRepo;

    public ImplementAccount(AccountCredentialsRepository accountRepo) {
        super();
        this.accountRepo = accountRepo;
    }

    public ImplementAccount(AccountCredentials account,
                            AccountCredentialsRepository accountRepo) {
        super(account);
        this.accountRepo = accountRepo;
    }

    @Override
    boolean isInDatabase() {
        if(hasAccount() && account.getId() != 0) return accountRepo.existsById(account.getId());
        return false;
    }


    public void createNewAccount(){
        if (!isInDatabase()){

            AccountCredentials savedAccount = new AccountCredentials(getAccount().getUsername(),
                    getAccount().getPassword());

            accountRepo.save(savedAccount);
        }
    }

    public void changePassword(String password){
        if(isInDatabase()){
            getAccount().setPassword(password);
            accountRepo.save(getAccount());
        }
    }
}
