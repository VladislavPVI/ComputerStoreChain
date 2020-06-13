package com.pvi.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Computer {
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

    @Enumerated(EnumType.ORDINAL)
    private Brand brand;

    private String name;
    private String cpu;
    private String gpu;
    private String ram;
    private String hdd;
    private String os;
    private int price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Computer(Brand brand, String name, String cpu, String gpu, String ram, String hdd, String os, int price) {
        this.brand = brand;
        this.name = name;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.hdd = hdd;
        this.os = os;
        this.price = price;

    }

    public Computer() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return getBrand()+" "+getName()+" "+getCpu()+" "+getGpu()+" "+getHdd()+" "+getRam()+" "+getOs();
    }
}
