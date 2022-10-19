package com.rihaviour.Bank_API_REST.controllers;

import com.rihaviour.Bank_API_REST.controllers.interfaces.AccountControllerInterface;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Transaction;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.services.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController implements AccountControllerInterface {

    @Autowired
    AccountServiceInterface accountService;

    @GetMapping("/get_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();

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

    @PatchMapping("/transfer_funds")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transaction transferFunds(@RequestBody Transaction transaction){
        return accountService.transferFunds(transaction);
    }




}
