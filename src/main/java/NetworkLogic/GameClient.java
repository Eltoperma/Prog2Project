package NetworkLogic;

import DrawLogic.DrawPanel;
import DrawLogic.SpectatorHandler;
import DrawLogic.SpectatorWindow;
import GameLogic.GameController;
import GameLogic.Position;
import model.GameModel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GameModel currentGameModel;
    private String ip;
    private int PORT;

    public GameClient(String host, int PORT) {
        try {
            this.ip = host;
            this.PORT = PORT;
            socket = new Socket(host, PORT);
            System.out.println("Verbunden mit Server: " + host + ":" + PORT);
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e) {
            System.err.println("Fehler beim Verbinden mit Server: " + e.getMessage());
        }
    }

    public GameModel receiveGameModel() {
        try {
            GameModel received = (GameModel) in.readObject();
            System.out.println("Spielmodell erfolgreich vom Server empfangen.");
            currentGameModel = received;

            return received;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

    public void handleClient(){
        try {
            GameModel receivedGameModel;
            receivedGameModel = receiveGameModel();

            SpectatorHandler spectatorHandler = new SpectatorHandler(receivedGameModel);

            while (true) {
                receivedGameModel = receiveGameModel();
                if (receivedGameModel != null) {
                    spectatorHandler.updateGameModel(receivedGameModel);
                } else {
                    break;
                }
            }
        } finally {
            close();
        }
    }

    public static void main(String[] args) {
        GameClient gameClient = new GameClient("localhost", 41337); // Beispiel-Host und Port
        gameClient.handleClient();
    }
}