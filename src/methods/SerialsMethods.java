package methods;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static dataBase.ConnectionDB.*;
import static utils.Utils.openerURL;

public class SerialsMethods {

    public static void deleteSerial(String name) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                st.execute("SELECT * FROM serialsURL");
                st.execute("DELETE FROM serialsURL WHERE nameSerial = " + "'"+name+"'");
            }
        }
    }

    public static String pathBrowsers(String name) throws SQLException{
        String resultPATH = null;
        try (Statement st = conn2.createStatement()) {
            st.execute("SELECT * FROM Browsers");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                st.execute("SELECT * FROM Browsers WHERE name = '"+name+"'");
                ResultSet res2 = st.getResultSet();
                while (res2.next()) {
                    resultPATH = res2.getString("path");
                }res2.close();
            }
        }
        return resultPATH;
    }

    public static ArrayList<String> allSerial() throws SQLException {
        ArrayList<String> resultSerials = new ArrayList<>();
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                st.execute("SELECT * FROM serialsURL");
                ResultSet res2 = st.getResultSet();
                while (res2.next()) {
                    String name = res2.getString("nameSerial");
                    resultSerials.add(name);

                }res2.close();
            }
        }
        return resultSerials;
    }

    public static void addSerial(String serialName, String URL) throws SQLException {
        try (Statement st = conn.createStatement()) {
            String[] checkUrlFirst = URL.split("://");
            if (Objects.equals(checkUrlFirst[0], "http")) {
                st.execute("INSERT INTO serialsURL (nameSerial, URL) VALUES (" +
                        "'" + serialName + "','" + URL + "')");
            } else if (Objects.equals(checkUrlFirst[0], "https")) {
                st.execute("INSERT INTO serialsURL (nameSerial, URL) VALUES (" +
                        "'" + serialName + "','" + URL + "')");
            } else {
                String[] checkUrlSecond = URL.split("\\.");
                if (Objects.equals(checkUrlSecond[0], "www")) {
                    st.execute("INSERT INTO serialsURL (nameSerial, URL) VALUES (" +
                            "'" + serialName + "','" + URL + "')");
                } else {
                    System.out.println("[Please. print correct URL (http://<URL> or https://<URL> or www.<URL>]");
                }
            }
        }
    }

    public static void openSerial3(String name, String path) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL WHERE name = " + name);
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
            } else {
                String URL = res.getString("URL");
                try {
                    openerURL(URL, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }//st.execute("SELECT * FROM serialsURL WHERE idSerial = " + idSerial);
    public static void openSerial(String name, String path) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL WHERE nameSerial = '" + name+"'");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
            } else {
                String URL = res.getString("URL");
                try {
                    openerURL(URL, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void changeSerialName(String newName, String oldName) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                st.execute("UPDATE serialsURL SET nameSerial = '" + newName + "' WHERE nameSerial = " + "'"+oldName+"'");
            }
        }
    }
    public static void changeSerialURL(String newURL, String oldName) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            String[] checkUrlFirst = newURL.split("://");
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
                res.close();
            } else {
                if (Objects.equals(checkUrlFirst[0], "http")) {
                    st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE nameSerial = " + "'"+oldName+"'");
                } else if (Objects.equals(checkUrlFirst[0], "https")) {
                    st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE nameSerial = " + "'"+oldName+"'");
                } else {
                    String[] checkUrlSecond = newURL.split("\\.");
                    if (Objects.equals(checkUrlSecond[0], "www")) {
                        st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE nameSerial = " + "'"+oldName+"'");
                    } else {
                        System.out.println("[Please. print correct URL (http://<URL> or https://<URL> or www.<URL>]");
                    }
                }
            }
        }
    }
}