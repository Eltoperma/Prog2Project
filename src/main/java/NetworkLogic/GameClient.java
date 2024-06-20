package NetworkLogic;

import model.GameModel;

import java.io.*;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public GameClient(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Verbunden mit Server: " + host + ":" + port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Fehler beim Verbinden mit Server: " + e.getMessage());
        }
    }

    public void sendGameModel(GameModel game) {
        try {
            out.writeObject(game);
            out.flush(); // Sicherstellen, dass Daten sofort gesendet werden
            System.out.println("Spielmodell erfolgreich an Server gesendet.");
        } catch (IOException e) {
            System.err.println("Fehler beim Senden des Spielmodells: " + e.getMessage());
        }
    }

    public GameModel receiveGameModel() {
        try {
            GameModel received = (GameModel) in.readObject();
            System.out.println("Spielmodell erfolgreich vom Server empfangen.");
            return received;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Fehler beim Empfangen des Spielmodells: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Verbindung zum Server geschlossen.");
        } catch (IOException e) {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient("localhost", 41337); // Beispiel-Host und Port

        try {
            GameModel receivedGameModel;
            while (true) {
                receivedGameModel = gameClient.receiveGameModel();
                if (receivedGameModel != null) {

                    System.out.println("Empfangenes Spielmodell: " + receivedGameModel);
                    System.out.println("Score: " + receivedGameModel.getCurrentScore());
                    System.out.println("Spieler: " + receivedGameModel.getUsername());
                    System.out.println("PosX: " + receivedGameModel.getPlayerModel().getPlayerPosition().x);
                } else {
                    break;
                }
            }
        } finally {
            gameClient.close();
        }
    }
}
