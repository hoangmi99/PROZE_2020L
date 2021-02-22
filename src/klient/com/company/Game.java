package klient.com.company;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

/**
 * klasa, ktora zarzadza dzialaniem gry
 */
public class Game extends JPanel implements Runnable {

    /**
     * przechowuje nick wprowadzony przez gracza
     */
    private static String nick;
    /**
     * wynik gracza
     */
    private int score;
    /**
     * aktualny poziom gry
     */
    private int currentLevel = 1;
    /**
     * watek
     */
    private Thread thread;
    /**
     * timer do pomiaru czasu gry
     */
    private Timer timer;
    /**
     * okresla kiedy timer ma liczyc czas
     */
    private TimerTask task;
    /**
     * okresla czas gry
     */
    private int timePassed;
    /**
     * okresla czy watek trwa
     */
    private boolean running = false;
    /**
     * okresla czy statek rozbil sie
     */
    private boolean isCrashed = false;
    /**
     * okresla czy statek wyladowal pomyslnie na ladowisku
     */
    private boolean isSucceeded = false;
    /**
     * okresla ilosc zebranych gwiazdek na danym poziomie
     */
    private int isGettingBonus=0;
    /**
     * okresla czy gra zostala zapuzowana
     */
    private static boolean isPaused = false;
    /**
     * handler
     */
    private Handler handler;
    /**
     * inizjalizuje gracza
     */
    private Player player;
    /**
     * wczytanie grafiki ksiezyca
     */
    private InputStream img2 = getClass().getResourceAsStream("/img/moon.jpg");
    /**
     * grafika ksiezyca
     */
    private Image moon = ImageIO.read(img2);
    /**
     * obiekt bonusowej gwiazdy
     */
    private Star star = new Star();
    /**
     * obiekt ufo
     */
    private Ufo ufo = new Ufo();

