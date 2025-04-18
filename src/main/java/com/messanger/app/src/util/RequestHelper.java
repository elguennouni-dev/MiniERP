package com.messanger.app.src.util;

import com.google.gson.Gson;
import com.messanger.app.src.model.Customer;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestHelper {

    private static final Gson gson = new Gson();

    public static <T> T parseRequestBody(HttpExchange exchange, Class<T> clazz) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            body.append(line);
        }
        return gson.fromJson(body.toString(),clazz);
    }

    public static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type","application/json");
        exchange.sendResponseHeaders(statusCode,response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void sendJson(HttpExchange exchange,Object obj, int statusCode) throws IOException {
        String json = gson.toJson(obj);
        exchange.getResponseHeaders().set("Content-Type","application/json");
        exchange.sendResponseHeaders(statusCode,json.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

    public static void sendMessage(HttpExchange exchange, String message, int statusCode) throws IOException {
        Map<String,String> res = new HashMap<>();
        res.put("message",message);
        sendJson(exchange,res,statusCode);
    }

}
