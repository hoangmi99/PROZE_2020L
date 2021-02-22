package klient.com.company;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
/**
 * Klasa odpowiadajaca z graficzny interfejs uzytkownika wyswietlajacy liste najlepszych wynikow. Rozszerza klase Panel
 */
public class RankingWindow extends JFrame {
    private JButton rankingOfflineButton = new JButton("RANKING OFFLINE");
    private JButton rankingOnlineButton = new JButton("RANKING ONLINE");
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel[] scoreLabels = new JLabel[5];

    public RankingWindow(MenuButtons menuButtons) throws IOException {
        setTitle("LUNAR LANDER");
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        panel1.setLayout(new GridLayout(1, 2));
        panel1.add(rankingOfflineButton);
        panel1.add(rankingOnlineButton);
        panel2.setLayout(new GridLayout(6, 1));
        JLabel label = new JLabel("WYNIKI");
        panel2.add(label);
        for (int i = 0; i < 5; i++) {
            scoreLabels[i] = new JLabel("");
            scoreLabels[i].setOpaque(true);
            scoreLabels[i].setHorizontalAlignment(JLabel.CENTER);
            scoreLabels[i].setBackground(Color.darkGray);
            scoreLabels[i].setForeground(Color.WHITE);
            scoreLabels[i].setFont(new Font("Impact", 0, 20));
            Border border = BorderFactory.createLineBorder(Color.black);
            scoreLabels[i].setBorder(border);
            panel2.add(scoreLabels[i]);
        }
        rankingOfflineButton.setBackground(Color.BLACK);
        rankingOfflineButton.setForeground(new Color(238, 197, 16));
        rankingOfflineButton.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 20));
        rankingOnlineButton.setBackground(Color.BLACK);
        rankingOnlineButton.setForeground(new Color(238, 197, 16));
        rankingOnlineButton.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 20));
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setForeground(new Color(238, 197, 16));
        label.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 45));
        setSize(400, 450);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        setVisible(true);
        rankingOnlineButton.addActionListener(actionEvent -> {
            Client.isOnline=true;
            Client.clientGetRanking("GET_RANKING");
            for (int i = 0; i < 5; i++) {
                try {
                    scoreLabels[i].setText((i + 1) + ".      " + Ranking.getScore(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        rankingOfflineButton.addActionListener(actionEvent -> {
            Client.isOnline=false;
            for (int i = 0; i < 5; i++) {
                try {
                    scoreLabels[i].setText((i + 1) + ".      " + Ranking.getScore(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * wykrywa zamknięcie okna gry i odblokowywuje przyciski menu glownego
         */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                menuButtons.enabledButtonsTrue();
                Client.isOnline=false;
            }
        });
    }
}
