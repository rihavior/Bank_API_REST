package com.rihaviour.Bank_API_REST.services;

import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.Account;
import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.Savings;
import com.rihaviour.Bank_API_REST.entities.accounts.StudentChecking;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.*;
import com.rihaviour.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    UserRepository userRepository;

    public Account createChecking(AccountDTO accountDTO) {

        Checking checking = new Checking();
        StudentChecking studentChecking = new StudentChecking();

        //todo    ESTO HACE FALTA?
        checking.setSecondaryOwner(null);
        studentChecking.setSecondaryOwner(null);

        AccountHolder primaryOwner = accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Secondary Owner doesn't Exists"));
            checking.setSecondaryOwner(secondaryOwner);
            studentChecking.setSecondaryOwner(secondaryOwner);
        }

        if (primaryOwner.getAge() > 24) {

            if (accountDTO.getBalance().getAmount().compareTo(checking.getMinimumBalance()) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The balance cannot be lower than" + checking.getMinimumBalance());
            }

            checking.setBalance(accountDTO.getBalance());
            checking.setPrimaryOwner(primaryOwner);

            return checkingRepository.save(checking);
        }

        studentChecking.setBalance(accountDTO.getBalance());
        studentChecking.setPrimaryOwner(primaryOwner);
        return studentCheckingRepository.save(studentChecking);
    }

    @Override
    public Account createSavings(AccountDTO accountDTO) {
        Savings savings = new Savings();
        return savingsRepository.save(savings);
    }

    public List<Account> getAllAccounts() {
        if (checkingRepository.count() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No checking accounts found");
        }
        return checkingRepository.findAll();
    }
}
