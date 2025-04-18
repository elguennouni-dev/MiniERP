package com.messanger.app.src.model;

import java.math.BigDecimal;

public class OrderItem {
    private int id;
    private Order order;
    private Product product;
    private int quantity;
    private BigDecimal priceAtOrder;

    public OrderItem(int id, Order order, Product product, int quantity, BigDecimal priceAtOrder) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public OrderItem(Order order, Product product, int quantity, BigDecimal priceAtOrder) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + (order != null ? order.getId() : null) +
                ", product=" + product +
                ", quantity=" + quantity +
                ", priceAtOrder=" + priceAtOrder +
                '}';
    }
}