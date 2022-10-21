package com.rihavior.Bank_API_REST.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String username;

    private Long originAccountId;

    private Long finalAccountId;

//    private LocalDateTime creationDate = LocalDateTime.now();

    public Transaction(BigDecimal amount, String username, Long originAccountId, Long finalAccountId) {
        this.amount = amount;
        this.username = username;
        this.originAccountId = originAccountId;
        this.finalAccountId = finalAccountId;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(Long originAccountId) {
        this.originAccountId = originAccountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getFinalAccountId() {
        return finalAccountId;
    }

    public void setFinalAccountId(Long finalAccountId) {
        this.finalAccountId = finalAccountId;
    }

//    public LocalDateTime getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(LocalDateTime creationDate) {
//        this.creationDate = creationDate;
//    }
}
