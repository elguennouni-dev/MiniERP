package com.messanger.app.src.dao;

import com.messanger.app.src.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO product(name,category,price,stock_qty) VALUES(?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,product.getName());
            statement.setString(2,product.getCategory());
            statement.setBigDecimal(3,product.getPrice());
            statement.setInt(4,product.getStockQty());
            statement.executeUpdate();
        }
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1,id);
            ResultSet set = statement.executeQuery();
            if(set.next()) {
                Product product = new Product(set.getString("name"),set.getString("category"),set.getBigDecimal("price"),set.getInt("stock_qty"));
                product.setId(set.getInt("id"));
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            while(set.next()) {
                Product product = new Product(set.getString("name"),set.getString("category"),set.getBigDecimal("price"),set.getInt("stock_qty"));
                product.setId(set.getInt("id"));
                products.add(product);
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET (name=?,category=?,price=?,stock_qty=?) WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getCategory());
            statement.setBigDecimal(3, product.getPrice());
            statement.setInt(4, product.getStockQty());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE * FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}
