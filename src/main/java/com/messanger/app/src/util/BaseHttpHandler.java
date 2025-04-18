package com.messanger.app.src.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class BaseHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            doHandle(exchange);

        } catch (Exception e) {
            System.err.println("Error in BaseHttpHandler:");
            e.printStackTrace();
            try {
                RequestHelper.sendMessage(exchange, "Server error", 500);
            } catch (Exception inner) {
                inner.printStackTrace();
            }
        }
    }


    protected  abstract void doHandle(HttpExchange exchange) throws Exception;

}
