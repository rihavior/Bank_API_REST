package com.rihaviour.Bank_API_REST.entities.accounts;

import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 1, fraction = 4)
    private BigDecimal interestRate;

    private LocalDate lastInterestApplied = LocalDate.now();

    private BigDecimal creditLimit;

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = new BigDecimal(100);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = new BigDecimal("0.2");
        this.creditLimit = new BigDecimal(100);
    }

    public CreditCard(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.interestRate = new BigDecimal("0.2");
        this.creditLimit = new BigDecimal(100);
    }



    public CreditCard() {
        this.interestRate = new BigDecimal("0.2");
        this.creditLimit = new BigDecimal(100);
    }

    @Override
    public Money getBalance() {
        if (Period.between(lastInterestApplied, LocalDate.now()).getMonths() > 0) {
            setBalance(new Money(super.getBalance().getAmount()
                    .add(super.getBalance().getAmount()
                            .multiply(interestRate
                                    .multiply(new BigDecimal(Period.between(lastInterestApplied,LocalDate.now()).getMonths()))
                            )
                    )
            ));
            lastInterestApplied = LocalDate.now();
        }
        return super.getBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public LocalDate getLastInterestApplied() {
        return lastInterestApplied;
    }

    public void setLastInterestApplied(LocalDate lastInterestApplied) {
        this.lastInterestApplied = lastInterestApplied;
    }
}
