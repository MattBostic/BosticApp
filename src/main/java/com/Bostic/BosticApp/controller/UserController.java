package com.Bostic.BosticApp.controller;


import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import com.Bostic.BosticApp.service.ImplementAccount;
import com.Bostic.BosticApp.service.RevokeAccount;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//ADMIN ACCESS ONLY!
@Controller
@RequestMapping("/user-accounts")
public class UserController {
    private AccountCredentialsRepository accountRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AccountCredentialsRepository accountRepo, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.accountRepo = accountRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/accounts")
    public String activeAccounts(Model model){
        model.addAttribute("accounts", accountRepo.findAll());
        return "user-accounts.html";
    }

    // Still uses @RequestBody (JSON). will not work with web forms.
    @PostMapping(value = "/create")
    public void createAccount(HttpServletRequest request, @RequestBody AccountCredentials account){

        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        ImplementAccount implementAccount = new ImplementAccount(account, accountRepo);
        implementAccount.createNewAccount();
    }

    @PostMapping(value = "/delete")
    public String deleteAccounts(@RequestParam("account") List<String> accountIds) {
        System.out.println(accountIds.isEmpty());
        RevokeAccount revokeAccount = new RevokeAccount(accountRepo);
        for (String accountId : accountIds) {
            accountRepo.findById(Long.valueOf(accountId)).ifPresent(revokeAccount::setAccount);
            System.out.println("Deleted!");
            revokeAccount.deleteAccount();

        }
        return "redirect:accounts";
    }
}
