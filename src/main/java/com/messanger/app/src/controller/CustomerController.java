package com.messanger.app.src.controller;

import com.google.gson.Gson;
import com.messanger.app.src.model.Customer;
import com.messanger.app.src.service.CustomerService;
import com.messanger.app.src.util.DBConnectionUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;


public class CustomerController implements HttpHandler {

    private static CustomerService customerService = new CustomerService(DBConnectionUtil.getConnection());
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
                    if(query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        Customer customer = customerService.getCustomerById(id);
                        sendResponse(exchange,gson.toJson(customer),200);
                    }else{
                        List<Customer> customers = customerService.getAllCustomers();
                        sendResponse(exchange,gson.toJson(customers),200);
                    }
                    break;

                case "POST":
                    Customer newCustomer = parseRequestBody(exchange);
                    customerService.addCustomer(newCustomer);
                    sendResponse(exchange,"{\"message\": \"Customer added\"}",201);
                    break;

                case "PUT":
                    Customer updatedCustomer = parseRequestBody(exchange);
                    customerService.updateCustomer(updatedCustomer);
                    sendResponse(exchange,"{\"message\": \"Customer updated\"}",200);
                    break;

                case "DELETE":
                    if(query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        customerService.deleteCustomer(id);
                        sendResponse(exchange,"{\"message\": \"Customer deleted\"}", 200);
                    }else{
                        sendResponse(exchange,"{\"error\": \"Missed id parameter\"}",400);
                    }
                    break;
                default:
                    exchange.sendResponseHeaders(405,-1);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                sendResponse(exchange,"{\"error\": \"Server error\"}",500);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }



    private Customer parseRequestBody(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            body.append(line);
        }
        return gson.fromJson(body.toString(),Customer.class);
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type","application/json");
        exchange.sendResponseHeaders(statusCode,response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
