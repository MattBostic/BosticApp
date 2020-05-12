package com.Bostic.BosticApp.controller;


import com.Bostic.BosticApp.domains.AccountCredentials;
import com.Bostic.BosticApp.domains.AccountCredentialsRepository;
import com.Bostic.BosticApp.service.AccountCredentialsService;
import com.Bostic.BosticApp.service.ImplementAccount;
import com.Bostic.BosticApp.service.RevokeAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

//ADMIN ACCESS ONLY!
@Controller
@RequestMapping("/user-accounts")
public class UserController {
    private AccountCredentialsRepository accountRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AccountCredentialsService credentialsService;
    private ImplementAccount implementAccount;
    private RevokeAccount revokeAccount;

    private AccountCredentials accountCredentials = new AccountCredentials();

    @Autowired
    public UserController(AccountCredentialsRepository accountRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
                          AccountCredentialsService credentialService, ImplementAccount implementAccount,
                          RevokeAccount revokeAccount){
        this.accountRepo = accountRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.credentialsService = credentialService;
        this.implementAccount = implementAccount;
        this.revokeAccount = revokeAccount;
    }

    @GetMapping(value = "/accounts")
    public String activeAccounts(Model model){
        AccountCredentials accountCredentials = new AccountCredentials();
        model.addAttribute("account", accountCredentials);
        model.addAttribute("accounts", credentialsService.findAll());
        return "user-accounts.html";
    }

    // Still uses @RequestBody (JSON). will not work with web forms.
    @PostMapping(value = "/create-account")
    public void createAccountJSON(HttpServletRequest request, @RequestBody AccountCredentials account){


    }

    @PostMapping(value = "/create")
    public String createAccount(Model model, @ModelAttribute("account") AccountCredentials account){
        model.addAttribute("account", accountCredentials);
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        implementAccount.setAccount(account);
        implementAccount.createNewAccount();
        return "redirect:accounts";
    }

    @PostMapping(value = "/delete")
    public String deleteAccounts(@RequestParam(value = "account", required = false) List<String> accountIds) {
        Long id;
            /*
            TODO: Create a redirect error event for no account selected.
             Frontend will also block posts to /delete with null values.
             */
        if(Objects.isNull(accountIds)) return "redirect:accounts";

        for (String accountId : accountIds) {
            try {
                id = Long.parseLong(accountId);
                credentialsService.findById(id).ifPresent(revokeAccount::setAccount);
                revokeAccount.deleteAccount();
                System.out.println("Deleted!");

            }catch (NumberFormatException nfe){
                // TODO: Log NFE and send to an error page.
            }
        }
        return "redirect:accounts";
    }
}
