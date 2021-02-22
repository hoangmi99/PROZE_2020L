package serwer.com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * klasa odpowiedzialna za wczytanie parametrow gry z serwera
 */
public class Config {
    /**
     * wczytuje numer portu z pliku konfiguracyjnego port.txt
     */
    static int loadPort() throws IOException {
        FileInputStream var0 = new FileInputStream("src/serwer/port.txt");
        Properties var1 = new Properties();
        var1.load(var0);
        int port;
        port=Integer.parseInt(var1.getProperty("port"));
        var0.close();
        return port;
    }
    /**
     * metoda ktora zwraca obiekt klasy String z parametrami konfiguracyjnymi gry
     */
    static String loadConfig() throws IOException {
        InputStream var0 = ClassLoader.getSystemClassLoader().getResourceAsStream("configServer.properties");
        Properties var1 = new Properties();
        var1.load(var0);
        String var2 = "";
        for(int var3 = 0; var3 < 15; ++var3) {
            if (var3 != 14) {
                var2 = var2 + var1.getProperty("param" + (var3 + 1)) + "-";
            } else {
                var2 = var2 + var1.getProperty("param" + (var3 + 1));
            }
        }
        var0.close();
        return var2;
    }

    /**
     * klasa odpowiedzialna za pobranie paramtrow dotyczacych danego poziomu gry
     * @param levelIndex numer poziomu gry, z ktorego chcemy pobrac parametry
     * @return
     * @throws IOException
     */
    static String loadLevelConfigs(int levelIndex) throws IOException {
        InputStream propertiesFile = ClassLoader.getSystemClassLoader().getResourceAsStream("txt/poziomServer.txt");
        Properties mapProperties = new Properties();
        mapProperties.load(propertiesFile);
        int[] xTeren = Arrays.stream(mapProperties.getProperty("xTeren" + levelIndex).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] yTeren = Arrays.stream(mapProperties.getProperty("yTeren" + levelIndex).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] xLad = Arrays.stream(mapProperties.getProperty("xLad" + levelIndex).split("-")).mapToInt(Integer::parseInt).toArray();
        int[] yLad = Arrays.stream(mapProperties.getProperty("yLad" + levelIndex).split("-")).mapToInt(Integer::parseInt).toArray();
        int xStatek = Integer.parseInt(mapProperties.getProperty("xStatek" + levelIndex));
        int xGwiazda = Integer.parseInt(mapProperties.getProperty("xGwiazda" + levelIndex));
        int yGwiazda = Integer.parseInt(mapProperties.getProperty("yGwiazda" + levelIndex));
        int xUfo = Integer.parseInt(mapProperties.getProperty("xUfo" + levelIndex));
        int yUfo = Integer.parseInt(mapProperties.getProperty("yUfo" + levelIndex));
        return (Arrays.toString(xTeren) + "-" + Arrays.toString(yTeren) + "-" + Arrays.toString(xLad) + "-" + Arrays.toString(yLad) + "-" + xStatek+"-"+xGwiazda+"-"+yGwiazda+"-"+xUfo+"-"+yUfo)
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
    }
}
