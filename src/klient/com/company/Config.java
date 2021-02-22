package klient.com.company;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * klasa odpowiedzialna za wczytywanie oraz przetwrzanie danych z plikow konfiguracyjnych
 */
public class Config {
    /**
     * Okresla poczatkowa szerokosc okna docelowej
     */
    static int gameWindowWidth;
    /**
     * Okresla poczatkowa wysokosc okna docelowej
     */
    static int gameWindowHeight;
    /**
     * Okresla poczatkowa szerokosc okna menu glownego
     */
    static int menuGlowneWidth;
    /**
     * Okresla poczatkowa wysokosc okna menu glownego
     */
    static int menuGlowneHeight;
    /**
     * Okresla poczatkowa szerokosc okna wprowadzajacego nick gracza
     */
    static int nickWidth;
    /**
     * Okresla poczatkowa wysokosc okna wprowadzajacego nick gracza
     */
    static int nickHeight;
    /**
     * Okresla ilosc poziomow
     */
    static int levels;
    /**
     * Okresla poczatkowa szerokosc okna konczacego rozgrywke
     */
    static int endgameWidth;
    /**
     * Okresla poczatkowa wysokosc okna konczacego rozgrywke
     */
    static int endgameHeight;
    /**
     * Okresla maksymalna predkosc pionowa z jaka mozna wyladowac
     */
    static double maxVelx;
    /**
     * Okresla maksymalna predkosc pozioma z jaka moze wyladowac
     */
    static double maxVely;
    /**
     * okresla ilosc statkow
     */
    static int statki;
    /**
     * okresla ilosc paliwa
     */
    static int fuel;
    /**
     * okresla wartosc przyspieszenia/grawitacji
     */
    static double acceleration;
    /**
     * updated per second
     */
    static int UPS;
    /**
     * frames per second
     */
    static int FPS;
    /**
     * punkty bonusowe za gwiazdki
     */
    static int bonusGwiazdka;
    /**
     * punkty bonusowe za kazdy pozostaly statek
     */
    static int bonusStatek;
    /**
     * czas przeznaczony na przejscie poziomu
     */
    static int timePerLevel;
    /**
     * tablica przechowujaca wspolrzedne x-owe wierzcholkow obszaru ksiezyca
     */
    static int[] xTeren;
    /**
     * tablica przechowujaca wspolrzedne y-owe wierzcholkow obszaru ksiezyca
     */
    static int[] yTeren;
    /**
     * tablica przechowujaca wspolrzedne x-owe ladowiska dla statku
     */
    static int[] xLad;
    /**
     * tablica przechowujaca wspolrzedne y-owe ladowiska dla statku
     */
    static int[] yLad;
    /**
     * okresla poczatkowa wspolrzedna x-owa statku
     */
    static int xStatek;
    /**
     * okresla poczatkowa wspolrzedna x-owa gwiazdy
     */
    static int xGwiazda;
    /**
     * okresla poczatkowa wspolrzedna y-owa gwiazdy
     */
    static int yGwiazda;
    /**
     * okresla poczatkowa wspolrzedna x-owa ufo
     */
    static int xUfo;
    /**
     * okresla poczatkowa wspolrzedna y-owa ufo
     */
    static int yUfo;
    /**
     * adres ip
     */
    static String address;
    /**
     * numer portu
     */
    static int port;

    /**
     * przypisanie wartosci dla parametru adresu ip oraz numeru portu
     * @param ipAddress adres ip
     * @param portNumber numer portu
     */
    static void savePortAndIP(String ipAddress, int portNumber) {
        address = ipAddress;
        port = portNumber;
    }

