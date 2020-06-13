package com.pvi.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Long id;


    @NotBlank(message = "Заполни email!")
    private String email;
    private LocalDate birthd;
    @NotBlank(message = "Заполни ФИО!")
    private String fullname;

    @OneToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;


    public Customer(String email, LocalDate birthd, String fullname, Account account) {
        this.email = email;
        this.birthd = birthd;
        this.fullname = fullname;
        this.account = account;
    }

    public Customer() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthd() {
        return birthd;
    }

    public void setBirthd(LocalDate birthd) {
        this.birthd = birthd;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return fullname;
    }
}