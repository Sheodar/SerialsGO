package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection conn;
    public static void DBConnect() throws SQLException {
        String urll = "jdbc:sqlite:serialsDB.db";
        conn = null;
        try {
            conn = DriverManager.getConnection(urll);
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
