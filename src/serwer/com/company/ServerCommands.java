package serwer.com.company;

import java.io.IOException;

/**
 * Klasa odpwiedzialna za wybor odpowiedzi serwera na zadanie klienta
 */
public class ServerCommands {
    /**
     * Na podstawie zadania klienta wybierana jest odpowiednia odpowiedz
     *
     * @param command tresc zadania klienta
     * @return odpowiedz serwera
     */
    public static String serverAction(String command) throws IOException {
        /**
         * odpowiedz serwera
         */
        String serverMessage;
        /**
         * tablicca przechowujaca zadanie klienta i parametry zadania, gdy napotka znak "-"
         */
        String[] commands = command.split("-");
        switch (commands[0]) {
            case "LOGIN":
                serverMessage = "LOGGED_IN";
                break;
            case "GET_CONFIG":
                serverMessage = Config.loadConfig();
                break;
            case "GET_POZIOM":
               serverMessage=Config.loadLevelConfigs(Integer.parseInt(commands[1]));
                break;
            case "GET_RANKING":
                serverMessage=Ranking.loadRanking();
                break;
            case "ADD_SCORE":
                serverMessage=Ranking.addScore(commands[1],Integer.parseInt(commands[2]));
                break;
            default:
                serverMessage = "Invalid command";
        }
        return serverMessage;
    }
}
