package klient.com.company;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * klasa odpowiedzialna za obrazy w tle menu glownego
 */
class BackgroundPanel extends JPanel {

    /**
     * odczyt obrazu
     */
    InputStream resource = getClass().getResourceAsStream("/img/menuBackground.png");
    Image img = ImageIO.read(resource);

    /**
     * konstruktor bezparametrowy
     */
    public BackgroundPanel() throws IOException {
    }

    /**
     * metoda rysuje obraz w tle, wypelniajac caly obszar okna
     */
    public void paintComponent(Graphics var1) {
        var1.drawImage(this.img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}


