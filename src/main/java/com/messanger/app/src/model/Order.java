package com.messanger.app.src.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Order {
    private int id;
    private Customer customer;
    private Date orderDate;
    private BigDecimal totalPrice;
    private List<OrderItem> items;

    public Order(int id, Customer customer, Date orderDate, BigDecimal totalPrice, List<OrderItem> items) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public Order(Customer customer, Date orderDate, BigDecimal totalPrice, List<OrderItem> items) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", items=" + items +
                '}';
    }
}