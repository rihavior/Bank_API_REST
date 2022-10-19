package com.rihaviour.Bank_API_REST.entities.accounts;

import com.rihaviour.Bank_API_REST.others.Money;
import com.rihaviour.Bank_API_REST.others.Status;
import com.rihaviour.Bank_API_REST.entities.users.AccountHolder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StudentChecking extends Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secretKey;
    private Status status;

    public StudentChecking(Money balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
        this.secretKey = "1234";
        this.status = Status.ACTIVE;
    }

    public StudentChecking() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
