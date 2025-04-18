package com.messanger.app.src.service;

import com.messanger.app.src.dao.CustomerDAO;
import com.messanger.app.src.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(Connection connection) {
        this.customerDAO = new CustomerDAO(connection);
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.deleteCustomer(id);
    }

}
