package NetworkLogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private BufferedReader reader;
    private Gson gson;

    public GameClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Verbunden mit Server: " + host + ":" + port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            gson = new GsonBuilder().create();
        } catch (IOException e) {
            System.err.println("Fehler beim Verbinden mit Server: " + e.getMessage());
        }
    }

    public GameModel receiveGameModel() {
        try {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            // Liest alle Zeilen vom BufferedReader
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
                System.out.println("Empfangene Zeile: " + line); // Debug-Ausgabe
                // End of JSON message identified by END_OF_MESSAGE
                if (line.contains("END_OF_MESSAGE")) {
                    break;
                }
            }

            String json = jsonBuilder.toString();
            System.out.println("Empfangener JSON-String: " + json);

            // Deserialisiert den JSON-String in ein GameModel-Objekt
            return gson.fromJson(json, GameModel.class);
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Daten vom Server: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der empfangenen Daten: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            reader.close();
            socket.close();
            System.out.println("Verbindung zum Server geschlossen.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient("10.10.0.53", 41337); // Beispiel-Host und Port

        // Beispiel für Datenübertragung und Verarbeitung
        GameModel receivedGameModel = gameClient.receiveGameModel();
        if (receivedGameModel != null) {
            // Verarbeitung der empfangenen Daten
            System.out.println("Empfangenes Spielmodell: " + receivedGameModel);
            System.out.println("Score: " + receivedGameModel.getCurrentScore());
        } else {
            System.out.println("Kein Spielmodell empfangen.");
        }

        gameClient.close();
    }
}
