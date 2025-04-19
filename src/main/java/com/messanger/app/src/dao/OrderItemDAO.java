package com.messanger.app.src.dao;

import com.messanger.app.src.model.Order;
import com.messanger.app.src.model.OrderItem;
import com.messanger.app.src.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private final Connection connection;

    public OrderItemDAO(Connection connection) {
        this.connection = connection;
    }

    public void addOrderItem(OrderItem item) throws SQLException {
        String sql = "INSERT INTO orderitem(order_id,product_id,quantity,price_at_order_time) VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getOrder().getId());
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getQuantity());
            statement.setBigDecimal(4, item.getPriceAtOrder());
            statement.executeUpdate();
        }
    }

    public OrderItem getOrderItemById(int id) throws SQLException {
        String sql = "SELECT * FROM orderitem WHERE order_item_id=?";
        ProductDAO productDAO = new ProductDAO(connection);
        OrderDAO orderDAO = new OrderDAO(connection);
        CustomerDAO customerDAO = new CustomerDAO(connection);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                int orderItemId = set.getInt("order_item_id");
                int orderId = set.getInt("order_id");
                int productId = set.getInt("product_id");
                int quantity = set.getInt("quantity");
                BigDecimal priceAtOrderTime = set.getBigDecimal("price_at_order_time");
                Order order = orderDAO.getOrderById(orderId);
                Product product = productDAO.getProductById(productId);

                return new OrderItem(orderItemId, order, product, quantity, priceAtOrderTime);
            }
            return null;
        }
    }


    public List<OrderItem> getOrderItemsByOrderID(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM orderitem WHERE order_id=?";

        ProductDAO productDAO = new ProductDAO(connection);
        OrderDAO orderDAO = new OrderDAO(connection);

        Order order = orderDAO.getOrderById(orderId);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int orderItemId = set.getInt("order_item_id");
                int productId = set.getInt("product_id");
                int quantity = set.getInt("quantity");
                BigDecimal priceAtOrder = set.getBigDecimal("price_at_order_time");

                Product product = productDAO.getProductById(productId);

                OrderItem orderItem = new OrderItem(orderItemId, order, product, quantity, priceAtOrder);
                items.add(orderItem);
            }
        }

        return items;
    }



    public List<OrderItem> getAllOrderItems() throws SQLException {
        String sql = "SELECT * FROM orderitem";
        List<OrderItem> items = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                OrderItem orderItem = getOrderItemById(set.getInt("order_item_id"));
                items.add(orderItem);
            }
        }
        return items;
    }

    public void updateOrderItem(OrderItem item) throws SQLException {
        String sql = "UPDATE orderitem SET order_id=?, product_id=?, quantity=?, price_at_order_time=? WHERE order_item_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getOrder().getId());
            statement.setInt(2, item.getProduct().getId());
            statement.setInt(3, item.getQuantity());
            statement.setBigDecimal(4, item.getPriceAtOrder());
            statement.setInt(5, item.getId());

            statement.executeUpdate();
        }
    }

    public void deleteOrderItem(int id) throws SQLException {
        String sql = "DELETE FROM orderitem WHERE order_item_id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            statement.executeUpdate();
        }
    }

}
