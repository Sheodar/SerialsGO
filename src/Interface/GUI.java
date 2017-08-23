package Interface;


import methods.SerialsMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static dataBase.ConnectionDB.*;
import static methods.SerialsMethods.getComment;
import static methods.SerialsMethods.saveComment;
import static utils.Utils.openerURL;

public class GUI extends JFrame {
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
    private JPanel rPanel;
    private JPanel workPanel;
    private JButton openSerial;
    private JTextArea comment;
    private JButton saveComm;
    private JScrollPane scrollSer;
    private JButton pickIe;
    private ArrayList<JButton> manyButton = new ArrayList<>();
    private static JTextField fieldOpera = new JTextField();
    private static JTextField fieldChrome = new JTextField();
    private static JTextField fieldIe = new JTextField();
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static JFrame frameMain = new JFrame();
    private static ArrayList<String> allSerialsName = new ArrayList<>();
    private GridBagConstraints c = new GridBagConstraints();

    private void printButton(ArrayList<String> allSerialsName) {
        for (int x = 0; x < allSerialsName.size(); x++) {
            GridBagConstraints c = new GridBagConstraints();
            manyButton.add(x, new JButton(allSerialsName.get(x)));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.insets.set(3, 3, 3, 3);
            allSerials.add(manyButton.get(x), c);
            manyButton.get(x).setAlignmentX(JComponent.CENTER_ALIGNMENT);
            manyButton.get(x).setFocusPainted(false);
            int finalX = x;

            manyButton.get(x).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    workPanel.setVisible(true);
                    if (openSerial.getActionListeners().length != 0)
                    openSerial.removeActionListener(openSerial.getActionListeners()[0]);
                    if (saveComm.getActionListeners().length != 0)
                        saveComm.removeActionListener(saveComm.getActionListeners()[0]);
                    manyButton.get(finalX).setBackground(Color.decode("#90EE90"));
                    for(int x = 0; x<manyButton.size();x++){
                        if(x!=finalX)
                            manyButton.get(x).setBackground(null);
                    }
                    try {
                        createPanelInfo(allSerialsName.get(finalX), finalX);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

        private void createPanelInfo(String name, int id) throws SQLException {
        comment.setText(getComment(name));
        ActionListener open = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String path = SerialsMethods.pathBrowsers("main");
                    if (Objects.equals(path, "")) {
                        path = "1";
                    }
                    SerialsMethods.openSerial(name, path);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
//                workPanel.setVisible(false);
//                manyButton.get(id).setBackground(null);
                openSerial.removeActionListener(openSerial.getActionListeners()[0]);
            }
        };
        ActionListener save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveComment(name, comment.getText());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                workPanel.setVisible(false);
                manyButton.get(id).setBackground(null);
                saveComm.removeActionListener(saveComm.getActionListeners()[0]);
            }
        };
        openSerial.addActionListener(open);
        saveComm.addActionListener(save);
    }


    private void createAllSerials() {
        allSerials.setLayout(new GridBagLayout());
        try {
            printButton(SerialsMethods.allSerial());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private GUI() throws SQLException {
        allSerialsName = SerialsMethods.allSerial();
        int sizeWidth = 420;
        int sizeHeight = 650;
        int locationX = (screenSize.width - sizeWidth) / 2;
        int locationY = (screenSize.height - sizeHeight) / 2;
        frameMain.setContentPane(mainPanel);
        frameMain.setTitle("Serials GO");
        frameMain.setVisible(true);
        frameMain.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        frameMain.setMinimumSize(new Dimension(420, 650));
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addSerialsPanel.setVisible(false);
        rPanel.setVisible(true);
        createAllSerials();
        Font font1 = new Font("Verdana", Font.PLAIN, 9);
        Font font2 = new Font("Verdana", Font.BOLD, 14);
        clickTo.setFont(font1);
        youSerials.setFont(font2);
        addSerial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSerialsPanel.setVisible(true);
                rPanel.setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNameSerial.setText("");
                addURLSerial.setText("");
                addSerialsPanel.setVisible(false);
                rPanel.setVisible(true);
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
                    addSerialsPanel.setVisible(false);
                    rPanel.setVisible(true);
                    int x = manyButton.size();
                    GridBagConstraints c = new GridBagConstraints();
                    manyButton.add(x, new JButton(addNameSerial.getText()));
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.weightx = 0.5;
                    c.gridx = 1;
                    c.insets.set(3, 3, 3, 3);
                    allSerials.add(manyButton.get(x), c);
                    allSerialsName.add(addNameSerial.getText());
                    manyButton.get(x).setAlignmentX(JComponent.CENTER_ALIGNMENT);
                    manyButton.get(x).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                String path = SerialsMethods.pathBrowsers("main");
                                if (Objects.equals(path, "")) {
                                    path = "1";
                                }
                                SerialsMethods.openSerial(allSerialsName.get(x), path);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                    addSerialsPanel.revalidate();
                    allSerials.removeAll();
                    printButton(SerialsMethods.allSerial());
                    allSerials.revalidate();
                    frameMain.setSize(new Dimension(400, 500));
                    addNameSerial.setText("");
                    addURLSerial.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        changeSerial.addActionListener(new ActionListener() {
            JComboBox<String> comboBox;
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameChange = new JFrame();
                if (!Objects.equals(frameChange.getTitle(), "Change Serial")) {
                    int sizeWidth = 420;
                    int sizeHeight = 175;
                    int locationX = (screenSize.width - sizeWidth) / 2;
                    int locationY = (screenSize.height - sizeHeight) / 2;
                    frameChange = new JFrame();
                    frameChange.setTitle("Change Serial");
                    frameChange.setBounds(locationX, locationY, sizeWidth, sizeHeight);
                    frameChange.setMinimumSize(new Dimension(sizeWidth, sizeHeight));
                    frameChange.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                    frameChange.setAlwaysOnTop(true);
//                    frameChange.setResizable(false);
                    frameMain.setVisible(false);
                    frameChange.setVisible(true);
                    JPanel panelChange = new JPanel(new GridBagLayout());
                    frameChange.setContentPane(panelChange);
                    GridBagConstraints c2 = new GridBagConstraints();
                    try {
                        comboBox = new JComboBox<>(SerialsMethods.allSerial().toArray(new String[SerialsMethods.allSerial().size()]));
//                    c.weightx = 1;
                        c2.fill = GridBagConstraints.HORIZONTAL;
                        c2.gridx = 0;
                        c2.gridy = 0;
                        c2.insets.set(0, 0, 0, 0);
                        panelChange.add(comboBox, c2); //TODO: Тут нужно сделать нормальное окно, а не как даун.
                        frameChange.setMinimumSize(new Dimension(Math.toIntExact((long) comboBox.getPreferredSize().getWidth())+190, sizeHeight));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    JLabel name = new JLabel("Name: ");
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.weightx = 0.5;
                    c2.gridx = 1;
                    c2.gridy = 0;
                    c2.insets.set(0, 5, 0, 0);
                    panelChange.add(name, c2);

                    JTextField nameField = new JTextField();
                    nameField.setPreferredSize(new Dimension(150, 20));
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.weightx = 3;
                    c2.gridx = 2;
                    c2.gridy = 0;
                    c2.insets.set(0, 0, 0, 0);
                    panelChange.add(nameField, c2);
//
                    JLabel URL = new JLabel("URL: ");
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.weightx = 0.5;
                    c2.gridx = 1;
                    c2.gridy = 1;
                    c2.insets.set(0, 5, 0, 0);
                    panelChange.add(URL, c2);
//
                    JTextField URLField = new JTextField();
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.weightx = 3;
                    c2.gridx = 2;
                    c2.gridy = 1;
                    c2.insets.set(0, 0, 0, 0);
                    panelChange.add(URLField, c2);
//
                    JButton change = new JButton("Change");
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.ipadx = 70;
//                c.weightx = 1;
                    c2.gridx = 0;
                    c2.gridy = 3;
//                c.gridwidth = 2;
                    c2.insets.set(5, 60, 0, 20);
                    panelChange.add(change, c2);
//
                    JButton delete = new JButton("Delete");
                    c2.fill = GridBagConstraints.HORIZONTAL;
//                c.ipadx = 40;
//                c.weightx = 1;
                    c2.gridx = 1;
                    c2.gridy = 3;
                    c2.gridwidth = 2;
                    c2.insets.set(5, 25, 0, 60);
                    panelChange.add(delete, c2);
//
                    JButton cancel = new JButton("Cancel");
                    c2.fill = GridBagConstraints.NONE;
                    c2.ipadx = 50;
//                c.weightx = 2;
                    c2.gridx = 0;
                    c2.gridy = 4;
                    c2.gridwidth = 3;
                    c2.insets.set(10, 0, 0, 0);
                    panelChange.add(cancel, c2);

                    JFrame finalFrameChange1 = frameChange;
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
                                    finalFrameChange1.setMinimumSize(new Dimension(Math.toIntExact((long) comboBox.getPreferredSize().getWidth())+190, sizeHeight));
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
                                printButton(SerialsMethods.allSerial());  //фикс.
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            frameMain.setSize(new Dimension(400, 500));
                            nameField.setText("");
                            URLField.setText("");
                            frameMain.setVisible(true);
                            finalFrameChange1.setVisible(false);
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
                                finalFrameChange1.setMinimumSize(new Dimension(Math.toIntExact((long) comboBox.getPreferredSize().getWidth())+190, sizeHeight));
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }

                        }
                    });

                }else{
                    frameChange.setVisible(true);
                }
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
                frameSettings.setAlwaysOnTop(true);
                frameSettings.setTitle("Settings");
                frameSettings.setBounds(locationX, locationY, sizeWidth, sizeHeight);
                frameSettings.setMinimumSize(new Dimension(400, 175));
                frameSettings.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                frameSettings.setResizable(false);
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
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets.set(3, 0, 3, 110);
        pickBrowse.add(pickChrome, c);

        JButton pickOpera = new JButton(new ImageIcon("other/opera.jpg"));
        pickOpera.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets.set(3, 0, 3, 110);
        pickBrowse.add(pickOpera, c);

        JButton pickIe = new JButton(new ImageIcon("other/IE.jpg"));
        pickIe.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.2;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets.set(3, 0, 3, 110);
        pickBrowse.add(pickIe, c);

        JButton pickDefault = new JButton("Default Browser");
        pickIe.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 3;
        c.insets.set(3, -23, -4, 2);
        pickBrowse.add(pickDefault, c);

        JLabel chromeAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 4;
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 0;
        c.insets.set(0, 0, 96, 110);
        pickBrowse.add(chromeAc, c);


        JLabel operaAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 3;
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        c.insets.set(0, 0, 60, 110);
        pickBrowse.add(operaAc, c);

        JLabel ieAc = new JLabel(new ImageIcon("other/backgroundBrow.jpg"));
        pickChrome.setPreferredSize(new Dimension(38, 30));
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 2;
        c.gridwidth = 2;
        c.weightx = 0.2;
        c.gridx = 0;
        c.gridy = 2;
        c.insets.set(0, 0, 24, 110);
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
                    if (Objects.equals(link, SerialsMethods.pathBrowsers("main"))) {
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
        DBConnect2();
        String urll = "jdbc:sqlite:serialsDB.db";
        conn = null;
        File file = new File("serialsDB.db");

        File file2;
        String url;
        try (Statement st = conn2.createStatement()) {
            st.execute("SELECT * FROM pathSerialsDB WHERE id = 1");
            ResultSet r = st.getResultSet();
            url = r.getString("path");
            file2 = new File(url);
        }
        if (file.exists() && file.isFile()) {
            try {
                conn = DriverManager.getConnection(urll);
                DBConnect2();
                new GUI();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (file2.exists() && file2.isFile()) {
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + url);
                DBConnect2();
                new GUI();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            int sizeWidth = 300;
            int sizeHeight = 140;
            int locationX = (screenSize.width - sizeWidth) / 2;
            int locationY = (screenSize.height - sizeHeight) / 2;
            JFrame frameErrorDB = new JFrame();
            frameErrorDB.setTitle("Error");
            frameErrorDB.setBounds(locationX, locationY, sizeWidth, sizeHeight);
            frameErrorDB.setMinimumSize(new Dimension(300, 140));
            frameErrorDB.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            frameMain.setVisible(false);
            frameErrorDB.setVisible(true);
            JPanel panelErrorDB = new JPanel(new GridBagLayout());
            frameErrorDB.setContentPane(panelErrorDB);
            GridBagConstraints c = new GridBagConstraints();

            Font font1 = new Font("Verdana", Font.PLAIN, 9);

            JLabel mainTextErrorDB = new JLabel("<html><p align=\"center\">Не установлено соединение с базой данных</p>" +
                    "<p align=\"center\">Поместите файл \"SerialsDB.db\"в папку с  .ехе</p>" +
                    "<p align=\"center\">или укажите путь к файлу</p></html>");
            c.fill = GridBagConstraints.CENTER;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 0;
            c.insets.set(3, 0, 0, 0);
            panelErrorDB.add(mainTextErrorDB, c);

            JLabel dlTextErrorDB = new JLabel("<html><p align=\"center\">(Если файл удален, его можно скачать)</p></html>");
            dlTextErrorDB.setFont(font1);
            c.fill = GridBagConstraints.CENTER;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 1;
            c.insets.set(1, 0, 3, 0);
            panelErrorDB.add(dlTextErrorDB, c);

            JButton downloadDB = new JButton("Download");
            downloadDB.setPreferredSize(new Dimension(38, 30));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 3;
            c.weightx = 0.2;
            c.gridx = -1;
            c.gridy = 2;
            c.insets.set(3, 20, 2, 150);
            panelErrorDB.add(downloadDB, c);

            JButton specifyPath = new JButton("Specify path");
            specifyPath.setPreferredSize(new Dimension(38, 30));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.weightx = 0.2;
            c.gridx = 0;
            c.gridy = 2;
            c.insets.set(3, 150, 2, 20);
            panelErrorDB.add(specifyPath, c);
            frameErrorDB.setPreferredSize(new Dimension(300, 140));
            frameErrorDB.setPreferredSize(new Dimension(300, 140));
            frameErrorDB.setResizable(false);

            downloadDB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        openerURL("https://github.com/Sheodar/SerialsGO", "1");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(new JFrame(), "Need Default Browser");
                        e1.printStackTrace();
                    }
                }
            });

            specifyPath.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file = fileopen.getSelectedFile();
                        String path = file.getAbsolutePath();
                        String[] pathMass = path.split("\\\\");
                        String fileName = "serialsDB.db";
                        if (Objects.equals(pathMass[pathMass.length - 1], fileName)) {
                            try {
                                try (Statement st = conn2.createStatement()) {
                                    st.execute("UPDATE pathSerialsDB SET path = '" + path + "' WHERE id = 1");
                                    String urll = "jdbc:sqlite:" + path;
                                    conn = null;
                                    File file1 = new File(path);
                                    if (file1.exists() && file1.isFile()) {
                                        try {
                                            conn = DriverManager.getConnection(urll);
                                            frameErrorDB.setVisible(false);
                                            new GUI();
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });

        }
    }

}
