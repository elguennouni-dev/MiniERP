package com.messanger.app.src.controller;

import com.messanger.app.src.annotation.WebRoute;
import com.messanger.app.src.model.Customer;
import com.messanger.app.src.service.CustomerService;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class CustomerController {

    private final CustomerService customerService = new CustomerService(DBConnectionUtil.getConnection());

    @WebRoute(path = "/api/customers", method = "GET")
    public void getCustomers(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null) {
                if (query.contains("id=")) {
                    int id = Integer.parseInt(query.split("=")[1]);
                    Customer customer = customerService.getCustomerById(id);

                    if (customer == null) {
                        RequestHelper.sendMessage(exchange, "Customer not found", 404);
                    } else {
                        RequestHelper.sendJson(exchange, customer, 200);
                    }

                } else if (query.contains("search=")) {
                    String name = query.split("=")[1];
                    List<Customer> searchCustomers = customerService.getByCustomerName(name);
                    RequestHelper.sendJson(exchange, searchCustomers, 200);

                } else {
                    List<Customer> customers = customerService.getAllCustomers();
                    RequestHelper.sendJson(exchange, customers, 200);
                }

            } else {
                List<Customer> customers = customerService.getAllCustomers();
                RequestHelper.sendJson(exchange, customers, 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/customers", method = "POST")
    public void createCustomer(HttpExchange exchange) throws IOException {
        try {
            Customer newCustomer = RequestHelper.parseRequestBody(exchange, Customer.class);

            if (customerService.getCustomerById(newCustomer.getId()) != null) {
                RequestHelper.sendMessage(exchange, "Customer already exists", 400);
                return;
            }

            customerService.addCustomer(newCustomer);
            RequestHelper.sendMessage(exchange, "Customer added", 201);

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/customers", method = "PUT")
    public void updateCustomer(HttpExchange exchange) throws IOException {
        try {
            Customer updatedCustomer = RequestHelper.parseRequestBody(exchange, Customer.class);
            customerService.updateCustomer(updatedCustomer);
            RequestHelper.sendMessage(exchange, "Customer updated", 200);

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/customers", method = "DELETE")
    public void deleteCustomer(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")) {
                int id = Integer.parseInt(query.split("=")[1]);
                customerService.deleteCustomer(id);
                RequestHelper.sendMessage(exchange, "Customer deleted", 200);
            } else {
                RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }
}
