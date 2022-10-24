package com.rihavior.Bank_API_REST.entities.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rihavior.Bank_API_REST.others.Money;
import com.rihavior.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded  //todo*******  No lleva @Embeddable, me daba problemas en la clase money
    @NotNull
    private Money balance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    private final LocalDate creationDate = LocalDate.now();

    private BigDecimal penaltyFee;

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = new BigDecimal(40);
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.penaltyFee = new BigDecimal(40);
    }

    public Account() {
        this.penaltyFee = new BigDecimal(40);
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
