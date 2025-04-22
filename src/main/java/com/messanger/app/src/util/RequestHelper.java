package com.messanger.app.src.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestHelper {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(java.sql.Date.class, new SqlDateDeserializer())
                .create();
    }

    public static <T> T parseRequestBody(HttpExchange exchange, Class<T> clazz) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                body.append(line);
            }
            return gson.fromJson(body.toString(), clazz);
        }
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

    public static void sendHtml(HttpExchange exchange) throws IOException {
        try {
            File file = new File("src/main/resources/index.html");
            if (!file.exists()) {
                throw new FileNotFoundException("Cannot find index.html at: " + file.getAbsolutePath());
            }
            FileInputStream inputStream = new FileInputStream(file);
            int fileSize = inputStream.available();
            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);
            String page = new String(buffer);

            exchange.getResponseHeaders().set("Content-Type", "text/html");
            byte[] responseBytes = page.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
