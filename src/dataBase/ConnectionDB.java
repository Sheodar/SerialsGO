package dataBase;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection conn;
    public static Connection conn2;

    public static void DBConnect2() throws SQLException {
        String urll2 = "jdbc:sqlite:browsersDB.db";
        conn2 = null;
        try {
            conn2 = DriverManager.getConnection(urll2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
