package klient.com.company;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/**
 * klasa odpowiedzialna za dzialanie klienta
 */
public class Client {
    /**
     * okresla czy gra jest online
     */
    static boolean isOnline=false;
    /**
     * zadanie klienta
     */
    static String response;

    /**
     *  Otwarcie gniazda, na ktore klient bedzie wysylal zadanie oraz
     *  strumien na ktorym odczytujemy informacje zwrotne z serwera
     * @param command zadanie klienta
     */
    public Client(String command) {
        try {
            isOnline=true;
            Socket socket = new Socket(Config.address, Config.port);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println(command);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            response=br.readLine();
            System.out.println("Server responde: "+response);
            socket.close();
            br.close();
        } catch (Exception e) {
            System.err.println("Nieudane polaczenie");
            isOnline=false;
        }
    }
    /**
     *  Otwarcie gniazda, na ktore klient bedzie wysylal zadanie o wyswietlenie listy najlepszych wynikow
     *  strumien na ktorym odczytujemy informacje zwrotne z serwera
     * @param command zadanie klienta
     */
    static void clientGetRanking(String command) {
        try {
            FileInputStream var0 = new FileInputStream("src/serwer/port.txt");
            Properties var1 = new Properties();
            var1.load(var0);
            int port;
            port=Integer.parseInt(var1.getProperty("port"));
            var0.close();
            isOnline=true;
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println(command);
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            response=br.readLine();
            System.out.println("Server responde: "+response);
            socket.close();
            br.close();
        } catch (Exception e) {
            System.err.println("Nieudane polaczenie");
            isOnline=false;
        }
    }
}