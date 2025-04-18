package com.messanger.app.src.controller;

import com.messanger.app.src.model.Product;
import com.messanger.app.src.service.ProductService;
import com.messanger.app.src.util.BaseHttpHandler;
import com.messanger.app.src.util.DBConnectionUtil;
import com.messanger.app.src.util.RequestHelper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

public class ProductController extends BaseHttpHandler {

    private final ProductService productService = new ProductService(DBConnectionUtil.getConnection());

    @Override
    protected void doHandle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String query = exchange.getRequestURI().getQuery();

            switch (method) {
                case "GET":
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
                    break;

                case "POST":
                    Product newProduct = RequestHelper.parseRequestBody(exchange, Product.class);

                    if (productService.getProductById(newProduct.getId()) != null) {
                        RequestHelper.sendMessage(exchange, "Product already exists", 400);
                        return;
                    }

                    productService.addProduct(newProduct);
                    RequestHelper.sendMessage(exchange, "Product created", 201);
                    break;

                case "PUT":
                    Product updatedProduct = RequestHelper.parseRequestBody(exchange, Product.class);
                    productService.updateProduct(updatedProduct);
                    RequestHelper.sendMessage(exchange, "Product updated", 200);
                    break;

                case "DELETE":
                    if (query != null && query.contains("id=")) {
                        int id = Integer.parseInt(query.split("=")[1]);
                        productService.deleteProduct(id);
                        RequestHelper.sendMessage(exchange, "Product deleted", 200);
                    } else {
                        RequestHelper.sendMessage(exchange, "Missing id parameter", 400);
                    }
                    break;

                default:
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                RequestHelper.sendMessage(exchange, "Server error", 500);
            } catch (Exception innerEx) {
                innerEx.printStackTrace();
            }
        }
    }

}

