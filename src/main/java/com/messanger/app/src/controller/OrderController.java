package com.messanger.app.src.controller;

import com.messanger.app.src.model.Order;
import com.messanger.app.src.service.CustomerService;
import com.messanger.app.src.service.OrderService;
import com.messanger.app.src.util.BaseHttpHandler;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class OrderController extends BaseHttpHandler {

    private final OrderService orderService = new OrderService(DBConnectionUtil.getConnection());

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
                        // ✅ Get all orders
                        List<Order> orders = orderService.getAllOrders();
                        if (orders.isEmpty()) {
                            RequestHelper.sendMessage(exchange, "No orders in database", 404);
                        } else {
                            RequestHelper.sendJson(exchange, orders, 200);
                        }
                    }
                    break;

                case "POST":
                    Order newOrder = RequestHelper.parseRequestBody(exchange, Order.class);

                    if (orderService.getOrderById(newOrder.getId()) != null) {
                        RequestHelper.sendMessage(exchange, "Order already exists", 400);
                        return;
                    }

                    orderService.addOrder(newOrder);
                    RequestHelper.sendMessage(exchange, "Order created", 201);
                    break;

                case "PUT":
                    Order updatedOrder = RequestHelper.parseRequestBody(exchange, Order.class);
                    orderService.updateOrder(updatedOrder);
                    RequestHelper.sendMessage(exchange, "Order updated", 200);
                    break;

                case "DELETE":
                    if (query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        orderService.deleteOrder(id);
                        RequestHelper.sendMessage(exchange, "Order deleted", 200);
                    } else {
                        RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
                    }
                    break;

                default:
                    exchange.sendResponseHeaders(405, -1);
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

