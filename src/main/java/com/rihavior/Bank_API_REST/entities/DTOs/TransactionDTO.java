package com.rihavior.Bank_API_REST.entities.DTOs;

import java.math.BigDecimal;

public class TransactionDTO {

    private BigDecimal amount;

    private String username;

    private Long originAccountId;

    private Long finalAccountId;

    public TransactionDTO(BigDecimal amount, String username, Long originAccountId, Long finalAccountId) {
        this.amount = amount;
        this.username = username;
        this.originAccountId = originAccountId;
        this.finalAccountId = finalAccountId;
    }

    public TransactionDTO(BigDecimal amount, String username, Long finalAccountId) {
        this.amount = amount;
        this.username = username;
        this.finalAccountId = finalAccountId;
    }

    public TransactionDTO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(Long originAccountId) {
        this.originAccountId = originAccountId;
    }

    public Long getFinalAccountId() {
        return finalAccountId;
    }

    public void setFinalAccountId(Long finalAccountId) {
        this.finalAccountId = finalAccountId;
    }
}
