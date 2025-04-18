package com.messanger.app.src.service;

import com.messanger.app.src.dao.AdminDAO;
import com.messanger.app.src.model.Admin;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminService {
    private AdminDAO adminDAO;

    public AdminService(Connection connection) {
        this.adminDAO = new AdminDAO(connection);
    }


    public Admin findbyEmail(String email) throws SQLException {
        return adminDAO.findAdminByEmail(email);
    }

}
