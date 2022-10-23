package com.rihavior.Bank_API_REST;

import com.rihavior.Bank_API_REST.entities.accounts.Account;
import com.rihavior.Bank_API_REST.entities.accounts.Checking;
import com.rihavior.Bank_API_REST.entities.accounts.CreditCard;
import com.rihavior.Bank_API_REST.entities.accounts.Savings;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;
import com.rihavior.Bank_API_REST.entities.users.Admin;
import com.rihavior.Bank_API_REST.entities.users.User;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.others.Money;
import com.rihavior.Bank_API_REST.others.Role;
import com.rihavior.Bank_API_REST.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class BankApiRestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BankApiRestApplication.class, args);
	}

	@Autowired
	CheckingRepository checkingRepository;

	@Autowired
	SavingsRepository savingsRepository;

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	CreditCardRepository creditCardRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	AccountHolder owner1_Test;
	AccountHolder owner2_Test;
	AccountHolder owner3_Test;

	Admin admin;


	@Override
	public void run(String... args) throws Exception {

//		Address testAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//		Address testMailingAddress = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//		owner1_Test = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), testAddress,testMailingAddress);
//
//		owner2_Test = new AccountHolder("paqito", "Paco", LocalDate.of(1961,1,1), testAddress,testMailingAddress);
//
//		owner3_Test = new AccountHolder("laurita", "Laura", LocalDate.of(2002,1,1), testAddress,testMailingAddress);
//
//		admin = new Admin("admin","admin");
//
//		adminRepository.save(admin);
//
//		accountHolderRepository.save(owner1_Test);
//
//		accountHolderRepository.save(owner2_Test);
//
//		accountHolderRepository.save(owner3_Test);
//
//		roleRepository.save(new Role("HOLDER",owner1_Test));
//		roleRepository.save(new Role("HOLDER",owner2_Test));
//		roleRepository.save(new Role("HOLDER",owner3_Test));
//		roleRepository.save(new Role("HOLDER",admin));
//		roleRepository.save(new Role("ADMIN",admin));


//		Checking checking = new Checking(new Money(new BigDecimal(1000)), user);
//
//		checkingRepository.save(checking);
//
//		Savings savings = new Savings(new Money(new BigDecimal("1000")),user);
//
//		savingsRepository.save(savings);
//
//		for (Account a : accountRepository.findAll()){
//			System.out.println(a.getId());
//		}
//
//		CreditCard creditCard = new CreditCard();
//
//		creditCard.setBalance(new Money(new BigDecimal(1000)));
//
//		creditCard.setPrimaryOwner(user);
//
//		creditCardRepository.save(creditCard);
	}
}
