package serwer.com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;

/**
 * klasa odpowiedzialna za liste najlepszych wynikow
 */
public class Ranking {
    /**
     * lista najlepszych wynikow
     */
    static ArrayList<String> ranking, ranking2;

    /**
     * metoda odpowiedzialna za wczytanie listy najlepszych wynikow z pliku ranking.txt
     * i przypisanie jej do listy ranking
     * @throws IOException
     */
    public static void loadLista() throws IOException {
        FileInputStream var0 = new FileInputStream("src/serwer/ranking.txt");
        Properties var1 = new Properties();
        var1.load(var0);
        ranking = new ArrayList();

        for (int var2 = 0; var2 < 5; ++var2) {
            ArrayList var10000 = ranking;
            String var10001 = var1.getProperty("nick" + (var2 + 1));
            var10000.add(var10001 + "-" + var1.getProperty("score" + (var2 + 1)));
        }

        var0.close();
        ranking.sort(new Ranking.MyComparator());
        saveInFile();
    }

    /**
     * metoda odpowiedzialna za zwrocenie obiektu klasy String, ktora bedzie odpowiedzia
     * na zadanie klienta o liste najlepszych wynikow
     */
    static String loadRanking() throws IOException {
        loadLista();
        FileInputStream var0 = new FileInputStream("src/serwer/ranking.txt");
        Properties var1 = new Properties();
        var1.load(var0);
        String var2 = "";
        for (int var3 = 0; var3 < 5; ++var3) {
            if (var3 < 4) {
                var2 = var2 + (String) ranking.get(var3) + ", ";
            }

            if (var3 == 4) {
                var2 = var2 + (String) ranking.get(var3);
            }
        }
        return var2;
    }

    /**
     * metoda odpowiedzialna za dodanie do listy najlepszych wynikow nowego wyniku
     * i sprawdzenie czy wynik miesci sie w liscie 5 najlepszych wynikow
     * @param nick nick
     * @param score wynik
     */
    static String addScore(String nick, int score) throws IOException {
        String var2;
        loadLista();
        ranking2=new ArrayList<>(ranking);
        ranking.add(nick + "-" + score);
       ranking.sort(new MyComparator());
        if (ranking.size() > 5) {
            ranking.remove(ranking.size() - 1);
        }
        if (!ranking.equals(ranking2)){
            var2="SCORE_ACCEPTED";
            saveInFile();
        }else{
            var2="SCORE_REJECTED";
        }
        return var2;
    }

    /**
     * metoda odpowiedzialna za zapis do pliku ranking.txt
     * @throws IOException
     */
    static void saveInFile() throws IOException {
        FileInputStream var0 = new FileInputStream("src/serwer/ranking.txt");
        Properties var1 = new Properties();
        var1.load(var0);

        for (int var2 = 0; var2 < 5; ++var2) {
            var1.setProperty("nick" + (var2 + 1), ((String) ranking.get(var2)).split("-")[0]);
            var1.setProperty("score" + (var2 + 1), ((String) ranking.get(var2)).split("-")[1]);
        }

        var1.store(new FileOutputStream("src/serwer/ranking.txt"), (String) null);
        var0.close();
    }

    /**
     * sortowanie listy (implementacja komparatora)
     */
    static class MyComparator implements Comparator<String> {
        MyComparator() {
        }

        public int compare(String var1, String var2) {
            Integer var3 = Integer.parseInt(var1.split("-")[1]);
            Integer var4 = Integer.parseInt(var2.split("-")[1]);
            return -var3.compareTo(var4);
        }
    }

}
