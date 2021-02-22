package klient.com.company;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * klasa odpowiedzialna za animacje elementow dodatkowych gry
 */
public class Animation {
    /**czas po ktorym pojawi sie kolejna klatka animacji podana w ms*/
    private double speed;
    /**
     * numer klatki
     */
    private int index;
    /**
     * czas System.currentTimeMillis();
     */
    private long lastTime;
    /**
     * timer
     */
    private long timer;
    /**
     * przechowuje grafike do animacji
     */
    private BufferedImage[] frames;

    /**
     * konstruktor Animacji
     *
     * @param speed  czas po ktorym pojawi sie kolejna klatka animacji podana w ms
     * @param frames przechowuje grafike do animacji
     */
    public Animation(double speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
    }

    /**
     * timer przelicza ile czasu minelo od czasu wykonania metody update() po raz pierwszy, jezeli czas animacji przekroczy
     * czas timera, ladowany jest nastepna klatka (index) animacji, a timer ponownie ustawiany na 0
     */
    public void update() {
        if(!Game.isPaused()){
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (timer > speed) {
                index++;
                timer = 0;
            }
            if (index >= frames.length) {
                index = 0;
            }
        }

    }

    /**
     * zwraca aktualna grafike animacji
     */
    public Image getCurrentFrame() {
        update();
        return frames[index];
    }
}
