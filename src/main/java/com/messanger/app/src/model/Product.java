package com.messanger.app.src.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String category;
    private BigDecimal price;
    private int stockQty;

    public Product(int id, String name, String category, BigDecimal price, int stockQty) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQty = stockQty;
    }

    public Product(String name, String category, BigDecimal price, int stockQty) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQty = stockQty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stockQty=" + stockQty +
                '}';
    }
}