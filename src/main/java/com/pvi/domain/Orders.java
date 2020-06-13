package com.pvi.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Orders {
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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<Item> items;

    private java.time.LocalDate dateOfCreate;
    private java.time.LocalDate dateOfDelivery;
    private java.time.LocalDate dateOfComplete;
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private DeliveryMethod deliveryMethod;

    @Enumerated(EnumType.ORDINAL)
    private PaymentMethod paymentMethod;

    private boolean confirm;
    private boolean paymentConfirm;
    private boolean completed;
    private int total;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Orders(Customer customer, List<Item> items, LocalDate dateOfCreate, LocalDate dateOfDelivery, DeliveryMethod deliveryMethod, PaymentMethod paymentMethod, int total, String address, boolean payment) {
        this.customer = customer;
        this.items = items;
        this.dateOfCreate = dateOfCreate;
        this.dateOfDelivery = dateOfDelivery;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.address = address;
        this.paymentConfirm = payment;
    }

    public Orders() {
    }

    public Long getId() {
        return id;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public LocalDate getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(LocalDate dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public LocalDate getDateOfComplete() {
        return dateOfComplete;
    }

    public void setDateOfComplete(LocalDate dateOfComplete) {
        this.dateOfComplete = dateOfComplete;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isPaymentConfirm() {
        return paymentConfirm;
    }

    public void setPaymentConfirm(boolean paymentConfirm) {
        this.paymentConfirm = paymentConfirm;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
