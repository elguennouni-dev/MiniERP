package com.messanger.app.src.annotation;

import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class WebRouter {
    public static void registerRoutes(HttpServer server, List<Object> controllers) {
        Map<String, Map<String, MethodHandler>> routeMap = new HashMap<>();

        for (Object controller : controllers) {
            for (Method method : controller.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(WebRoute.class)) {
                    WebRoute route = method.getAnnotation(WebRoute.class);
                    String path = route.path();
                    String httpMethod = route.method().toUpperCase();

                    routeMap
                            .computeIfAbsent(path, p -> new HashMap<>())
                            .put(httpMethod, new MethodHandler(controller, method));
                }
            }
        }

        // Register a single context per unique path
        for (Map.Entry<String, Map<String, MethodHandler>> entry : routeMap.entrySet()) {
            String path = entry.getKey();
            Map<String, MethodHandler> methodMap = entry.getValue();

            server.createContext(path, exchange -> {
                String requestMethod = exchange.getRequestMethod().toUpperCase();
                MethodHandler handler = methodMap.get(requestMethod);

                if (handler != null) {
                    try {
                        handler.invoke(exchange);
                    } catch (Exception e) {
                        RequestHelper.sendMessage(exchange, "Internal server error", 500);
                        e.printStackTrace();
                    }
                } else {
                    RequestHelper.sendMessage(exchange, "Method not allowed", 405);
                }
            });
        }

        server.setExecutor(Executors.newFixedThreadPool(10));
    }

    private static class MethodHandler {
        private final Object controller;
        private final Method method;

        public MethodHandler(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }

        public void invoke(HttpExchange exchange) throws Exception {
            method.invoke(controller, exchange);
        }
    }
}