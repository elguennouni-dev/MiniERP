package com.messanger.app.src.auth;

import com.google.gson.Gson;
import com.messanger.app.src.annotation.WebRoute;
import com.messanger.app.src.model.Admin;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class AuthController {
    private final AuthService authService = new AuthService(DBConnectionUtil.getConnection());
    private final Gson gson = new Gson();


//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        try {
//            if("POST".equals(exchange.getRequestMethod())) {
//                LoginRequest loginRequest = RequestHelper.parseRequestBody(exchange, LoginRequest.class);
//
//                String email = loginRequest.getEmail();
//                String password = loginRequest.getPassword();
//
//                Admin admin = authService.login(email,password);
//
//                if(admin != null) {
//                    RequestHelper.sendJson(exchange, admin, 200);
//                }else{
//                    RequestHelper.sendMessage(exchange,"Invalid email or password",401);
//                }
//            }else{
//                RequestHelper.sendMessage(exchange,"Method not allowed",405);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            RequestHelper.sendMessage(exchange,"Server error",500);
//        }
//    }


    @WebRoute(path = "/login", method = "POST")
    public  void login(HttpExchange exchange) throws IOException {
        try {
            LoginRequest loginRequest = RequestHelper.parseRequestBody(exchange, LoginRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            Admin admin = authService.login(email,password);

            if(admin != null) {
                RequestHelper.sendJson(exchange, admin, 200);
            }else {
                RequestHelper.sendMessage(exchange,"Invalid email or password",401);
            }
        } catch (Exception e) {
            RequestHelper.sendMessage(exchange,"Server error",500);
            throw new RuntimeException(e);
        }
    }

}
