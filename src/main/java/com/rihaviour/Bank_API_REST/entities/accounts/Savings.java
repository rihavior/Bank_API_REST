package com.rihaviour.Bank_API_REST.entities.accounts;

import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.others.Status;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Entity
public class Savings extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretKey;
    private BigDecimal minimumBalance = new BigDecimal(1000);//todo ESTO FUNIONARA??? JUNTO CON lin28
    private Status status;
    @DecimalMax(value = "0.5")//todo ESTO FUNIONARA??? JUNTO CON lin26 y 28
    private BigDecimal interestRate = new BigDecimal(0.0025);

    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = new BigDecimal(100);
        this.status = Status.ACTIVE;
        this.interestRate = interestRate;
    }

    public Savings() {
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
}
