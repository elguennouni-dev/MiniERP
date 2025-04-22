package com.messanger.app.src.controller;

import com.messanger.app.src.annotation.WebRoute;
import com.messanger.app.src.dto.OrderDTO;
import com.messanger.app.src.model.Order;
import com.messanger.app.src.service.OrderService;
import com.messanger.app.src.util.BaseHttpHandler;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class OrderController {

    private final OrderService orderService = new OrderService(DBConnectionUtil.getConnection());

    @WebRoute(path = "/api/orders", method = "GET")
    public void getOrders(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null) {
                if (query.contains("id=")) {
                    int id = Integer.parseInt(query.split("=")[1]);
                    Order order = orderService.getOrderById(id);

                    if (order == null) {
                        RequestHelper.sendMessage(exchange, "Order not found", 404);
                    } else {
                        RequestHelper.sendJson(exchange, order, 200);
                    }

                } else if (query.contains("customerId=")) {
                    int customerId = Integer.parseInt(query.split("=")[1]);
                    List<Order> orders = orderService.getOrdersByCustomerId(customerId);

                    if (orders.isEmpty()) {
                        RequestHelper.sendMessage(exchange, "No orders found for this customer", 404);
                    } else {
                        RequestHelper.sendJson(exchange, orders, 200);
                    }

                } else {
                    RequestHelper.sendMessage(exchange, "Invalid query parameter", 400);
                }

            } else {
                List<Order> orders = orderService.getAllOrders();
                if (orders.isEmpty()) {
                    RequestHelper.sendMessage(exchange, "No orders in database", 404);
                } else {
                    RequestHelper.sendJson(exchange, orders, 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/orders", method = "POST")
    public void createOrder(HttpExchange exchange) throws IOException {
        try {
            OrderDTO orderDTO = RequestHelper.parseRequestBody(exchange, OrderDTO.class);
            orderService.addOrder(orderDTO);
            RequestHelper.sendMessage(exchange, "Order created", 201);
        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/orders", method = "PUT")
    public void updateOrder(HttpExchange exchange) throws IOException {
        try {
            Order updatedOrder = RequestHelper.parseRequestBody(exchange, Order.class);
            orderService.updateOrder(updatedOrder);
            RequestHelper.sendMessage(exchange, "Order updated", 200);
        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/orders", method = "DELETE")
    public void deleteOrder(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")) {
                int id = Integer.parseInt(query.split("=")[1]);
                orderService.deleteOrder(id);
                RequestHelper.sendMessage(exchange, "Order deleted", 200);
            } else {
                RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }
}
