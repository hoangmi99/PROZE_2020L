package klient.com.company;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * klasa tworzaca okno na przyciski do menu glownego
 */
public class MenuButtons extends JPanel {
    /**
     * przycisk grania
     */
    private JButton GRAJButton = new JButton("GRAJ");
    /**
     * przycisk wynikow
     */
    private JButton WYNIKIButton = new JButton("WYNIKI");
    /**
     * przycisk instrukcji
     */
    private JButton INSTRUKCJAButton = new JButton("INSTRUKCJA");
    /**
     * przycisk wyjscia
     */
    private JButton WYJDZButton = new JButton("WYJDZ");
    /**
     * tworzy menu na przyciski, nadaje przyciskom ich funkcje
     */
    public MenuButtons() {
        setOpaque(true);
        setLayout(new GridLayout(4, 1));
        add(GRAJButton);
        add(WYNIKIButton);
        add(INSTRUKCJAButton);
        add(WYJDZButton);
        JFrame instrukcja = new JFrame("INSTRUKCJA");
        GRAJButton.setBackground(Color.darkGray);
        WYNIKIButton.setBackground(Color.darkGray);
        INSTRUKCJAButton.setBackground(Color.darkGray);
        WYJDZButton.setBackground(Color.darkGray);
        GRAJButton.setForeground(Color.WHITE);
        WYNIKIButton.setForeground(Color.WHITE);
        INSTRUKCJAButton.setForeground(Color.WHITE);
        WYJDZButton.setForeground(Color.WHITE);
        setVisible(true);

        /**
         * Listener obsługujący przycisk wyjscia
         */
        WYJDZButton.addActionListener(actionEvent -> System.exit(0));
        /**
         * Listener obsługujący przycisk instrukcji, tworzy nowe okno ktore wczytuje plik instrukcja.txt
         */
        INSTRUKCJAButton.addActionListener(actionEvent -> {
            try {

                instrukcja.setSize(new Dimension(600, 400));
                instrukcja.setResizable(true);
                instrukcja.getContentPane().setLayout(null);
                JTextArea textField = new JTextArea();
                textField.setEditable(false);
                InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("txt/instrukcja.txt");
                Reader text = new InputStreamReader(inputStream);
                textField.read(text,null);

                JScrollPane scroll = new JScrollPane(textField);
                scroll.setBounds(10, 10, 565, 340);
                textField.setBackground(Color.black);
                textField.setForeground(Color.WHITE);

                instrukcja.getContentPane().add(scroll);
                instrukcja.setLocation(100, 100);
                instrukcja.setVisible(true);
                enabledButtonsFalse();
            } catch (Exception e) {
                e.printStackTrace();
                String message = "Err! Nie mozna odnalezc instrukcji!";
                String title = "Err!";
                UIManager.put("OptionPane.background", Color.BLACK);
                UIManager.put("OptionPane.messagebackground", Color.BLACK);
                UIManager.put("Panel.background", Color.BLACK);
                UIManager.put("OptionPane.messageForeground", Color.WHITE);
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        /**
         * Listener obsługujący przycisk WYNIKIbutton, tworzy okno z lista najlepszych wynikow,
         * ustawia przyciski menu glownego na nieaktywne
         */
        WYNIKIButton.addActionListener(actionEvent -> {
            try {
              new RankingWindow(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
          enabledButtonsFalse();
        });
        /**
         * Listener obsługujący przycisk grania, tworzy okno na podanie nicku gracza,
         * ustawia przyciski menu glownego na nieaktywne
         */
        GRAJButton.addActionListener(actionEvent -> {
            new Nick(this);
            enabledButtonsFalse();
        });
        /**
         * wykrywa zamkniecie okna instrukcji i odblokowywuje przyciski menu glownego
         */
        instrukcja.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                enabledButtonsTrue();
            }
        });
    }

    /**
     * odblokowywuje przyciski menu glownego
     */
    public void enabledButtonsTrue(){
        GRAJButton.setEnabled(true);
        WYNIKIButton.setEnabled(true);
        INSTRUKCJAButton.setEnabled(true);
        WYJDZButton.setEnabled(true);
    }

    /**
     * zablokowywuje przyciski menu glownego
     */
    public void enabledButtonsFalse(){
        GRAJButton.setEnabled(false);
        WYNIKIButton.setEnabled(false);
        INSTRUKCJAButton.setEnabled(false);
        WYJDZButton.setEnabled(false);
    };

}
