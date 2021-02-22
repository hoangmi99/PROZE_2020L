package klient.com.company;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * klasa wymuszajaca na graczu podanie swojego nicku
 */
public class Nick extends JFrame {
    /**Jlabel podaj numer ip*/
    JLabel podajIP = new JLabel("PODAJ ADRES IP");
    /**Jlabel podaj numer portu*/
    JLabel podajPort = new JLabel("PODAJ NUMER PORTU");
    /** Jlabel podaj nick*/
    JLabel podajnick = new JLabel("PODAJ NICK");
    /** okno do wporwadzeniu nicku*/
    JTextField wprowadzNickTextField = new JTextField("nick");
    /** okno do wporwadzenia numeru ip*/
    JTextField wprowadzIPTextField = new JTextField("000.000.00.0");
    /**okno do wporwadzenia numeru portu*/
    JTextField wprowadzPortTextField = new JTextField("00000");
    /** przycisk grania online*/
    JButton grajOnlineButton = new JButton("GRAJ ONLINE");
    /** przycisk grania offline*/
    JButton grajOfflineButton = new JButton("GRAJ OFFLINE");
    /** przycisk powrotu*/
    JButton wrocButton = new JButton("WROC");
    /**
     * tworzy okno na wprowadzenie nicku, nadaje przyciskom ich funkcje
     * @param menuButtons przyciski menu glownego
     */
    public Nick(MenuButtons menuButtons) {
        setTitle("LUNAR LANDER");
        setLayout(new GridLayout(2,1));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setLayout(new GridLayout(3,2));
        panel2.setLayout(new GridLayout(3,1));
        panel1.add(podajIP);
        panel1.add(wprowadzIPTextField);
        panel1.add(podajPort);
        panel1.add(wprowadzPortTextField);
        panel1.add(podajnick);
        panel1.add(wprowadzNickTextField);
        panel2.add(grajOnlineButton);
        panel2.add(grajOfflineButton);
        panel2.add(wrocButton);
        add(panel1);
        add(panel2);
        this.setSize(Config.nickWidth, Config.nickHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        grajOnlineButton.setBackground(Color.darkGray);
        grajOnlineButton.setForeground(Color.WHITE);
        grajOfflineButton.setBackground(Color.darkGray);
        grajOfflineButton.setForeground(Color.WHITE);
        wrocButton.setBackground(Color.darkGray);
        wrocButton.setForeground(Color.WHITE);
        podajIP.setOpaque(true);
        podajIP.setHorizontalAlignment(JLabel.CENTER);
        podajIP.setBackground(Color.BLACK);
        podajIP.setForeground(new Color(238, 197, 16));
        podajIP.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 30));
        podajPort.setOpaque(true);
        podajPort.setHorizontalAlignment(JLabel.CENTER);
        podajPort.setBackground(Color.BLACK);
        podajPort.setForeground(new Color(238, 197, 16));
        podajPort.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 30));
        podajnick.setOpaque(true);
        podajnick.setHorizontalAlignment(JLabel.CENTER);
        podajnick.setBackground(Color.BLACK);
        podajnick.setForeground(new Color(238, 197, 16));
        podajnick.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 30));
        wprowadzNickTextField.setHorizontalAlignment(JTextField.CENTER);
        wprowadzNickTextField.setBackground(Color.BLACK);
        wprowadzNickTextField.setForeground(Color.white);
        wprowadzIPTextField.setHorizontalAlignment(JTextField.CENTER);
        wprowadzIPTextField.setBackground(Color.BLACK);
        wprowadzIPTextField.setForeground(Color.white);
        wprowadzPortTextField.setHorizontalAlignment(JTextField.CENTER);
        wprowadzPortTextField.setBackground(Color.BLACK);
        wprowadzPortTextField.setForeground(Color.white);

        /**
         * Listener obsługujący przycisk grania, tworzy okno gry, zamyka okno do wprowadzania nicku
         */
        grajOfflineButton.addActionListener(actionEvent -> {
            if(wprowadzNickTextField.getText().equals("")){

            }else if (!wprowadzNickTextField.getText().equals("") &&!Client.isOnline){
                Game.setNick(wprowadzNickTextField.getText());
                try {
                   // System.out.println("isOnline: "+Client.isOnline);
                    Config.loadConfig();
                    new Game(menuButtons);
                    dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * Listener obsługujący przycisk grania, tworzy okno gry, zamyka okno do wprowadzania nicku
         */
        grajOnlineButton.addActionListener(actionEvent -> {
            Config.savePortAndIP(wprowadzIPTextField.getText(),Integer.parseInt(wprowadzPortTextField.getText()));
            new Client("LOGIN");
            if(wprowadzNickTextField.getText().equals("")){

            }else if (!wprowadzNickTextField.getText().equals("") && Client.isOnline){
                Game.setNick(wprowadzNickTextField.getText());
                try {
                    new Client("GET_CONFIG");
                    Config.loadConfig();
                    new Game(menuButtons);
                   // System.out.println("isOnline: "+Client.isOnline);
                    dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * Listener obsługujący przycisk powrotu, zamyka okno do wprowadzania nicku
         */
        wrocButton.addActionListener(actionEvent -> {
            dispose();
            menuButtons.enabledButtonsTrue();
        });
        /**
         * wykrywa zamknięcie okna do wprowadzania nicku i odblokowywuje przyciski menu glownego
         */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                menuButtons.enabledButtonsTrue();
            }
        });

    }

}
