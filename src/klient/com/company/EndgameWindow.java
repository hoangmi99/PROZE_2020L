package klient.com.company;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

/**
 * klasa odpowiadajaca za okno konca gry (wygranej, lub przegranej)
 */
public class EndgameWindow extends JFrame {
    /**
     * konstruktor okna
     * @param success okresla czy gra zostala zakonczona pomyslnie czy kleska
     */
    public EndgameWindow(boolean success, int score) {
        setTitle("LUNAR LANDER");
        setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel();
        if (success &&!Client.isOnline) {
            label.setText("<html>GRATULACJE!!! WYGRALES!!!<br/><font color='white'>gracz "+Game.getNick()+" uzyskal wynik: "+score+"<html>");
            try {
                Ranking.saveScore(Game.getNick(),score);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(success && Client.isOnline){
            label.setText("<html>GRATULACJE!!! WYGRALES!!!<br/><font color='white'>gracz "+Game.getNick()+" uzyskal wynik: "+score+"<html>");
            new Client("ADD_SCORE-"+Game.getNick()+"-"+score);
        }else {
            label.setText("NIESTETY PRZEGRALES :(");
        }
        add(label, BorderLayout.CENTER);
        JButton okButton = new JButton("OK");
        add(okButton,BorderLayout.SOUTH);
        this.setSize(Config.endgameWidth, Config.endgameHeight);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        okButton.setBackground(Color.darkGray);
        okButton.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setForeground(new Color(238, 197, 16));
        label.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 45));

        /**
         * listener obslugujący przycisk OK, powoduje zamkniecie okna
         */
        okButton.addActionListener(actionEvent -> {
            dispose();

        });

    }

}
