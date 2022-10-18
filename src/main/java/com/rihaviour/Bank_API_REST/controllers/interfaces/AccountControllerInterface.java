package com.rihaviour.Bank_API_REST.controllers.interfaces;

import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountControllerInterface {


//    Account createChecking(double startingBalance, AccountHolder primaryOwner);

    Account createChecking(AccountDTO accountDTO);

    Account createSavings(AccountDTO accountDTO);

    Account createCreditCard(AccountDTO accountDTO);

    List<Account> getAllAccounts();
}
