package Interface;


import methods.SerialsMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static dataBase.ConnectionDB.*;

public class GUI extends JFrame {
    public static int buttonSerial = 0;
    private JPanel mainPanel;
    private JButton addSerial;
    private JPanel allSerials;
    private JPanel addSerialsPanel;
    private JTextField addNameSerial;
    private JTextField addURLSerial;
    private JButton addButton;
    private JButton cancelButton;
    private JLabel nameSerial;
    private JLabel URLSerial;
    private JPanel BotBar;
    private JButton UPButton;
    private JButton changeSerial;
    private JLabel youSerials;
    private JLabel clickTo;
    private JButton settingsButton;
    private JPanel pickBrowse;
    private JButton pickIe;
    private ArrayList<JButton> manyButton = new ArrayList<>();
    private static JTextField fieldOpera = new JTextField();
    private static JTextField fieldChrome = new JTextField();
    private static JTextField fieldIe = new JTextField();

    private void printButton(ArrayList<String> allSerialsName) {
        for (int x = 0; x < allSerialsName.size(); x++) {
            GridBagConstraints c = new GridBagConstraints();
            manyButton.add(x, new JButton(allSerialsName.get(x)));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = x;
            c.insets.set(3, 3, 3, 3);
            allSerials.add(manyButton.get(x), c);
            manyButton.get(x).setAlignmentX(JComponent.CENTER_ALIGNMENT);
            int finalX = x + 2;
            buttonSerial++;
            manyButton.get(x).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String path = SerialsMethods.pathBrowsers("main");
                        if (Objects.equals(path, "")) {
                            path = "1";
                        }
                        SerialsMethods.openSerial(finalX, path);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    private void createAllSerials() {
        allSerials.setLayout(new GridBagLayout());
        try {
            printButton(SerialsMethods.allSerial());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private GUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = 400;
        int sizeHeight = 550;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;

        JFrame frameMain = new JFrame();
        frameMain.setContentPane(mainPanel);
        frameMain.setTitle("My Serials");
        frameMain.setVisible(true);
        frameMain.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        frameMain.setMinimumSize(new Dimension(400, 650));
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addSerialsPanel.setVisible(false);
        createAllSerials();

        Font font1 = new Font("Verdana", Font.PLAIN, 9);
        Font font2 = new Font("Verdana", Font.BOLD, 14);
        clickTo.setFont(font1);
        youSerials.setFont(font2);

        addSerial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSerialsPanel.setVisible(true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNameSerial.setText("");
                addURLSerial.setText("");
                addSerialsPanel.setVisible(false);
            }
        });

        UPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    allSerials.removeAll();
                    printButton(SerialsMethods.allSerial());
                    frameMain.setSize(new Dimension(401, 500));
                    frameMain.setSize(new Dimension(400, 500));
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SerialsMethods.addSerial(addNameSerial.getText(), addURLSerial.getText());
                    addNameSerial.setText("");
                    addURLSerial.setText("");
                    addSerialsPanel.setVisible(false);
                    allSerials.removeAll();
                    printButton(SerialsMethods.allSerial());
                    frameMain.setSize(new Dimension(400, 500));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        changeSerial.addActionListener(new ActionListener() {
            JComboBox<String> comboBox;

            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeWidth = 400;
                int sizeHeight = 200;
                int locationX = (screenSize.width - sizeWidth) / 2;
                int locationY = (screenSize.height - sizeHeight) / 2;
                JFrame frameChange = new JFrame();
                frameChange.setTitle("Change Serial");
                frameChange.setBounds(locationX, locationY, sizeWidth, sizeHeight);
                frameChange.setMinimumSize(new Dimension(400, 200));
                frameChange.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                frameMain.setVisible(false);
                frameChange.setVisible(true);
                JPanel panelChange = new JPanel(new GridBagLayout());
                frameChange.setContentPane(panelChange);
                GridBagConstraints c = new GridBagConstraints();
                try {
                    comboBox = new JComboBox<>(SerialsMethods.allSerial().toArray(new String[SerialsMethods.allSerial().size()]));
                    c.weightx = 0.5;
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 0;
                    c.gridy = 0;
                    c.insets.set(3, 3, 3, 3);
                    panelChange.add(comboBox, c);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                JLabel name = new JLabel("Name: ");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 0;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(name, c);

                JTextField nameField = new JTextField();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 3;
                c.gridx = 2;
                c.gridy = 0;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(nameField, c);

                JLabel URL = new JLabel("URL: ");
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridy = 1;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(URL, c);

                JTextField URLField = new JTextField();
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 3;
                c.gridx = 2;
                c.gridy = 1;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(URLField, c);

                JButton change = new JButton("Change");
                c.fill = GridBagConstraints.NONE;
                c.ipadx = 70;
                c.weightx = 1;
                c.gridx = 0;
                c.gridy = 2;
                c.gridwidth = 2;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(change, c);

                JButton delete = new JButton("Delete");
                c.fill = GridBagConstraints.NONE;
                c.ipadx = 70;
                c.weightx = 1;
                c.gridx = 1;
                c.gridy = 2;
                c.gridwidth = 2;
                c.insets.set(3, 3, 3, 3);
                panelChange.add(delete, c);

                JButton cancel = new JButton("Cancel");
                c.fill = GridBagConstraints.NONE;
                c.ipadx = 70;
                c.weightx = 1;
                c.gridx = 1;
                c.gridy = 3;
                c.gridwidth = 2;
                c.insets.set(3, 3, 3, 157);
                panelChange.add(cancel, c);

                delete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nameField.setText("");
                        URLField.setText("");
                        try {
                            SerialsMethods.deleteSerial((String) comboBox.getSelectedItem());
                            comboBox.removeAllItems();
                            for (int x = 0; 0 < SerialsMethods.allSerial().size(); x++) {
                                if (x == SerialsMethods.allSerial().size())
                                    break;
                                comboBox.addItem(SerialsMethods.allSerial().get(x));
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        allSerials.removeAll();
                        try {
                            printButton(SerialsMethods.allSerial());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frameMain.setSize(new Dimension(400, 500));
                        nameField.setText("");
                        URLField.setText("");
                        frameMain.setVisible(true);
                        frameChange.setVisible(false);
                    }
                });

                change.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!nameField.getText().equals("")) {
                                SerialsMethods.changeSerialName(nameField.getText(), (String) comboBox.getSelectedItem());
                            }
                            if (!URLField.getText().equals("")) {
                                SerialsMethods.changeSerialURL(URLField.getText(), (String) comboBox.getSelectedItem());
                            }
                            nameField.setText("");
                            URLField.setText("");
                            comboBox.removeAllItems();
                            for (int x = 0; 0 < SerialsMethods.allSerial().size(); x++) {
                                if (x == SerialsMethods.allSerial().size())
                                    break;
                                comboBox.addItem(SerialsMethods.allSerial().get(x));
                            }
                            allSerials.removeAll();
                            printButton(SerialsMethods.allSerial());
                            frameMain.setSize(new Dimension(400, 500));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });


            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sizeWidth = 400;
                int sizeHeight = 175;
                int locationX = (screenSize.width - sizeWidth) / 2;
                int locationY = (screenSize.height - sizeHeight) / 2;
                JFrame frameSettings = new JFrame();
                frameSettings.setTitle("Settings");
                frameSettings.setBounds(locationX, locationY, sizeWidth, sizeHeight);
                frameSettings.setMinimumSize(new Dimension(400, 175));
                frameSettings.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                frameMain.setVisible(false);
                frameSettings.setVisible(true);
                JPanel panelSettings = new JPanel(new GridBagLayout());
                frameSettings.setContentPane(panelSettings);
                GridBagConstraints c = new GridBagConstraints();

                JLabel name = new JLabel(new ImageIcon("other/chrome.jpg"));
                c.fill = GridBagConstraints.NONE;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridy = 0;
                c.insets.set(3, 0, 3, 0);
                panelSettings.add(name, c);
                name = new JLabel(new ImageIcon("other/opera.jpg"));
                c.fill = GridBagConstraints.NONE;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridy = 1;
                c.insets.set(3, -15, 3, -15);
                panelSettings.add(name, c);
                name = new JLabel(new ImageIcon("other/IE.jpg"));
                c.fill = GridBagConstraints.NONE;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridy = 2;
                c.insets.set(3, -15, 3, -15);
                panelSettings.add(name, c);

                c.fill = GridBagConstraints.HORIZONTAL;
                fieldChrome.setPreferredSize(new Dimension(100, 27));
                c.weightx = 3;
                c.gridx = 1;
                c.gridy = 0;
                c.insets.set(3, 3, 3, 3);
                try {
                    fieldChrome.setText(SerialsMethods.pathBrowsers("chrome"));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                panelSettings.add(fieldChrome, c);


                c.fill = GridBagConstraints.HORIZONTAL;
                fieldOpera.setPreferredSize(new Dimension(100, 27));
                c.weightx = 3;
                c.gridx = 1;
                c.gridy = 1;
                c.insets.set(3, 3, 3, 3);
                try {
                    fieldOpera.setText(SerialsMethods.pathBrowsers("opera"));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                panelSettings.add(fieldOpera, c);

                c.fill = GridBagConstraints.HORIZONTAL;
                fieldIe.setPreferredSize(new Dimension(1, 27));
                c.weightx = 3;
                c.gridx = 1;
                c.gridy = 2;
                c.insets.set(3, 3, 3, 3);
                try {
                    fieldIe.setText(SerialsMethods.pathBrowsers("ie"));
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                panelSettings.add(fieldIe, c);

                JButton saveButton = new JButton("Save");
                c.fill = GridBagConstraints.NONE;
                c.ipadx = 70;
                c.weightx = 1;
                c.gridx = 1;
                c.gridy = 4;
                c.gridwidth = 2;
                c.insets.set(0, 35, 0, 10);
                panelSettings.add(saveButton, c);

                JButton pickChrome = new JButton("Browse");
                pickChrome.setPreferredSize(new Dimension(-20, 26));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1;
                c.gridx = 2;
                c.gridy = 0;
                c.gridwidth = 2;
                c.insets.set(0, 0, 0, 3);
                panelSettings.add(pickChrome, c);

                JButton pickOpera = new JButton("Browse");
                pickOpera.setPreferredSize(new Dimension(-20, 26));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1;
                c.gridx = 2;
                c.gridy = 1;
                c.gridwidth = 2;
                c.insets.set(0, 0, 0, 3);
                panelSettings.add(pickOpera, c);

                JButton pickIe = new JButton("Browse");
                pickIe.setPreferredSize(new Dimension(-20, 26));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 1;
                c.gridx = 2;
                c.gridy = 2;
                c.gridwidth = 2;
                c.insets.set(0, 0, 0, 3);
                panelSettings.add(pickIe, c);

                pickChrome.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Открыть файл");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file = fileopen.getSelectedFile();
                            fieldChrome.setText(file.getAbsolutePath());
                        }
                    }
                });

                pickOpera.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Открыть файл");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file = fileopen.getSelectedFile();
                            fieldOpera.setText(file.getAbsolutePath());
                        }
                    }
                });

                pickIe.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileopen = new JFileChooser();
                        int ret = fileopen.showDialog(null, "Открыть файл");
                        if (ret == JFileChooser.APPROVE_OPTION) {
                            File file = fileopen.getSelectedFile();
                            fieldIe.setText(file.getAbsolutePath());
                        }
                    }
                });

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        allSerials.removeAll();
                        try {
                            printButton(SerialsMethods.allSerial());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            try (Statement st = conn2.createStatement()) {
                                st.execute("SELECT * FROM Browsers");
                                ResultSet res = st.getResultSet();
                                if (!res.next()) {
                                    System.out.println("Not created serials. Please, create signature.");
                                    res.close();
                                } else {
                                    st.execute("UPDATE Browsers SET path = '" + fieldChrome.getText() + "' WHERE name = " + "'" + "chrome" + "'");
                                    st.execute("UPDATE Browsers SET path = '" + fieldOpera.getText() + "' WHERE name = " + "'" + "opera" + "'");
                                    st.execute("UPDATE Browsers SET path = '" + fieldIe.getText() + "' WHERE name = " + "'" + "ie" + "'");
                                }
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        frameMain.setSize(new Dimension(400, 500));
                        frameMain.setVisible(true);
                        frameSettings.setVisible(false);
                    }
                });


            }
        });

        pickBrowse.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton pickChrome = new JButton(new ImageIcon("other/chrome.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(pickChrome, c);

        JButton pickOpera = new JButton(new ImageIcon("other/opera.jpg"));
        pickOpera.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(pickOpera, c);

        JButton pickIe = new JButton(new ImageIcon("other/IE.jpg"));
        pickIe.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(pickIe, c);

        JButton pickDefault = new JButton("Default Browser");
        pickIe.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = -2;
        c.weightx = 0.2;
        c.gridx = 1;
        c.gridy = 3;
        c.insets.set(3, -49, -4, 1);
        pickBrowse.add(pickDefault, c);

        JLabel chromeAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(chromeAc, c);


        JLabel operaAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(operaAc, c);

        JLabel ieAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets.set(3, 4, 3, 0);
        pickBrowse.add(ieAc, c);

        chromeAc.setVisible(false);
        operaAc.setVisible(false);
        ieAc.setVisible(false);



        try {
            try (Statement st = conn2.createStatement()) {
                String link;
                String name;
                st.execute("SELECT * FROM Browsers");
                ResultSet w = st.getResultSet();

                while (w.next()) {
                        name = w.getString("name");
                        link = w.getString("path");
                        if (Objects.equals(link, SerialsMethods.pathBrowsers("main"))){
                            switch (name) {
                                case "opera":
                                    operaAc.setVisible(true);
                                    break;
                                case "chrome":
                                    chromeAc.setVisible(true);
                                    break;
                                case "ie":
                                    ieAc.setVisible(true);
                                    break;
                                case "":

                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                }


            }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }


        pickDefault.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try (Statement st = conn2.createStatement()) {
                        st.execute("UPDATE Browsers SET path = '" + "" + "' WHERE name = " + "'" + "main" + "'");
                            operaAc.setVisible(false);
                            ieAc.setVisible(false);
                            chromeAc.setVisible(false);
                        }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });



        pickChrome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try (Statement st = conn2.createStatement()) {
                        st.execute("SELECT * FROM Browsers WHERE name = 'chrome'");
                        ResultSet r = st.getResultSet();
                        String path = r.getString("path");
                        if (Objects.equals(path, "")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Please select the path to the browser on \"Settings\"");
                        } else {
                            st.execute("UPDATE Browsers SET path = '" + path + "' WHERE name = " + "'" + "main" + "'");
                            operaAc.setVisible(false);
                            ieAc.setVisible(false);
                            chromeAc.setVisible(true);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pickOpera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try (Statement st = conn2.createStatement()) {
                        st.execute("SELECT * FROM Browsers WHERE name = 'opera'");
                        ResultSet r = st.getResultSet();
                        String path = r.getString("path");
                        if (Objects.equals(path, "")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Please select the path to the browser on \"Settings\"");
                        } else {
                            st.execute("UPDATE Browsers SET path = '" + path + "' WHERE name = " + "'" + "main" + "'");
                            operaAc.setVisible(true);
                            ieAc.setVisible(false);
                            chromeAc.setVisible(false);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        pickIe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    try (Statement st = conn2.createStatement()) {
                        st.execute("SELECT * FROM Browsers WHERE name = 'ie'");
                        ResultSet r = st.getResultSet();
                        String path = r.getString("path");
                        if (Objects.equals(path, "")) {
                            JOptionPane.showMessageDialog(new JFrame(), "Please select the path to the browser on \"Settings\"");
                        } else {
                            st.execute("UPDATE Browsers SET path = '" + path + "' WHERE name = " + "'" + "main" + "'");
                            operaAc.setVisible(false);
                            ieAc.setVisible(true);
                            chromeAc.setVisible(false);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws SQLException {
        DBConnect();
        DBConnect2();
        new GUI();
    }
}
