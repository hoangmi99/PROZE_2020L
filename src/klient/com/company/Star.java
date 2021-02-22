package klient.com.company;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * klasa tworzaca obiekt bonusowej gwiazdki
 */
public class Star  {
    /**
     * obiekt klasy Animation
     */
    private Animation starAnimation;
    /**
     * przechowuje grafike gwiazdki do animacji
     */
    private BufferedImage[] images = new BufferedImage[6];

    /**
     * konstruktor gwiazdki
     */
    public Star() throws IOException {
        for (int i=0;i<images.length;++i){
            InputStream resource = getClass().getResourceAsStream("/img/gif/star/star"+i+".png");
            BufferedImage img = ImageIO.read(resource);
            images[i]=img;
        }
        starAnimation=new Animation(200,images);
    }

    public void update(){
 //       starAnimation.update();
    }

    /**
     * rysowanie bonusowej gwiazdki oraz jej skalowanie
     */
    public Rectangle drawStar(Graphics g){
        int newWidth = (int) ((double) starAnimation.getCurrentFrame().getWidth(null) * (double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150));
        int newHeight = (int) ((double) starAnimation.getCurrentFrame().getHeight(null) * (double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight);
        int newPositionx =(int) (Config.xGwiazda * ((double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150)));
        int newPositiony=(int) (Config.yGwiazda * ((double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight));
        g.drawImage(starAnimation.getCurrentFrame(),newPositionx,newPositiony, newWidth, newHeight, null);
        return new Rectangle(newPositionx,newPositiony,newWidth,newHeight);
    }

}