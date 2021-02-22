package klient.com.company;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * klasa dziedziczy po klasie obstrakcyjnej GameObject. Klasa opisujaca glowny obiekt z gry, czyli rakiete sterowana przez gracza
 */
public class Player extends GameObject {
    /**
     * wczytanie grafiki statka
     */
    private InputStream img1 = getClass().getResourceAsStream("/img/statek1.png");
    /**
     * grafika statku
     */
    private Image statek = ImageIO.read(img1);
    /**
     * konstruktor ze wspolrzednymi
     *
     * @param x wspolrzedne
     * @param y wspolrzedne
     */
    public Player(double x, double y) throws IOException {
        super(x, y);
    }

    /**
     * aktualizuje stan(wspolrzedne) obiektu
     * przemieszcza rakiete po planszy
     */
    public void update() {
        double dt = 1 / (double) Config.UPS;
        applyGravity(dt, Config.acceleration);
        y += velY * dt + 1 / 2 * Config.acceleration * dt * dt;
        x += velX*dt;
    }

    /**
     * grawitacja
     * @param dt czas
     * @param acceleration przyspieszenie
     */
    private void applyGravity(double dt, double acceleration) {
        velY += (acceleration * dt);
    }

    /**
     * getter, dostep do obiektu klasy Image
     * @return zwraca grafike statku
     */
    public Image getStatek() {
        return statek;
    }
}
