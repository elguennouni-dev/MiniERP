package com.messanger.app.src.service;

import com.messanger.app.src.dao.ProductDAO;
import com.messanger.app.src.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(Connection connection) {
        this.productDAO = new ProductDAO(connection);
    }

    public Product getProductById(int id) throws SQLException {
        return productDAO.getProductById(id);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }

    public void updateProduct(Product newProduct) throws SQLException {
        productDAO.updateProduct(newProduct);
    }

    public void deleteProduct(int id) throws SQLException {
        productDAO.deleteProduct(id);
    }

}
