package com.rihavior.Bank_API_REST.services;

import com.rihavior.Bank_API_REST.entities.DTOs.AccountDTO;
import com.rihavior.Bank_API_REST.entities.DTOs.TransactionDTO;
import com.rihavior.Bank_API_REST.entities.accounts.*;
import com.rihavior.Bank_API_REST.entities.users.ThirdParty;
import com.rihavior.Bank_API_REST.entities.users.User;
import com.rihavior.Bank_API_REST.others.Role;
import com.rihavior.Bank_API_REST.repositories.*;
import com.rihavior.Bank_API_REST.services.interfaces.AccountServiceInterface;
import com.rihavior.Bank_API_REST.entities.DTOs.AccountHolderDTO;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public Account createChecking(AccountDTO accountDTO) {

        Checking checking = new Checking();
        StudentChecking studentChecking = new StudentChecking();

        checking.setSecondaryOwner(null);
        studentChecking.setSecondaryOwner(null);

        AccountHolder primaryOwner = accountHolderRepository.findByUsername(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUsername(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
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

        AccountHolder primaryOwner = accountHolderRepository.findByUsername(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUsername(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
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

        AccountHolder primaryOwner = accountHolderRepository.findByUsername(accountDTO.getPrimaryOwnerUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "The User introduced as Primary Owner doesn't Exists")
        );

        creditCard.setPrimaryOwner(primaryOwner);

        if (accountDTO.getSecondaryOwnerUsername() != null) {
            AccountHolder secondaryOwner = accountHolderRepository.findByUsername(accountDTO.getSecondaryOwnerUsername()).orElseThrow(
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


    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        AccountHolder accountHolder = new AccountHolder();

        if (accountHolderDTO.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The Name can't be null.");
        } accountHolder.setName(accountHolderDTO.getName());

        if (accountHolderDTO.getUserName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The UserName can't be null.");
        }

        if (accountHolderRepository.findByUsername(accountHolderDTO.getUserName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The UserName: " + accountHolderDTO.getUserName() + ", already exists.");
        } accountHolder.setUserName(accountHolderDTO.getUserName());

        try{
            accountHolder.setDateOfBirth(LocalDate.of(accountHolderDTO.getBirthYear(),accountHolderDTO.getBirthMonth(),accountHolderDTO.getBirthDay()));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong birthDate format.");
        }
        accountHolder.setAge();

        if (accountHolder.getAge() > 150 || accountHolder.getAge() < 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden age(" + accountHolderDTO.getBirthYear() + "), should be between 0 and 150.");
        }

        accountHolder.setPrimaryAddress(accountHolderDTO.getPrimaryAddress());
        accountHolder.setMailingAddress(accountHolderDTO.getMailingAddress());

        accountHolderRepository.save(accountHolder);

        roleRepository.save(new Role("HOLDER", userRepository.findByUsername(accountHolder.getUserName()).get()));

        return accountHolder;
    }

    public ThirdParty createThirdParty(ThirdParty thirdParty){
        userRepository.save(thirdParty);
        roleRepository.save(new Role("THIRD", userRepository.findByUsername(thirdParty.getUserName()).get()));
        return thirdParty;
    }

    public Transaction thirdTransfer(TransactionDTO transactionDTO) {

        Transaction transaction = new Transaction();

        User thirdParty = userRepository.findByUsername(transactionDTO.getUsername()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "The given username doesn't exist.")
        );

        transaction.setUsername(thirdParty.getUserName());

        Account destiny = accountRepository.findById(transactionDTO.getFinalAccountId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "The final account doesn't exist.")
        );

        transaction.setFinalAccountId(transactionDTO.getFinalAccountId());

        if (destiny.getBalance().getAmount().compareTo(transactionDTO.getAmount()) < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough Funds");
        }

        transaction.setAmount(transactionDTO.getAmount());

        if (transactionDTO.getAmount().compareTo(new BigDecimal(0)) < 0){
            destiny.getBalance().decreaseAmount(transactionDTO.getAmount());
        } else {
            destiny.getBalance().increaseAmount(transactionDTO.getAmount());
        }

        if (destiny instanceof Checking){
            if (destiny.getBalance().getAmount().compareTo(((Checking) destiny).getMinimumBalance()) < 0){
                destiny.getBalance().decreaseAmount(destiny.getPenaltyFee());
            }
        } else if (destiny instanceof Savings){
            if (destiny.getBalance().getAmount().compareTo(((Savings) destiny).getMinimumBalance()) < 0){
                destiny.getBalance().decreaseAmount(destiny.getPenaltyFee());
            }
        }
        return transactionRepository.save(transaction);
    }

    public Transaction transferFunds(Transaction transaction) {

        AccountHolder accountHolder = accountHolderRepository.findByUsername(transaction.getUsername()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "The given userName doesn't exist.")
        );

        Account origin = accountRepository.findById(transaction.getOriginAccountId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "The origin account doesn't exist.")
        );

        Account destiny = accountRepository.findById(transaction.getFinalAccountId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "The final account doesn't exist.")
        );

        if (!origin.getPrimaryOwner().getUserName().equals(transaction.getUsername() )
                && origin.getSecondaryOwner() != null){
            if (!origin.getSecondaryOwner().getUserName().equals(transaction.getUsername())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This account doesn't belong to this User");
            }
        }

        if (origin.getBalance().getAmount().compareTo(transaction.getAmount()) < 0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough Funds");
        }

        origin.getBalance().decreaseAmount(transaction.getAmount());

        destiny.getBalance().increaseAmount(transaction.getAmount());

        if (origin instanceof Checking){
            if (origin.getBalance().getAmount().compareTo(((Checking) origin).getMinimumBalance()) < 0){
                origin.getBalance().decreaseAmount(origin.getPenaltyFee());
            }
        } else if (origin instanceof Savings){
            if (origin.getBalance().getAmount().compareTo(((Savings) origin).getMinimumBalance()) < 0){
                origin.getBalance().decreaseAmount(origin.getPenaltyFee());
            }
        }



        return transactionRepository.save(transaction);
    }

    public Optional<Account> getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "AccountId not found.")
        );
        return Optional.ofNullable(account);
    }

    public Account modifyBalance(AccountDTO accountDTO) {

        Account account = accountRepository.findById(accountDTO.getAccountId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NO_CONTENT, "AccountId not found.")
        );

        account.setBalance(accountDTO.getBalance());
        account.getBalance().decreaseAmount(accountDTO.getBalance());

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        if (checkingRepository.count() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No accounts found");
        }
        return checkingRepository.findAll();
    }
}
