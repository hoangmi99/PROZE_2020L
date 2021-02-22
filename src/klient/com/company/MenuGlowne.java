package klient.com.company;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

/**
 * klasa tworzaca okno menu glownego oraz odpowiedzialna za jego wyglad
 */
public class MenuGlowne extends JFrame {
    /**
     * konstruktor wywoluje konstruktor klasy MenuButtons, tworzy okno menu,wkleja obraz, ustawia kolor oraz wielkosc z plikow
     */
    public MenuGlowne() throws IOException {
        MenuButtons menuButtons = new MenuButtons();
        BackgroundPanel panel = new BackgroundPanel();
        this.setTitle("LUNAR LANDER");
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.pack();
        this.setSize(Config.menuGlowneWidth, Config.menuGlowneHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(menuButtons, BorderLayout.PAGE_END);
        menuButtons.setBackground(Color.black);
        this.setBackground(Color.black);
        this.setVisible(true);
    }
}
