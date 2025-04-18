package com.messanger.app.src.service;

import com.messanger.app.src.dao.OrderItemDAO;
import com.messanger.app.src.model.OrderItem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemService {
    private OrderItemDAO orderItemDAO;

    public OrderItemService(Connection connection) {
        this.orderItemDAO = new OrderItemDAO(connection);
    }

    public OrderItem getOrderItemById(int id) throws SQLException {
        return orderItemDAO.getOrderItemById(id);
    }

    public List<OrderItem> getAllOrderItems() throws SQLException {
        return orderItemDAO.getAllOrderItems();
    }

    public void updateOrderItem(OrderItem newOrderItem) throws SQLException {
        orderItemDAO.updateOrderItem(newOrderItem);
    }

    public void deleteOrderItem(int id) throws SQLException {
        orderItemDAO.deleteOrderItem(id);
    }



}
