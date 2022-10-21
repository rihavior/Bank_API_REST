package com.rihavior.Bank_API_REST.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rihavior.Bank_API_REST.entities.accounts.Account;
import com.rihavior.Bank_API_REST.others.Address;
import com.rihavior.Bank_API_REST.others.Role;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;

@Entity
public class AccountHolder extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate dateOfBirth;

    private int age;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private Address primaryAddress;
    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
    private Address mailingAddress;

    /**
     * Aqui se puden hacer dos listas, una para las cuentas en las que aparezca como primaryOwner
     * y otro para las que aparezca como secondaryOwner.
     * Tambien se podria hacer un Map<Account, String> donde la String sea primary o secondary. O un boolean isPrimary.
     */

    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accountList;



    public AccountHolder(String userName, String name, LocalDate dateOfBirth,Address primaryAddress, Address mailingAddress) {
        super(userName,name);
        this.dateOfBirth = dateOfBirth;
        setAge();
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
//        getRoles().add(new Role("HOLDER",this));
//        setRoles(new HashSet<>(List.of(new Role("HOLDER", this))));

    }

    public AccountHolder() {
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age = Period.between(dateOfBirth,LocalDate.now()).getYears();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}
