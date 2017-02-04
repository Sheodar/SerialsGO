package Interface;


import methods.SerialsMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static dataBase.ConnectionDB.DBConnect;
import static dataBase.ConnectionDB.DBDisconnect;

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
    private ArrayList<JButton> manyButton = new ArrayList<>();

    private void printButton(ArrayList<String> allSerialsName) {
        for (int x = 0; x < allSerialsName.size(); x++) {
            manyButton.add(x, new JButton(allSerialsName.get(x)));
            allSerials.add(manyButton.get(x));
            allSerials.add(Box.createVerticalStrut(4));
            manyButton.get(x).setAlignmentX(JComponent.CENTER_ALIGNMENT);
            int finalX = x+1;
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



    private GUI() {
        final JFrame frameMain = new JFrame();
        frameMain.setContentPane(mainPanel);
        frameMain.setVisible(true);
        frameMain.setTitle("My Serials");
        frameMain.setVisible(true);
        frameMain.setMinimumSize(new Dimension(400, 550));
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addSerialsPanel.setVisible(false);
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
        allSerials.add(Box.createVerticalStrut(12));
        try {
            printButton(SerialsMethods.allSerial());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (JButton aManyButton : manyButton) {
                        allSerials.remove(aManyButton);
                    }
                    printButton(SerialsMethods.allSerial());
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
    }
    public static void main(String[] args) throws SQLException {
        DBConnect();
        new GUI();
    }
}
