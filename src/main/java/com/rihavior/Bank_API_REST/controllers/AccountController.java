package com.rihavior.Bank_API_REST.controllers;

import com.rihavior.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihavior.Bank_API_REST.entities.accounts.Transaction;
import com.rihavior.Bank_API_REST.entities.users.ThirdParty;
import com.rihavior.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihavior.Bank_API_REST.controllers.interfaces.AccountControllerInterface;
import com.rihavior.Bank_API_REST.entities.accounts.Account;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController implements AccountControllerInterface {

    @Autowired
    AccountServiceInterface accountService;

    @GetMapping("/get_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/get_account/{accountId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Account> getAccount(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }


    @PostMapping("/create_checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createChecking(@RequestBody AccountDTO accountDTO) {
        return accountService.createChecking(accountDTO);
    }

    @PostMapping("/create_savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavings(@RequestBody AccountDTO accountDTO) {
        return accountService.createSavings(accountDTO);
    }

    @PostMapping("/create_credit_card")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditCard(@RequestBody AccountDTO accountDTO) {
        return accountService.createCreditCard(accountDTO);
    }

    @PostMapping("/create_account_holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody AccountHolderDTO accountHolderDTO) {
        return accountService.createAccountHolder(accountHolderDTO);
    }

    @PostMapping("/create_third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody ThirdParty thirdParty) {
        return accountService.createThirdParty(thirdParty);
    }

    @PatchMapping("/transfer_funds")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction transferFunds(@RequestBody Transaction transaction){
        return accountService.transferFunds(transaction);
    }

    @PatchMapping("/modify_balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account modifyBalance (@RequestBody AccountDTO accountDTO){
        return accountService.modifyBalance(accountDTO);
    }
}
