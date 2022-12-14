package com.rihavior.Bank_API_REST.entities.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Admin(String username, String name) {
        super(username, name);
    }

    public Admin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
