package com.rihavior.Bank_API_REST;

import com.rihavior.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihavior.Bank_API_REST.repositories.CheckingRepository;
import com.rihavior.Bank_API_REST.repositories.CreditCardRepository;
import com.rihavior.Bank_API_REST.repositories.SavingsRepository;
import com.rihaviour.Bank_API_REST.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


	@Override
	public void run(String... args) throws Exception {

//		Address address = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//		AccountHolder user = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), address,"ricardo@test.com");
//
//		accountHolderRepository.save(user);
//
//
////		Checking checking = new Checking(new Money(new BigDecimal(1000)), user);
////
////		checkingRepository.save(checking);
////
////		Savings savings = new Savings(new Money(new BigDecimal("1000")),user);
////
////		savingsRepository.save(savings);
//
//		CreditCard creditCard = new CreditCard();
//
//		creditCard.setBalance(new Money(new BigDecimal(1000)));
//
//		creditCard.setPrimaryOwner(user);
//
//		creditCardRepository.save(creditCard);
//
//		System.out.println(savingsRepository.findByPrimaryOwnerUserName(user.getUserName()).get().getMinimumBalance());
//		System.out.println(savingsRepository.findByPrimaryOwnerUserName(user.getUserName()).get().getInterestRate());
////
////		checkingRepository.findByPrimaryOwnerId(1L);
//
//		int age = Period.between(user.getDateOfBirth(),LocalDate.now()).getYears();
//
//		System.out.println(age);

//		checkingRepository.saveAll(List.of(
//				new Checking(
//						new Money(new BigDecimal(1000)),
//						new AccountHolder("Ricardo",
//								LocalDate.of(1990,4,26),
//								new Address("Industria",175,8033,"Barcelona","Spain"),
//								"ricardo@mendoza.com"),
//						"1234")));
	}
}