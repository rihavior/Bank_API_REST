package com.rihaviour.Bank_API_REST.controllers;

import com.rihaviour.Bank_API_REST.controllers.interfaces.CheckingControllerInterface;
import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CheckingController implements CheckingControllerInterface {

    @Autowired
    AccountServiceInterface accountService;

    @GetMapping("/get_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();

    }

//    @PostMapping("/create_checking/{startingBalance}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Account createChecking(@PathVariable double startingBalance, @RequestBody AccountHolder primaryOwner) {
//        return accountService.createChecking(startingBalance, primaryOwner);
//    }

    @PostMapping("/create_checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createChecking(@RequestBody AccountDTO accountDTO) {
        return accountService.createChecking(accountDTO);
    }

//    @PatchMapping("/products/updateQuantity/{id}/{quantity}")
//    @ResponseStatus(HttpStatus.OK)
//    public Product updateProductQuantity(@PathVariable Long id, @PathVariable int quantity) {
//        return productService.updateProductQuantity(id, quantity);
//    }



}
