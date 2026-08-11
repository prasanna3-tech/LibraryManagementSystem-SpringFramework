package org.pras.config;

import org.pras.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    public static void main(String[] args) {

        try (Connection connection = DBConnection.getConnection()) {

            if (connection != null) {
                System.out.println("Connected to MySQL successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

    }
}