    /**
     * konstruktor gry
     *
     * @param menuButtons dostep do obiektu klasy Menubuttons
     * @throws IOException
     */
    public Game(MenuButtons menuButtons) throws IOException {
        score = 0;
        isPaused = false;
        isGettingBonus = 0;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if ((!isSucceeded || isCrashed) && !isPaused) {
                    ++timePassed;
                }

            }
        };
        if (Client.isOnline) {
            new Client("GET_POZIOM-" + currentLevel);
        }
        Config.loadLevelConfigs(currentLevel);
        player = new Player(Config.xStatek, 0);
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        new GameWindow(Config.gameWindowWidth, Config.gameWindowHeight, "LUNAR LANDER", this, player, menuButtons);
        handler.addObject(player);
    }

    /**
     * odpowiada za uruchomienie timera oraz watku, ktory powoduje  rozpoczecie wykonywania
     * kodu/petli w metodzie run()
     */
    public synchronized void start() {
        timer.schedule(task, 1000, 1000);
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    /**
     * odpowiada za zatrzymanie watku
     */
    public synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * game loop - aktualizacja stanu i rysowanie
     */
    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / (double) Config.UPS;
        final double timeF = 1000000000 / (double) Config.FPS;
        double deltaU = 0, deltaF = 0;
        //int frames = 0, updates = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            if (deltaU >= 1) {
                update();
                //updates++;
                deltaU--;
            }
            if (deltaF >= 1) {
                repaint();
                //frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                //System.out.println(String.format("UPS: %s, FPS: %s", updates, frames));
                //frames = 0;
                //updates = 0;
                timer += 1000;
            }
        }
        stop();
    }

    /**
     * sprawdza stan gry (pauza, zderzenie, pomyslne przejscie poziomu),
     * blokowanie statku aby nie wychodzilo poza ekran gry
     */
    private void update() {
        this.requestFocus();
        if (!isPaused) {
            handler.update();
        }
        if (isSucceeded || isCrashed) {
            new EndgameWindow(isSucceeded, score);
            timer.cancel();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        blokowanie();
    }

    /**
     * metoda odpowiada za rysowanie obiektow w grze
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Rectangle statek;
        Rectangle gwiazdka = null;
        Rectangle ufo1 = null;
        statek = drawStatek(g);
        Polygon moon;
        moon = drawMoon(g);
        ufo1 = ufo.drawUfo(g);
        Polygon ladowisko = drawLadowisko(g);
        if (isGettingBonus==0) {
            gwiazdka = star.drawStar(g);
        } else {
            gwiazdka = new Rectangle();
        }
        try {
            detekcja(moon, statek, ladowisko, gwiazdka, ufo1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.dispose();
    }

    /**
     * rysowanie i sklaowanie statku
     *
     * @return zwraca obiekt klasy Rectangle
     */
    public Rectangle drawStatek(Graphics g) {
        int newWidth = (int) ((double) player.getStatek().getWidth(null) * (double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150));
        int newHeight = (int) ((double) player.getStatek().getHeight(null) * (double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight);
        int newPositionx = (int) (player.getX() * ((double) (GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150)));
        int newPositiony = (int) (player.getY() * ((double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight));
        g.drawImage(player.getStatek(), newPositionx, newPositiony, newWidth, newHeight, null);
        return new Rectangle(newPositionx, newPositiony, newWidth, newHeight);
    }

    /**
     * rysowanie i sklaowanie statku
     *
     * @return zwraca obiekt klasy Polygon
     */
    public Polygon drawMoon(Graphics g) {
        Polygon p = new Polygon(Config.xTeren, Config.yTeren, Config.xTeren.length);
        for (int i = 0; i < p.xpoints.length; i++) {
            p.xpoints[i] = (int) ((double) p.xpoints[i] * (((double) GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150)));
            p.ypoints[i] = (int) ((double) p.ypoints[i] * ((double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight));
        }
        g.setClip(p);
        g.drawImage(moon, 0, GameWindow.getCurrentHeight() / 2, null);
        g.setClip(null);
        return p;
    }

    /**
     * rysowanie i skalowanie ladowiska
     *
     * @return zwraca obiekt klasy Polygon
     */
    public Polygon drawLadowisko(Graphics g) {
        Polygon l = new Polygon(Config.xLad, Config.yLad, Config.xLad.length);
        g.setColor(Color.red);
        for (int i = 0; i < l.xpoints.length; i++) {
            l.xpoints[i] = (int) ((double) l.xpoints[i] * (((double) GameWindow.getCurrentWidth()) / (double) (Config.gameWindowWidth - 150)));
            l.ypoints[i] = (int) ((double) l.ypoints[i] * ((double) GameWindow.getCurrentHeight() / (double) Config.gameWindowHeight));
        }
        g.fillPolygon(l);
        return l;
    }

    /**
     * detekcja kolizji oraz ladowanie danych do kolejnego poziomu z config.properties, punktacja
     *
     * @param moon      ksiezyc
     * @param statek    statel
     * @param ladowisko ladowisko
     * @param gwiazdka  gwiazdka
     */
    public void detekcja(Polygon moon, Rectangle statek, Polygon ladowisko, Rectangle gwiazdka, Rectangle ufo) throws IOException {
        if (ladowisko.intersects(statek.getBounds()) && abs(player.velX) <= Config.maxVelx && player.velY <= Config.maxVely) {
            if (currentLevel < Config.levels) {
                score += (Config.timePerLevel - timePassed) * currentLevel + player.getStatki() * Config.bonusStatek + player.getFuel() + isGettingBonus*Config.bonusGwiazdka;
                timePassed = 0;
                isGettingBonus = 0;
                currentLevel++;
                if (Client.isOnline) {
                    new Client("GET_POZIOM-" + currentLevel);
                }
                Config.loadLevelConfigs(currentLevel);
                player.setFuel(Config.fuel);
                player.setX(Config.xStatek);
                player.setY(0.0);
                player.setVelY(0.0);
                player.setVelX(0.0);
            } else {
                isSucceeded = true;
                    score += (Config.timePerLevel - timePassed) * currentLevel + player.getStatki() * Config.bonusStatek + player.getFuel() + isGettingBonus*Config.bonusGwiazdka;
            }

        } else if (moon.intersects(statek.getBounds()) || (ufo.intersects(statek.getBounds())) || (ladowisko.intersects(statek.getBounds()) && (abs(player.velX) > Config.maxVelx || player.velY > Config.maxVely))) {
            if (player.statki > 1) {
                isGettingBonus = 0;
                --player.statki;
                player.setFuel(Config.fuel);
                player.setX(Config.xStatek);
                player.setY(0.0);
                player.setVelY(0.0);
                player.setVelX(0.0);
            } else {
                isCrashed = true;
            }
        } else if (gwiazdka.intersects(statek.getBounds())) {
            isGettingBonus = 1;
        }

    }

    /**
     * getter
     *
     * @return zwraca czas gry
     */
    public int getTimePassed() {
        return timePassed;
    }

    /**
     * getter
     *
     * @return zwraca informacje czy gra jest zapauzowana
     */
    public static boolean isPaused() {
        return isPaused;
    }

    /**
     * setter
     *
     * @param paused ustawia wartosc stanu gry, czy jest pauza
     */
    public static void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * blokuje statek, aby nie wychodzil poza ekran gry
     */
    public void blokowanie() {
        if (player.getY() < 0 && player.getY() < 0) {
            player.setVelY(0);
            player.setY(0);
        }
        if (player.getX() < 0 && player.getVelX() < 0) {
            player.setVelX(0);
            player.setX(0);
        }
        if (player.getX() > (690) && player.getVelX() > 0) {
            player.setVelX(0);
            player.setX(690);
        }

    }

    /**
     * getter, pozwala na dostep do parametru
     *
     * @return zwraca nick gracza
     */
    public static String getNick() {
        return nick;
    }

    /**
     * ustawia nick gracza
     *
     * @param nick nick
     */
    public static void setNick(String nick) {
        Game.nick = nick;
    }

    /**
     * getter, pozwala na dostep do parametru
     *
     * @return zwraca wynik gracza
     */
    public int getScore() {
        return score;
    }

}
