package serwer.com.company;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * klasa odpowiadajaca za dzialanie serwera
 */
public class Server {
    /**
     * konstruktor
     */
    public Server() {}

    public static void main() throws Exception {
        /**
         * adres serwera
         */
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("Host address: " + localHost.getHostAddress());
        /**
         * Gniazdo, na ktorym bedzie nasluchiwał serwer
          */
        ServerSocket serverSocket;
        // Try/catch, ponieważ może nam się nie udać otwarcie portu,
        try {
            serverSocket = new ServerSocket(Config.loadPort());
            System.out.println("Port: " + serverSocket.getLocalPort());
        } catch (Exception e) {
            System.err.println("Nieudane otwarcie portu");
            return;
        }
        /**
         * Petla serwera
         */
        while (true) {
            try {
                // Rozpocznij nasłuch na gnieździe i gdy połączenie przyjdzie,zaakceptuj je.
                // Program zatrzymuje się na tej funkcji dopóki nie będzie połączenia.
                Socket socket = serverSocket.accept();
                // Strumień informacji przychodzących na gnieździe
                InputStream is = socket.getInputStream();
                // Reader strumienia
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                // Strumień informacji wysyłanych z gniazda
                OutputStream os = socket.getOutputStream();
                // Writer informacji na strumień
                PrintWriter pw = new PrintWriter(os, true);
                // String pobrany ze strumienia od klienta
                String clientRequest = br.readLine();
                System.out.println("Client request: " + clientRequest );
                // Wysłanie do klienta informacji zwrotnej
                pw.println(ServerCommands.serverAction(clientRequest));
                // Zamknięcie gniazda
                socket.close();
                // Zamknięcie strumieni,zwolnienie pamieci
                br.close();
                pw.close();
                is.close();
                os.close();
            } catch (Exception e) {
                System.err.println("Server exception: " + e);
            }
        }
    }
}