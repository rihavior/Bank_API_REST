package com.rihaviour.Bank_API_REST.entities.accounts;

import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class CreditCard extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal interestRate;

    private BigDecimal creditLimit;

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, BigDecimal creditLimit) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }

    public CreditCard() {
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
}
