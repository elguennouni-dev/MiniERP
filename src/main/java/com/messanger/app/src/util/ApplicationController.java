package com.messanger.app.src.util;

import com.messanger.app.src.annotation.WebRoute;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class ApplicationController{

    @WebRoute(path = "/", method = "GET")
    public void handle(HttpExchange exchange) throws IOException {
        RequestHelper.sendHtml(exchange);
    }
}
