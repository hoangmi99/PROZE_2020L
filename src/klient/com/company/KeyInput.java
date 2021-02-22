package klient.com.company;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * klasa umozliwiajaca sterowanie obiektami z gry
 */
public class KeyInput extends KeyAdapter {
    /**
     * handler
     */
    private Handler handler;

    /**
     * wciskane przyciski przez gracza
     * @param handler handler
     */
    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    /**
     * przyporzadkowuje ruchy obiektu podczas nacisniecia klawiszy, poprzez manipulacje wspolrzednymi i predkosciami obiektow
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(!Game.isPaused()) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (key == KeyEvent.VK_UP && tempObject.fuel != 0) {
                    if (tempObject.velY > -60) {
                        tempObject.velY -= 2;
                    }
                    --tempObject.fuel;
                }
                if (key == KeyEvent.VK_RIGHT && tempObject.fuel != 0) {
                    tempObject.velX += 2.5;
                    --tempObject.fuel;
                }
                if (key == KeyEvent.VK_LEFT && tempObject.fuel != 0) {
                    tempObject.velX -= 2.5;
                    --tempObject.fuel;

                }
            }
        }
    }

    /**
     * przyporzadkowuje ruchy obiektu podczas zwolnienie klawiszy, poprzez manipulacje wspolrzednymi i predkosciami obiektow
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (!Game.isPaused()) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (key == KeyEvent.VK_UP) {
                    tempObject.velY += Config.acceleration * (1 / (double) Config.FPS);
                }
                if (key == KeyEvent.VK_RIGHT) {
                    tempObject.velX += Config.acceleration * (1 / (double) Config.FPS);
                }
                if (key == KeyEvent.VK_LEFT) {
                    tempObject.velX += Config.acceleration * (1 / (double) Config.FPS);
                }
            }
        }
    }
}

