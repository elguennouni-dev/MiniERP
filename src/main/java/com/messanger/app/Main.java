package com.messanger.app;

import com.messanger.app.src.auth.AuthController;
import com.messanger.app.src.controller.CustomerController;
import com.messanger.app.src.controller.OrderController;
import com.messanger.app.src.controller.OrderItemController;
import com.messanger.app.src.controller.ProductController;
import com.messanger.app.src.util.Route;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);

        List<Route> routes = List.of(
                new Route("/api/login",new AuthController()),
                new Route("/api/customers",new CustomerController()),
                new Route("/api/products",new ProductController()),
                new Route("/api/orders",new OrderController()),
                new Route("/api/orderitems",new OrderItemController())
        );

        for(Route route : routes) {
            server.createContext(route.getPath(),route.getHandler());
        }

        server.setExecutor(null);
        server.start();
        System.out.println("Server running on http://localhost:8000");
    }

}