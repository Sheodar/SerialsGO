package dataBase;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection conn;
    public static Connection conn2;

    public static void DBConnect() throws SQLException {
        String urll = "jdbc:sqlite:serialsDB.db";
        conn = null;
        File file = new File("serialsDB2.db");
        if (file.exists() && file.isFile()) {
            try {
                conn = DriverManager.getConnection(urll);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Please select the path to the browser on \"Settings\"");
        }
    }
    public static void DBConnect2() throws SQLException {
        String urll2 = "jdbc:sqlite:browsersDB.db";
        conn2 = null;
        try {
            conn2 = DriverManager.getConnection(urll2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void DBDisconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
