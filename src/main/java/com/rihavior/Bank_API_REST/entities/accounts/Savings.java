package com.rihavior.Bank_API_REST.entities.accounts;

import com.rihavior.Bank_API_REST.others.Money;
import com.rihavior.Bank_API_REST.others.Status;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Savings extends Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretKey;
    private BigDecimal minimumBalance;//todo ESTO FUNIONARA??? JUNTO CON lin28
    private Status status;
    @DecimalMax(value = "0.5", message = "The given interestRate is greater than max.")//todo ESTO FUNIONARA???
    @Digits(integer = 1, fraction = 4)
    private BigDecimal interestRate;

    private LocalDate lastInterestApplied = LocalDate.now();

    public Savings(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.secretKey = "1234";
        this.minimumBalance = new BigDecimal(100);
        this.status = Status.ACTIVE;
        this.interestRate = new BigDecimal("0.0025");
    }

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = "1234";
        this.minimumBalance = new BigDecimal(100);
        this.status = Status.ACTIVE;
        this.interestRate = new BigDecimal("0.0025");
    }
    public Savings() {
        setBalance(new Money(new BigDecimal(1000)));
        this.secretKey = "1234";
        this.minimumBalance = new BigDecimal(100);
        this.status = Status.ACTIVE;
        this.interestRate = new BigDecimal("0.0025");
    }

    @Override
    public Money getBalance() {
        if (Period.between(lastInterestApplied,LocalDate.now()).getYears() > 0) {
            setBalance(new Money(super.getBalance().getAmount()
                    .add(super.getBalance().getAmount()
                            .multiply(interestRate
                                    .multiply(new BigDecimal(Period.between(lastInterestApplied,LocalDate.now()).getYears()))
                            )
                    )
            ));
            setLastInterestApplied(LocalDate.now());
        }
        return super.getBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestApplied() {
        return lastInterestApplied;
    }

    public void setLastInterestApplied(LocalDate lastInterestApplied) {
        this.lastInterestApplied = lastInterestApplied;
    }
}
