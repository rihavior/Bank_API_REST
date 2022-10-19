package com.rihaviour.Bank_API_REST.services.interfaces;

import com.rihaviour.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

import java.util.List;

public interface AccountServiceInterface {

//    Account createChecking(double startingBalance, AccountHolder primaryOwner);

    Account createChecking(AccountDTO accountDTO);

    Account createSavings(AccountDTO accountDTO);

    Account createCreditCard(AccountDTO accountDTO);

    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);

    List<Account> getAllAccounts();
}
