package com.messanger.app.src.dao;

import com.messanger.app.src.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders(customer_id, order_date, total_price) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,order.getCustomer().getId());
            statement.setDate(2,order.getOrderDate());
            statement.setBigDecimal(3, order.getTotalPrice());
            statement.executeUpdate();
        }
    }

    public Order getOrderById(int id) throws SQLException {
        // order_id, customer_id, order_date, total_price
        String sql = "SELECT * FROM orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next()) {

            }
        }
        return null;
    }

    public List<Order> getAllOrders() throws SQLException {
        // order_id, customer_id, order_date, total_price
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {

            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET(customer_id=?,order_date=?,total_price=?) WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,order.getCustomer().getId());
            statement.setDate(2,order.getOrderDate());
            statement.setBigDecimal(3,order.getTotalPrice());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE * FROM orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            statement.executeUpdate();
        }
    }

}
