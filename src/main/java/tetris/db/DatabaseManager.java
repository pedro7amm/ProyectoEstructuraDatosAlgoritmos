/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.db;

import java.sql.*;


public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/tetris_db";
    private static final String USER = "root"; //
    private static final String PASSWORD = "Local123";
    private static Connection connection;

    private DatabaseManager() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al conectar con la base de datos.");
            }
        }
        return connection;
    }
}
