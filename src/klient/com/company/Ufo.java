package klient.com.company;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Klasa tworzaca dodatkowy element gry, ktora jest przeszkoda
 */
public class Ufo  {
    /**
     * wspolrzedna x ufo
     */
    private int x;
    /**
     * wspolrzedna y ufo
     */
    private  int y;
    /**
     * kat
     */
    private  int kat;
    /**
     * czas od ostatniego wywolania metody update() w milisekundach
     */
    private long lastTime;
    /**
     * timer w milisekundach
     */
    private long timer;
    /**
     * obiekt klasy Animation
     */
    private Animation ufoAnimation;
    /**
     * przechowuje grafike ufo do animacji
     */
    private BufferedImage[] images = new BufferedImage[11];

    /**
     * konstruktor ufo
     */
    public Ufo() throws IOException {
        timer = 0;
        lastTime = System.currentTimeMillis();
        kat=0;
        for (int i=0;i<images.length;++i){
            InputStream resource = getClass().getResourceAsStream("/img/gif/ufo/ufo"+i+".png");
            BufferedImage img = ImageIO.read(resource);
            images[i]=img;
        }
        ufoAnimation=new Animation(100,images);
    }

    /**
     * aktualizuje pozycje ufo co 20ms, ktore porusza sie po okregu
     * Config.xUfo i Config.yUfo jest to pozycja srodka okregu,
     * 100 jest to promien po ktorym sie porusza, wartosc cos to kat
     */
    public void update(){
        if(!Game.isPaused()){
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            if (timer > 20) { //co 20 ms wywoluja sie funkcje tej metody
                x= (int) (Config.xUfo+100*Math.cos(0.01*kat++));
                y= (int) (Config.yUfo+100*Math.sin(0.01*kat++));
                timer = 0;
            }
        }
    }

    /**
     * rysowanie ufo oraz jej skalowanie
     */
    public Rectangle drawUfo(Graphics g){
        update();
        int newWidth = (int) ((double) ufoAnimation.getCurrentFrame().getWidth(null) * (double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150));
        int newHeight = (int) ((double) ufoAnimation.getCurrentFrame().getHeight(null) * (double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight);
        int newPositionx =(int) (x * ((double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150)));
        int newPositiony=(int) (y * ((double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight));
        g.drawImage(ufoAnimation.getCurrentFrame(),newPositionx,newPositiony, newWidth, newHeight, null);
        return new Rectangle(newPositionx,newPositiony,newWidth,newHeight);
    }

}