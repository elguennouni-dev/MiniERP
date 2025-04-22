package com.messanger.app.src.util;

import com.messanger.app.src.annotation.WebRouter;
import com.messanger.app.src.auth.AuthController;
import com.messanger.app.src.controller.CustomerController;
import com.messanger.app.src.controller.OrderController;
import com.messanger.app.src.controller.OrderItemController;
import com.messanger.app.src.controller.ProductController;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.List;

public class Application {

//    public static void start() throws Exception {
//        int PORT = 8080;
//        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
//
//        List<Route> routes = List.of(
//                new Route("/", new ApplicationController())
//        );
//
//        for(Route route : routes) {
//            server.createContext(route.getPath(),route.getHandler());
//        }
//
//        server.setExecutor(null);
//        server.start();
//
//        Banner.loadBanner();
//        System.out.println("Application running on http://localhost:" + PORT + "/");
//    }


    public static void start() throws Exception {
        int PORT = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);

        List<Object> controllers = List.of(
                new ApplicationController(),
                new AuthController(),
                new CustomerController(),
                new ProductController(),
                new OrderController(),
                new OrderItemController()
        );

        WebRouter.registerRoutes(server,controllers);

        server.setExecutor(null);
        server.start();

        Banner.loadBanner();
        System.out.println("Application running on http://localhost:" + PORT + "/");
    }

}
