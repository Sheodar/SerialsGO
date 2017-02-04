package methods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static dataBase.ConnectionDB.conn;
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
                }
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

    public static void openSerial(Integer idSerial) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
            } else {
                st.execute("SELECT * FROM serialsURL WHERE idSerial = " + idSerial);
                res = st.getResultSet();
                String URL = res.getString("URL");
                openerURL(URL);
            }
        }
    }

    public static void changeSerialName(String newName, String oldName) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
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

    public void changeSerial(Integer idSerial) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("SELECT * FROM serialsURL");
            ResultSet res = st.getResultSet();
            if (!res.next()) {
                System.out.println("Not created serials. Please, create signature.");
            } else {
                Scanner printOpt = new Scanner(System.in);
                String outConsoleCatOpt;
                do {
                    System.out.println("[1]- Change name; [2]- Change URL; [3]- Back;");
                    outConsoleCatOpt = printOpt.nextLine();
                    switch (outConsoleCatOpt) {
                        case "1":
                            System.out.println("Enter new name: ");
                            String newName = printOpt.nextLine();
                            st.execute("UPDATE serialsURL SET nameSerial = '" + newName + "' WHERE idSerial = " + idSerial);
                            break;
                        case "2":
                            System.out.println("Enter new URL: ");
                            String newURL = printOpt.nextLine();
                            String[] checkUrlFirst = newURL.split("://");
                            if (Objects.equals(checkUrlFirst[0], "http")) {
                                st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE idSerial = " + idSerial);
                            } else if (Objects.equals(checkUrlFirst[0], "https")) {
                                st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE idSerial = " + idSerial);
                            } else {
                                String[] checkUrlSecond = newURL.split("\\.");
                                if (Objects.equals(checkUrlSecond[0], "www")) {
                                    st.execute("UPDATE serialsURL SET URL = '" + newURL + "' WHERE idSerial = " + idSerial);
                                } else {
                                    System.out.println("[Please. print correct URL (http://<URL> or https://<URL> or www.<URL>]");
                                }
                            }
                            break;
                        default:
                            if (Objects.equals(outConsoleCatOpt, "3")) {
                                break;
                            }
                            System.out.println("[Invalid command]");
                            break;
                    }
                }
                while (!Objects.equals(outConsoleCatOpt, "3"));
            }
        }
    }


}