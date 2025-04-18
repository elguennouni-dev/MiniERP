package com.messanger.app.src.util;

import com.sun.net.httpserver.HttpHandler;

public class Route {
    private final String path;
    private final HttpHandler handler;

    public Route(String path, HttpHandler handler) {
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public HttpHandler getHandler() {
        return handler;
    }

}
