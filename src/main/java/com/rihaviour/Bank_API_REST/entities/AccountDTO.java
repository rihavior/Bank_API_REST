package com.rihaviour.Bank_API_REST.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;
import com.rihaviour.Bank_API_REST.others.Money;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    @NotNull
    private Double balance;

    @NotNull
    @NotBlank
    private String primaryOwnerUsername;

    private String secondaryOwnerUsername;

    public AccountDTO(Double balance, String primaryOwnerUsername, String secondaryOwnerUsername) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
        this.secondaryOwnerUsername = secondaryOwnerUsername;
    }

    public AccountDTO(Double balance, String primaryOwnerUsername) {
        this.balance = balance;
        this.primaryOwnerUsername = primaryOwnerUsername;
    }

    public AccountDTO() {
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
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
}
