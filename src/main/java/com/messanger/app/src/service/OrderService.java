package com.messanger.app.src.service;

import com.messanger.app.src.dao.OrderDAO;
import com.messanger.app.src.model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(Connection connection) {
        this.orderDAO = new OrderDAO(connection);
    }

    public Order getOrderById(int id) throws SQLException {
        return orderDAO.getOrderById(id);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public void addOrder(Order order) throws SQLException {
        orderDAO.addOrder(order);
    }

    public List<Order> getOrdersByCustomerId(int id) throws SQLException {
        return orderDAO
    }

    public void updateOrder(Order newOrder) throws SQLException {
        orderDAO.updateOrder(newOrder);
    }

    public void deleteOrder(int id) throws SQLException {
        orderDAO.deleteOrder(id);
    }

}
