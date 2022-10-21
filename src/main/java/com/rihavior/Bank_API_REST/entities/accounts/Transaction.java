package com.rihavior.Bank_API_REST.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String ownerUserName;

    private Long originAccountId;

    private Long finalAccountId;

//    private LocalDateTime creationDate = LocalDateTime.now();

    public Transaction(BigDecimal amount, String ownerUserName, Long originAccountId, Long finalAccountId) {
        this.amount = amount;
        this.ownerUserName = ownerUserName;
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

    public Long getDestinyAccountId() {
        return finalAccountId;
    }

    public void setDestinyAccountId(Long finalAccountId) {
        this.finalAccountId = finalAccountId;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
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