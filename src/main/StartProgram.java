package main;

import methods.SerialsMethods;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static dataBase.ConnectionDB.DBConnect;
import static dataBase.ConnectionDB.DBDisconnect;
import static utils.Helper.helper;
import static utils.Utils.openerURL;


public class StartProgram extends SQLException {
    private static SerialsMethods startSerial = new SerialsMethods();

    public static void main(String[] args) throws SQLException {
        DBConnect();
        System.out.println("Welcome!");
        System.out.println("You can open this serial(s): ");
        startSerial.allSerial();
        System.out.println("________________________________________");
        Scanner printOpt = new Scanner(System.in);
        String print;
        String[] command;
        do {
            System.out.println("Print command");
            print = printOpt.nextLine();
            command = print.trim().split(" ");
            switch (command[0]) {
                case "all":
                    switch (command[1]) {
                        case "serials":
                            startSerial.allSerial();
                            break;
                        default:
                            System.out.println("[Invalid command]");
                            break;
                    }
                    break;
                case "add":
                    switch (command[1]) {
                        case "serial":
                            startSerial.addSerial();
                            break;
                        default:
                            System.out.println("[Invalid command]");
                            break;
                    }
                    break;

                case "change":
                    switch (command[1]) {
                        case "serial":
                            if (command[2] == null) {
                                System.out.println("[Invalid command]");
                                break;
                            }
                            try {
                                Integer ID = new Integer(command[2]);
                                startSerial.changeSerial(ID);
                            } catch (Exception e) {
                                System.out.println("[Invalid command]");
                            }
                            break;
                        default:
                            System.out.println("[Invalid command]");
                            break;
                    }
                    break;
                case "del":
                    switch (command[1]) {
                        case "serial":
                            if (command[2] == null) {
                                System.out.println("[Invalid command]");
                                break;
                            }
                            try {
                                Integer ID = new Integer(command[2]);
                                startSerial.deleteSerial(ID);
                            } catch (Exception e) {
                                System.out.println("[Invalid command]");
                            }
                            break;
                        default:
                            System.out.println("[Invalid command]");
                            break;
                    }
                    break;
                case "open":
                    if (command[1] == null) {
                        System.out.println("[Invalid command]");
                        break;
                    }
                    try {
                        Integer ID = new Integer(command[1]);
                        startSerial.openSerial(ID);
                    } catch (Exception e) {
                        System.out.println("[Invalid command]");
                    }
                    break;
                case "help":
                    helper();
                    break;
                case "site":
                    openerURL("https://github.com/Sheodar/SerialsGO/");
                    break;
                default:
                    if (Objects.equals(command[0], "exit")) {
                        break;
                    }
                    System.out.println("[Invalid command]");
                    break;
            }
        } while (!Objects.equals(command[0], "exit"));
        DBDisconnect();
    }


}
