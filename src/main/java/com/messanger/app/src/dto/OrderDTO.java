package com.messanger.app.src.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private int customerId;
    private Date orderDate;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {}

    public OrderDTO (int customerId, String orderDateString, BigDecimal totalPrice, List<OrderItemDTO> orderItems) {
        this.customerId = customerId;
        this.orderDate = Date.valueOf(orderDateString);
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDateString) {
        this.orderDate = Date.valueOf(orderDateString);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

}
