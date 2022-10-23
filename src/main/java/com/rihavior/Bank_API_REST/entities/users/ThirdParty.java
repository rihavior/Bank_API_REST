package com.rihavior.Bank_API_REST.entities.users;

import com.rihavior.Bank_API_REST.others.HashCreator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ThirdParty extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hashedKey;

    public ThirdParty(String username, String name) {
        super(username, name);
        this.hashedKey = HashCreator.generateString();
    }

    public ThirdParty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
