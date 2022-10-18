package com.rihaviour.Bank_API_REST.services;

import com.rihaviour.Bank_API_REST.entities.AccountDTO;
import com.rihaviour.Bank_API_REST.entities.accounts.*;
import com.rihaviour.Bank_API_REST.repositories.*;
import com.rihaviour.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
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
    CreditCardRepository creditCardRepository;

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


    public Account createSavings(AccountDTO accountDTO) {

        Savings savings = new Savings();

        savings.setSecondaryOwner(null);

        AccountHolder primaryOwner = accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Secondary Owner doesn't Exists"));
            savings.setSecondaryOwner(secondaryOwner);
        }

        if (accountDTO.getInterestRate() != null) {

            if (accountDTO.getInterestRate().compareTo(new BigDecimal("0.5")) > 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The interestRate cannot be greater than max(0.5).");
            }
            savings.setInterestRate(accountDTO.getInterestRate());
        }



        savings.setPrimaryOwner(primaryOwner);

        if (accountDTO.getBalance() != null) {

            if (accountDTO.getBalance().getAmount().compareTo(savings.getMinimumBalance()) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Balance cannot be lower than min(100).");
            }
            savings.setBalance(accountDTO.getBalance());
        }



        return savingsRepository.save(savings);
    }


    public Account createCreditCard(AccountDTO accountDTO) {

        CreditCard creditCard = new CreditCard();

        creditCard.setSecondaryOwner(null);

        AccountHolder primaryOwner = accountHolderRepository.findByUserName(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        creditCard.setPrimaryOwner(primaryOwner);

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUserName(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Secondary Owner doesn't Exists"));
            creditCard.setSecondaryOwner(secondaryOwner);
        }

        if (accountDTO.getCreditLimit() != null) {

            if (accountDTO.getCreditLimit().compareTo(new BigDecimal(100000)) > 0 || accountDTO.getCreditLimit().compareTo(new BigDecimal(100)) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden Credit Limit should be between 100 and 100000.");
            }
            creditCard.setCreditLimit(accountDTO.getCreditLimit());
        }

        if (accountDTO.getInterestRate() != null) {

            if (accountDTO.getInterestRate().compareTo(new BigDecimal("0.2000")) > 0 || accountDTO.getInterestRate().compareTo(new BigDecimal("0.1000")) < 0) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden Interest Rate should be between 0.1000 and 0.2000.");
            }
            creditCard.setInterestRate(accountDTO.getInterestRate());
        }

        creditCard.setBalance(accountDTO.getBalance());

        return creditCardRepository.save(creditCard);
    }

    public List<Account> getAllAccounts() {
        if (checkingRepository.count() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No accounts found");
        }
        return checkingRepository.findAll();
    }
}
