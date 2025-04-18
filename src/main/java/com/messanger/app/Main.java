package com.messanger.app;

import com.messanger.app.src.controller.CustomerController;
import com.messanger.app.src.util.DBConnectionUtil;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        server.createContext("/api/customers", new CustomerController());
        server.setExecutor(null);
        server.start();

        System.out.println("Server running on http://localhost:8000");
    }
}