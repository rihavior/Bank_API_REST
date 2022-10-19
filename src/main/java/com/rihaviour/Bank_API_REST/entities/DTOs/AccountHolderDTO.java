package com.rihaviour.Bank_API_REST.entities.DTOs;

import com.rihaviour.Bank_API_REST.others.Address;

public class AccountHolderDTO {

    private String name;

    private String userName;

    private int birthYear, birthMonth, birthDay;

    private Address primaryAddress;

    private Address mailingAddress;

    public AccountHolderDTO(String name, String userName, int birthYear, int birthMonth, int birthDay, Address primaryAddress, Address mailingAddress) {
        this.name = name;
        this.userName = userName;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }
}
