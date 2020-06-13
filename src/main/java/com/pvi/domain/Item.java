package com.pvi.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Item {
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

    @ManyToOne
    @JoinColumn(name = "computer_id",nullable=false)
    private Computer computer;


    public Item(Computer computer) {
        this.computer = computer;
    }

    public Item() {
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    @Override
    public String toString() {
        return getComputer().toString();
    }
}
