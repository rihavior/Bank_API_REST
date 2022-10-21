package com.rihavior.Bank_API_REST.others;

import com.rihavior.Bank_API_REST.entities.users.User;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Role(String role, User user) {
        Role = role;
        this.user = user;
    }

    public Role() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
