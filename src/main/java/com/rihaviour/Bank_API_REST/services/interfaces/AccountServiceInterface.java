package com.rihaviour.Bank_API_REST.services.interfaces;

import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountServiceInterface {

//    Account createChecking(double startingBalance, AccountHolder primaryOwner);

    Account createChecking(AccountDTO accountDTO);

    Account createSavings(AccountDTO accountDTO);

    List<Account> getAllAccounts();
}