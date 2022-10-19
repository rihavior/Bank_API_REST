package com.rihaviour.Bank_API_REST.others;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(insertable = false, updatable = false)
    private String streetName;

    @Column(insertable = false, updatable = false)
    private int streetNumber;

    @Column(insertable = false, updatable = false)
    private int postalCode;


    @Column(insertable = false, updatable = false)
    private String City;

    @Column(insertable = false, updatable = false)
    private String Country;

    public Address() {
    }

    public Address(String streetName, int streetNumber, int postalCode, String city, String country) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        City = city;
        Country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
