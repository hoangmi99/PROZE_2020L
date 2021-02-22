package klient.com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

/**
 * klasa dotyczaca listy najlepszych wynikow
 */
public class Ranking {
    /**
     * Lista przechowujaca nick oraz wyniki uzyskane przez gracza
     */
    static ArrayList<String> ranking;

    /**
     * wczytywanie wynikow z pliku, dodanie nowego wyniku, sortowanie listy,usuwanie nadmiaru wynikow oraz zapis do pliku
     *
     * @param score wynik gracza
     * @param nick  nazwa gracza
     */
    static void saveScore(String nick, int score) throws IOException {
        loadScores();
        ranking.add(nick + " - " + score);
        ranking.sort(new MyComparator());
        if (ranking.size() > 5) {
            ranking.remove(ranking.size() - 1);
        }
        saveToFile();
    }

    /**
     * Wczytywanie wynikow z pliku ranking.txt
     */
    public static void loadScores() throws IOException {
        if (Client.isOnline) {
            String var0 = Client.response;
            ranking=new ArrayList<>();
            ranking.addAll(Arrays.asList(var0.split(", ")).subList(0, 5));
        } else {
            InputStream file = new FileInputStream("src/klient/ranking.txt");
            Properties var0 = new Properties();
            var0.load(file);
            ranking = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (var0.containsKey("nick" + (i + 1)))
                    ranking.add(var0.getProperty("nick" + (i + 1)) + " - " + var0.getProperty("score" + (i + 1)));
            }
            file.close();
            ranking.sort(new MyComparator());
        }

    }

    /**
     * sortowanie listy (implementacja komparatora)
     */
    static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            Integer a = Integer.parseInt(s1.split(" - ")[1]);
            Integer b = Integer.parseInt(s2.split(" - ")[1]);
            return -a.compareTo(b);
        }
    }

    /**
     * Zapisywanie wynikow do pliku ranking.txt
     */
    public static void saveToFile() throws IOException {
        InputStream file = new FileInputStream("src/klient/ranking.txt");
        Properties var0 = new Properties();
        var0.load(file);
        for (int i = 0; i < ranking.size(); i++) {
            var0.setProperty("nick" + (i + 1), ranking.get(i).split(" - ")[0]);
            var0.setProperty("score" + (i + 1), ranking.get(i).split(" - ")[1]);
        }
        var0.store(new FileOutputStream("src/klient/ranking.txt"), null);
        file.close();
    }

    /**
     * @param index
     */
    static String getScore(int index) throws IOException {
            loadScores();
            return ranking.get(index).split("-")[0] + ": " + ranking.get(index).split("-")[1];
    }

}

