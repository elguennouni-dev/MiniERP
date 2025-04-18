package com.messanger.app.src.auth;

import com.messanger.app.src.model.Admin;
import com.messanger.app.src.service.AdminService;
import com.messanger.app.src.util.PasswordEncryptor;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {

    private final AdminService adminService;

    public AuthService(Connection connection) {
        this.adminService = new AdminService(connection);
    }


    public Admin login(String email, String password) throws SQLException {
        Admin admin = adminService.findbyEmail(email);
        if (admin != null) {
            String decryptedPassword = PasswordEncryptor.decode(admin.getPassword());
            if (decryptedPassword.equals(password)) {
                return admin;
            }
        }
        return null;
    }

}
