package com.rihavior.Bank_API_REST.entities.DTOs;

import com.rihavior.Bank_API_REST.others.Money;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountDTO {

    @NotNull
    private Money balance;

    @NotNull
    @NotBlank
    private String primaryOwnerUsername;

    private Long accountId;

    private String secondaryOwnerUsername;

    private BigDecimal interestRate;

    private BigDecimal creditLimit;

    public AccountDTO(Money balance, String primaryOwnerUsername, String secondaryOwnerUsername) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
        this.secondaryOwnerUsername = secondaryOwnerUsername;
    }
    public AccountDTO(Money balance, String primaryOwnerUsername, String secondaryOwnerUsername, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
        this.secondaryOwnerUsername = secondaryOwnerUsername;
        this.interestRate = interestRate;
    }

    public AccountDTO(BigDecimal interestRate, Money balance, String primaryOwnerUsername) {
        this.interestRate = interestRate;
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
    }

    public AccountDTO(Money balance, String primaryOwnerUsername) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
    }

    public AccountDTO(Money balance, Long accountId) {
        this.balance = balance;
        this.accountId = accountId;
    }

    public AccountDTO(Money balance, String primaryOwnerUsername, BigDecimal interestRate) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
        this.interestRate = interestRate;
    }

    public AccountDTO(Money balance, BigDecimal creditLimit, String primaryOwnerUsername) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
        this.creditLimit = creditLimit;
    }

    public AccountDTO(String primaryOwnerUsername) {
        this.primaryOwnerUsername = primaryOwnerUsername;
    }

    public AccountDTO() {
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public String getPrimaryOwnerUsername() {
        return primaryOwnerUsername;
    }

    public void setPrimaryOwnerUsername(String primaryOwnerUsername) {
        this.primaryOwnerUsername = primaryOwnerUsername;
    }

    public String getSecondaryOwnerUsername() {
        return secondaryOwnerUsername;
    }

    public void setSecondaryOwnerUsername(String secondaryOwnerUsername) {
        this.secondaryOwnerUsername = secondaryOwnerUsername;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
