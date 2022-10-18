package com.rihaviour.Bank_API_REST;

import com.rihaviour.Bank_API_REST.entities.accounts.Checking;
import com.rihaviour.Bank_API_REST.entities.accounts.Savings;
import com.rihaviour.Bank_API_REST.others.Address;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.repositories.AccountHolderRepository;
import com.rihaviour.Bank_API_REST.repositories.SavingsRepository;
import com.rihaviour.Bank_API_REST.repositories.UserRepository;
import com.rihaviour.Bank_API_REST.repositories.CheckingRepository;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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


	@Override
	public void run(String... args) throws Exception {

//		Address address = new Address("Diagonal", 1000, 8051,"Bcn","Esp");
//
//		AccountHolder user = new AccountHolder("rihavior", "Ricardo", LocalDate.of(1990,4,26), address,"ricardo@test.com");
//
//		accountHolderRepository.save(user);


//		Checking checking = new Checking(new Money(new BigDecimal(1000)), user);
//
//		checkingRepository.save(checking);
//
//		Savings savings = new Savings(new Money(new BigDecimal("1000")),user);
//
//		savingsRepository.save(savings);
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
