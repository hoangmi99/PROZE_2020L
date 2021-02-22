package klient.com.company;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import static java.lang.Math.abs;


/**
 * klasa tworzaca okienko gry
 */
public class GameWindow extends JFrame {

    /**
     * parametr informuje o aktualnej szerokosci okna
     */
    private static int currentWidth;
    /**
     * parametr informuje o aktualnej wysokosci okna
     */
    private static int currentHeight;

    /**
     * Konstruktor dzieli okno na czesc grywalna i na panel pomocniczy,
     * ustawia rozmiary okna, dodaje przyciski oraz umozliwia ogladanie co sie dzieje w grze
     *
     * @param width  szerokosc okna
     * @param height wysokosc okna
     * @param title  tytul okna
     * @param game   gra
     * @param player gracz
     * @param menuButtons przyciski menu glownego
     */
    public GameWindow(int width, int height, String title, Game game, Player player, MenuButtons menuButtons) {
        JFrame frame = new JFrame(title);
        frame.setSize(new Dimension(width, height));
        JPanel panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(150, height));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(panel1, BorderLayout.WEST);
        frame.add(game, BorderLayout.CENTER);
        JButton wyjdz = new JButton("WYJDZ");
        JButton pauza = new JButton("PAUZA");
        JLabel dane = new JLabel();
        dane.setForeground(Color.WHITE);
        //Jprogressbar linie 65-99
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        JProgressBar fuelBar = new JProgressBar();
        fuelBar.setMaximum(Config.fuel);
        fuelBar.setMinimum(0);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            dane.setText("<html>x: " + (new DecimalFormat("##.###").format(player.getX())) +
                                    "<br/>y: " + (new DecimalFormat("##.###").format(player.getY())) +
                                    Vx(player) + Vy(player) +
                                    "<br/><br/><font color='white'> czas: " + game.getTimePassed() +
                                    "<br/> wynik: " +game.getScore()+
                                    "<br/><br/>Pozostale statki: " + (new DecimalFormat("##.###").format(player.getStatki())) + "<html>");

                            fuelBar.setStringPainted(true);
                            fuelBar.setString("paliwo: " + player.getFuel());
                            fuelBar.setValue(player.getFuel());
                            if (player.getFuel() > 0) {
                                fuelBar.setForeground(Color.green);
                                fuelBar.setBackground(Color.white);
                            } else
                                fuelBar.setBackground(Color.red);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        /**
         * Listener obsługujący przycisk wyjscia, zamyka okno gry, ustawia przyciski menu glownego na aktywne
         */
        wyjdz.addActionListener(e -> {
            frame.dispose();
            menuButtons.enabledButtonsTrue();
            Client.isOnline=false;
            System.out.println("Koniec gry");
        });
        /**
         * Listener obsługujący przycisk pauzy gry
         */
        pauza.addActionListener(e -> {
            if (game.isPaused()) {
                game.setPaused(false);
                pauza.setText("PAUZA");
            } else {
                game.setPaused(true);
                pauza.setText("WZNOW");
            }
        });
        /**
         * wykrywa zamknięcie okna gry i odblokowywuje przyciski menu glownego
         */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                menuButtons.enabledButtonsTrue();
                Client.isOnline=false;
                System.out.println("Koniec gry");
            }
        });
        panel1.setLayout(new GridLayout(0, 1));
        panel1.add(dane);
        panel1.add(fuelBar);
        panel1.add(pauza);
        panel1.add(wyjdz);
        panel1.setBackground(Color.black);
        Border whiteline = BorderFactory.createLineBorder(Color.white);
        panel1.setBorder(whiteline);
        frame.setVisible(true);
        /**
         * za pomocy funkcji pomocniczej componentResized, zwracana jest aktualna wielkosc okna
         * i przypisywana do odpowiednich pol
         */
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Component c = (Component) e.getSource();
                currentWidth = c.getWidth()-150;
                currentHeight = c.getHeight();
            }
        });
        game.start();

    }

    /**
     * getter umozliwia szybki i latwy dostep do paramtru
     * @return zwraca aktualna szerokosc okna
     */
    static int getCurrentWidth() {
        return currentWidth;
    }
    /**
     * getter umozliwia szybki i latwy dostep do paramtru
     * @return zwraca aktualna wysokosc okna
     */
    static int getCurrentHeight() {
        return currentHeight;
    }

    /**
     * ustawia informacje o predkosci poziomej gracza pojawiajacy sie w panelu bocznym gry
     * kolor zielony gry predkosc miejsci sie w dopuszczalnym zakresie predkosci
     * kolor czerowny kiedy nie miesci sie w dopuszczalnym zakresie predkosci
     * @param player gracz
     * @return zwraca String prekosci poziomej
     */
    public String Vx(Player player) {
        String VelX;
        if (abs(player.getVelX()) < Config.maxVelx) {
            VelX = "<br/><br/><font color='green'>Vx: " + (new DecimalFormat("##.###").format(player.getVelX()));
        } else {
            VelX = "<br/><br/><font color ='red'>Vx: " + (new DecimalFormat("##.###").format(player.getVelX()));
        }
        return VelX;
    }
    /**
     * ustawia informacje o predkosci pionowej gracza pojawiajacy sie w panelu bocznym gry
     * kolor zielony gry predkosc miejsci sie w dopuszczalnym zakresie predkosci
     * kolor czerowny kiedy nie miesci sie w dopuszczalnym zakresie predkosci
     * @param player gracz
     * @return zwraca String prekosci pionowej
     */
    public String Vy(Player player) {
        String VelY;
        if (player.getVelY() <= Config.maxVely) {
            VelY = "<br/><font color='green'>Vy: " + (new DecimalFormat("##.###").format(player.getVelY()));
        } else {
            VelY = "<br/><font color='red'>Vy: " + (new DecimalFormat("##.###").format(player.getVelY()));
        }
        return VelY;
    }
}

