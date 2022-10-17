package com.rihaviour.Bank_API_REST.entities.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

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
    @JsonIgnore
    private Money balance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;


    private LocalDate creationDate;

    private BigDecimal penaltyFee;

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = LocalDate.now();
        this.penaltyFee = new BigDecimal(40);
    }

    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.creationDate = LocalDate.now();
        this.penaltyFee = new BigDecimal(40);
    }

    public Account() {
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

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }
}
