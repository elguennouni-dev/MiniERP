package com.messanger.app.src.dao;

import com.messanger.app.src.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer(name, phone, email) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.executeUpdate();
        }
    }

    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if(set.next()) {
                Customer customer = new Customer(set.getString("name"),set.getString("phone"),set.getString("email"));
                customer.setId(set.getInt("customer_id"));
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                Customer customer = new Customer(set.getString("name"), set.getString("phone"),set.getString("email"));
                customer.setId(set.getInt("customer_id"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, phone = ?, email = ? WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setInt(4, customer.getId());
            statement.executeUpdate();
        }
    }

    public void deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}
