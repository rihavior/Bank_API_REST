package com.rihavior.Bank_API_REST.controllers.interfaces;

import com.rihavior.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihavior.Bank_API_REST.entities.accounts.Transaction;
import com.rihavior.Bank_API_REST.entities.accounts.Account;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;

import java.util.List;
import java.util.Optional;

public interface AccountControllerInterface {


//    Account createChecking(double startingBalance, AccountHolder primaryOwner);

    Account createChecking(AccountDTO accountDTO);

    Account createSavings(AccountDTO accountDTO);

    Account createCreditCard(AccountDTO accountDTO);

    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);

    Transaction transferFunds(Transaction transaction);

    Optional<Account> getAccount(Long accountId);

    Account modifyBalance (AccountDTO accountDTO);

    List<Account> getAllAccounts();
}