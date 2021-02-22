package klient.com.company;

/**
 * klasa abstrakcyjna przechowujaca dane obiektow wystepujacych w grze
 * takich jak statek, gwiazdy, ufo
 */
public abstract class GameObject {
    /**
     * wspolrzedna x obiektu
     */
    protected double x;
    /**
     * wspolrzedna y obiektu
     */
    protected double y;
    /**
     * skladowe predkosci obiektu
     */
    protected double velX, velY;
    /**
     * paliwo
     */
    protected int fuel;
    /**
     * ilosc statkow
     */
    protected int statki;


    /**
     * konstruktor z wspolrzednymi
     *
     * @param x wspolrzedna x obiektu
     * @param y wspolrzedna y obiektu
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        fuel = Config.fuel;
        statki = Config.statki;
    }

    /**
     * aktualizuje stan obiektu
     */
    public abstract void update();

    /**
     * getter umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca wspolrzedna x
     */
    public double getX() {
        return x;
    }

    /**
     * setter, umozliwia ustawienie parametru
     *
     * @param x wspolrzedna x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * getter, umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca wspolrzedna y
     */
    public double getY() {
        return y;
    }

    /**
     * setter, umozliwia ustawienie parametru
     *
     * @param y wspolrzedna y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * getter umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca predkosc pozioma statku
     */
    public double getVelX() {
        return velX;
    }

    /**
     * setter umozliwia ustawienie parametru
     *
     * @param velX predkosc pozioma statku
     */
    public void setVelX(double velX) {
        this.velX = velX;
    }

    /**
     * getter umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca prekosc pionowa statku
     */
    public double getVelY() {
        return velY;
    }

    /**
     * setter umozliwia ustawienie parametru
     *
     * @param velY predkosc pionowa
     */
    public void setVelY(double velY) {
        this.velY = velY;
    }

    /**
     * getter umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca ilosc paliwa
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * setter umozliwia ustawienie parametru
     *
     * @param fuel paliwo
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * getter umozliwia prosty i szybki dostep do parametru
     *
     * @return zwraca ilosc statkow
     */
    public int getStatki() {
        return statki;
    }

}