    /**
     * wczytywanie danych otrzymanych od serwera (gra online),
     * lub wczytywanie danych z pliku konfiguracyjnego config.properties
     * @throws IOException
     */
    static void loadConfig() throws IOException {
        if (Client.isOnline) {
            String var0 = Client.response;
            int[] var1 = Arrays.stream(var0.split("-")).mapToInt(Integer::parseInt).toArray();
            gameWindowWidth = var1[0];
            gameWindowHeight =var1[1];
            endgameWidth =var1[2];
            endgameHeight =var1[3];
            levels = var1[4];
            statki=var1[5];
            fuel = var1[6];
            acceleration =var1[7];
            UPS = var1[8];
            FPS =var1[9];
            maxVelx = var1[10];
            maxVely = var1[11];
            bonusGwiazdka = var1[12];
            bonusStatek = var1[13];
            timePerLevel=var1[14];
        }
        else{
            try {
                InputStream var0 = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
                Properties var1 = new Properties();
                var1.load(var0);
                gameWindowWidth = Integer.parseInt(var1.getProperty("gameWindowWidth"));
                gameWindowHeight = Integer.parseInt(var1.getProperty("gameWindowHeight"));
                endgameWidth = Integer.parseInt(var1.getProperty("endgameWidth"));
                endgameHeight = Integer.parseInt(var1.getProperty("endgameHeigh"));
                levels = Integer.parseInt(var1.getProperty("levels"));
                fuel = Integer.parseInt(var1.getProperty("fuel"));
                UPS = Integer.parseInt(var1.getProperty("UPS"));
                FPS = Integer.parseInt(var1.getProperty("FPS"));
                acceleration = Double.parseDouble(var1.getProperty("acceleration"));
                statki = Integer.parseInt(var1.getProperty("statki"));
                maxVelx = Integer.parseInt(var1.getProperty("maxVelx"));
                maxVely = Integer.parseInt(var1.getProperty("maxVely"));
                bonusGwiazdka = Integer.parseInt(var1.getProperty("bonusGwiazdka"));
                bonusStatek = Integer.parseInt(var1.getProperty("bonusStatek"));
                timePerLevel=Integer.parseInt(var1.getProperty("timePerLevel"));
                var0.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     *Wczytuje dane o rozmiarach okienek z pliku konfiguracyjnego config.properties, przydziela odpowiednim polom w klasie.
     * W razie niepoprawnego odczytania z pliku konfiguracyjnego spowodowanego bledem  np. liteorwka, przydzielane
     * zostana parametrom domyslne wartosci.
     * */
    static {
        try {
            InputStream var0 = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
            Properties var1 = new Properties();
            var1.load(var0);
            menuGlowneWidth = Integer.parseInt(var1.getProperty("menuGlowneWidth"));
            menuGlowneHeight = Integer.parseInt(var1.getProperty("menuGlowneHeight"));
            nickWidth = Integer.parseInt(var1.getProperty("nickWidth"));
            nickHeight = Integer.parseInt(var1.getProperty("nickHeight"));
            var0.close();
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    /**
     * wczytywanie danych o poziomie gry
     * metoda wczytuje dane otrzymane w odpowiedzi od serwera i przypisuje do odpowiednich parametrow i tablic (gra online),
     * metoda wczytuje dane z pliku konfiguracyjnego poziom.txt
     */
    static void loadLevelConfigs(int index) throws IOException {
        if(Client.isOnline){
            String var1 = Client.response;
            String[] var2 = var1.split("-");
            xTeren = Arrays.stream(var2[0].split(" ")).mapToInt(Integer::parseInt).toArray();
            yTeren = Arrays.stream(var2[1].split(" ")).mapToInt(Integer::parseInt).toArray();
            xLad = Arrays.stream(var2[2].split(" ")).mapToInt(Integer::parseInt).toArray();
            yLad = Arrays.stream(var2[3].split(" ")).mapToInt(Integer::parseInt).toArray();
            xStatek = Integer.parseInt(var2[4]);
            xGwiazda=Integer.parseInt(var2[5]);
            yGwiazda=Integer.parseInt(var2[6]);
            xUfo=Integer.parseInt(var2[7]);
            yUfo=Integer.parseInt(var2[8]);
            transformPoints();
        }else{
            InputStream var1 = ClassLoader.getSystemClassLoader().getResourceAsStream("txt/poziom.txt");
            Properties var2 = new Properties();
            var2.load(var1);
            xTeren = Arrays.stream(var2.getProperty("xTeren" + index).split("-")).mapToInt(Integer::parseInt).toArray();
            yTeren = Arrays.stream(var2.getProperty("yTeren" + index).split("-")).mapToInt(Integer::parseInt).toArray();
            xLad = Arrays.stream(var2.getProperty("xLad" + index).split("-")).mapToInt(Integer::parseInt).toArray();
            yLad = Arrays.stream(var2.getProperty("yLad" + index).split("-")).mapToInt(Integer::parseInt).toArray();
            xStatek = Integer.parseInt(var2.getProperty("xStatek" + index));
            xGwiazda = Integer.parseInt(var2.getProperty("xGwiazda" + index));
            yGwiazda = Integer.parseInt(var2.getProperty("yGwiazda" + index));
            xUfo = Integer.parseInt(var2.getProperty("xUfo" + index));
            yUfo = Integer.parseInt(var2.getProperty("yUfo" + index));
            transformPoints();
        }

    }

    /**
     * Metoda przelicza wczytane wartosci z parametrow konfiguracyjncyh,
     * ktore dopasowywuje do rozmiarow okna do gry
     */
    private static void transformPoints() {
        xTeren = Arrays.stream(xTeren).map((var0) -> (int) ((double) (gameWindowWidth) * 0.01D * (double) var0)).toArray();
        yTeren = Arrays.stream(yTeren).map((var0) -> (int) ((double) (gameWindowHeight) * 0.01D * (double) var0)).toArray();
        xLad = Arrays.stream(xLad).map((var0) -> (int) ((double) (gameWindowWidth) * 0.01D * (double) var0)).toArray();
        yLad = Arrays.stream(yLad).map((var0) -> (int) ((double) (gameWindowHeight) * 0.01D * (double) var0)).toArray();
        xStatek = (int) ((double) (gameWindowWidth - 150) * 0.01D * (double) xStatek);
        xGwiazda = (int) ((double) (gameWindowWidth - 150) * 0.01D * (double) xGwiazda);
        yGwiazda = (int) ((double) (gameWindowHeight) * 0.01D * (double) yGwiazda);
        xUfo = (int) ((double) (gameWindowWidth - 150) * 0.01D * (double) xUfo);
        yUfo = (int) ((double) (gameWindowHeight) * 0.01D * (double) yUfo);
    }
}
