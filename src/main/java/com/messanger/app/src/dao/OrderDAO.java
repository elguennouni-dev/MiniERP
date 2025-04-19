package com.messanger.app.src.dao;

import com.messanger.app.src.model.Customer;
import com.messanger.app.src.model.Order;
import com.messanger.app.src.model.OrderItem;

import java.math.BigDecimal;
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
        String sql = "SELECT * FROM orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                int customerID = set.getInt("customer_id");
                CustomerDAO customerDAO = new CustomerDAO(connection);
                Customer customer = customerDAO.getCustomerById(customerID);

                Date orderDate = set.getDate("order_date");
                BigDecimal totalPrice = set.getBigDecimal("total_price");

                OrderItemDAO orderItemDAO = new OrderItemDAO(connection);
                List<OrderItem> items = orderItemDAO.getOrderItemsByOrderID(id);

                return new Order(id, customer, orderDate, totalPrice, items);
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

    public List<Order> getOrdersByCustomerId(int id) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id=?";
        CustomerDAO customerDAO = new CustomerDAO(connection);
        OrderItemDAO orderItemDAO = new OrderItemDAO(connection);
        Customer customer = customerDAO.getCustomerById(id);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int orderId = set.getInt("order_id");
                List<OrderItem> items = orderItemDAO.getOrderItemsByOrderID(orderId);
                Order order = new Order(
                        orderId,
                        customer,
                        set.getDate("order_date"),
                        set.getBigDecimal("total_price"),
                        items
                );
                for (OrderItem item : items) {
                    item.setOrder(order);
                }
                orders.add(order);
            }
        }
        return orders;
    }


    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE orders SET customer_id=?,order_date=?,total_price=? WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,order.getCustomer().getId());
            statement.setDate(2,order.getOrderDate());
            statement.setBigDecimal(3,order.getTotalPrice());
            statement.setInt(4, order.getId());
            statement.executeUpdate();
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            statement.executeUpdate();
        }
    }

}
