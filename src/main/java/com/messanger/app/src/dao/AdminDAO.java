package com.messanger.app.src.dao;

import com.messanger.app.src.model.Admin;
import com.messanger.app.src.util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    private final Connection connection;

    public AdminDAO(Connection connection) {
        this.connection = connection;
    }

    public Admin findAdminByEmail(String email) throws SQLException {
        // id / email / password
        String sql = "SELECT * FROM admin WHERE email=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                Admin admin = new Admin(resultSet.getString("email"),resultSet.getString("password"));
                admin.setId(resultSet.getInt("id"));
                return admin;
            }
            return null;
        }
    }

}
