package com.messanger.app.src.controller;

import com.messanger.app.src.model.Customer;
import com.messanger.app.src.service.CustomerService;
import com.messanger.app.src.util.BaseHttpHandler;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;


public class CustomerController extends BaseHttpHandler {

    private final CustomerService customerService = new CustomerService(DBConnectionUtil.getConnection());

    @Override
    protected void doHandle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
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
                    break;

                case "POST":
                    Customer newCustomer = RequestHelper.parseRequestBody(exchange, Customer.class);

                    if (customerService.getCustomerById(newCustomer.getId()) != null) {
                        RequestHelper.sendMessage(exchange, "Customer already exists", 400);
                        return;
                    }

                    customerService.addCustomer(newCustomer);
                    RequestHelper.sendMessage(exchange, "Customer added", 201);
                    break;

                case "PUT":
                    Customer updatedCustomer = RequestHelper.parseRequestBody(exchange, Customer.class);
                    customerService.updateCustomer(updatedCustomer);
                    RequestHelper.sendMessage(exchange, "Customer updated", 200);
                    break;

                case "DELETE":
                    if (query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        customerService.deleteCustomer(id);
                        RequestHelper.sendMessage(exchange, "Customer deleted", 200);
                    } else {
                        RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
                    }
                    break;

                default:
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                RequestHelper.sendMessage(exchange, "Server error", 500);
            } catch (Exception innerEx) {
                innerEx.printStackTrace();
            }
        }
    }
}
