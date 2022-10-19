package com.rihaviour.Bank_API_REST.entities.DTOs;

import com.rihaviour.Bank_API_REST.others.Address;

public class AccountHolderDTO {

    private String userName;

    private String name;

    private int birthYear, birthMonth, birthDay;

    private Address primaryAddress;

    private Address mailingAddress;

    public AccountHolderDTO(String userName, String name, int birthYear, int birthMonth, int birthDay, Address primaryAddress, Address mailingAddress) {
        this.name = name;
        this.userName = userName;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
