package com.messanger.app.src.dto;


import java.math.BigDecimal;

public class OrderItemDTO {
    private int productId;
    private int quantity;
    private BigDecimal priceAtOrderTime;

    public OrderItemDTO() {}

    public OrderItemDTO(int productId, int quantity, BigDecimal priceAtOrderTime) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtOrderTime = priceAtOrderTime;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtOrderTime() {
        return priceAtOrderTime;
    }

    public void setPriceAtOrderTime(BigDecimal priceAtOrderTime) {
        this.priceAtOrderTime = priceAtOrderTime;
    }

}
