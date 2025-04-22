package com.messanger.app.src.controller;

import com.messanger.app.src.annotation.WebRoute;
import com.messanger.app.src.model.Product;
import com.messanger.app.src.service.ProductService;
import com.messanger.app.src.util.BaseHttpHandler;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class ProductController {

    private final ProductService productService = new ProductService(DBConnectionUtil.getConnection());

    @WebRoute(path = "/api/products", method = "GET")
    public void getProducts(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null) {
                if (query.contains("id=")) {
                    int id = Integer.parseInt(query.split("=")[1]);
                    Product product = productService.getProductById(id);

                    if (product == null) {
                        RequestHelper.sendMessage(exchange, "Product not found", 404);
                    } else {
                        RequestHelper.sendJson(exchange, product, 200);
                    }

                } else if (query.contains("search=")) {
                    String name = query.split("=")[1];
                    List<Product> searchResults = productService.searchProductsByName(name);

                    if (searchResults.isEmpty()) {
                        RequestHelper.sendMessage(exchange, "No products match your search", 404);
                    } else {
                        RequestHelper.sendJson(exchange, searchResults, 200);
                    }

                } else {
                    List<Product> products = productService.getAllProducts();

                    if (products.isEmpty()) {
                        RequestHelper.sendMessage(exchange, "No products in database.", 404);
                    } else {
                        RequestHelper.sendJson(exchange, products, 200);
                    }
                }

            } else {
                List<Product> products = productService.getAllProducts();

                if (products.isEmpty()) {
                    RequestHelper.sendMessage(exchange, "No products in database.", 404);
                } else {
                    RequestHelper.sendJson(exchange, products, 200);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/products", method = "POST")
    public void createProduct(HttpExchange exchange) throws IOException {
        try {
            Product newProduct = RequestHelper.parseRequestBody(exchange, Product.class);

            if (productService.getProductById(newProduct.getId()) != null) {
                RequestHelper.sendMessage(exchange, "Product already exists", 400);
                return;
            }

            productService.addProduct(newProduct);
            RequestHelper.sendMessage(exchange, "Product created", 201);

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/products", method = "PUT")
    public void updateProduct(HttpExchange exchange) throws IOException {
        try {
            Product updatedProduct = RequestHelper.parseRequestBody(exchange, Product.class);
            productService.updateProduct(updatedProduct);
            RequestHelper.sendMessage(exchange, "Product updated", 200);

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }

    @WebRoute(path = "/api/products", method = "DELETE")
    public void deleteProduct(HttpExchange exchange) throws IOException {
        try {
            String query = exchange.getRequestURI().getQuery();

            if (query != null && query.contains("id=")) {
                int id = Integer.parseInt(query.split("=")[1]);
                productService.deleteProduct(id);
                RequestHelper.sendMessage(exchange, "Product deleted", 200);
            } else {
                RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
            }

        } catch (Exception e) {
            e.printStackTrace();
            RequestHelper.sendMessage(exchange, "Server error", 500);
        }
    }
}
