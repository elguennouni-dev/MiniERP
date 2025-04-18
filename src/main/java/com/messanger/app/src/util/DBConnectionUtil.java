package com.messanger.app.src.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectionUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = DBConnectionUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if(inputStream == null) {
                throw new RuntimeException("Cannot find db.properties file");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    public static Connection getConnection() {
        try {
            String URL = PROPERTIES.getProperty("db.url");
            String USER = PROPERTIES.getProperty("db.user");
            String PASSWORD = PROPERTIES.getProperty("db.password");

            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to database.");

            return connection;

        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static void closeQuietly(AutoCloseable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
