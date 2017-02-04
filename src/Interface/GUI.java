package Interface;


import methods.SerialsMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static dataBase.ConnectionDB.DBConnect;

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
    private ArrayList<JButton> manyButton = new ArrayList<>();


    private void printButton(ArrayList<String> allSerialsName) {
        for (int x = 0; x < allSerialsName.size(); x++) {
            manyButton.add(x, new JButton(allSerialsName.get(x)));
            allSerials.add(manyButton.get(x));
            manyButton.get(x).setAlignmentX(JComponent.CENTER_ALIGNMENT);
            int finalX = x + 1;
            buttonSerial++;
            manyButton.get(x).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        SerialsMethods.openSerial(finalX);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    }

    private void createAllSerials() {
        allSerials.setLayout(new BoxLayout(allSerials, BoxLayout.Y_AXIS));
        Font font1 = new Font("Verdana", Font.PLAIN, 9);
        Font font2 = new Font("Verdana", Font.BOLD, 13);
        JLabel you = new JLabel("You'r Serials");
        you.setFont(font2);
        you.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JLabel youOpen = new JLabel("(click to open)");
        youOpen.setFont(font1);
        youOpen.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        allSerials.add(you);
        allSerials.add(youOpen);
        allSerials.add(Box.createVerticalStrut(10));
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
        frameMain.setMinimumSize(new Dimension(400, 550));
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addSerialsPanel.setVisible(false);
        createAllSerials();

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
                    for (JButton aManyButton : manyButton) {
                        allSerials.remove(aManyButton);
                    }
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
                    for (JButton aManyButton : manyButton) {
                        allSerials.remove(aManyButton);
                    }
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
                frameChange.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
                            for (JButton aManyButton : manyButton) {
                                allSerials.remove(aManyButton);
                            }
                            printButton(SerialsMethods.allSerial());
                            frameMain.setSize(new Dimension(400, 500));
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });


            }
        });
    }

    public static void main(String[] args) throws SQLException {
        DBConnect();
        new GUI();
    }
}
