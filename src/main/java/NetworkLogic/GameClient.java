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
            System.out.println("Version: " + received.getVersion());
//            if (received.getVersion() == 1 || received.getVersion() > currentGameModel.getVersion()) {
            currentGameModel = received;
            System.out.println("Spielmodell erfolgreich vom Server empfangen.");
                // Verarbeiten Sie das aktualisierte GameModel;
                return received;
//            }
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

            GameModel finalReceivedGameModel = receivedGameModel;
            SwingUtilities.invokeLater(() -> {
                SpectatorWindow window = new SpectatorWindow(finalReceivedGameModel);
                window.setVisible(true);
            });

            while (true) {
                receivedGameModel = receiveGameModel();
                if (receivedGameModel != null) {


                    System.out.println("Empfangenes Spielmodell: " + receivedGameModel);
                    System.out.println("Score: " + receivedGameModel.getCurrentScore());
                    System.out.println("Spieler: " + receivedGameModel.getUsername());
                    System.out.println("PosX: " + receivedGameModel.getPlayerModel().getPlayerPosition().x);
                    System.out.println("Komplex: " + receivedGameModel.getLevelModel().getTiles().get(new Position(4, 5)).getTileType());
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

        try {
            GameModel receivedGameModel;
            receivedGameModel = gameClient.receiveGameModel();

            GameModel finalReceivedGameModel = receivedGameModel;

            SpectatorHandler spectatorHandler = new SpectatorHandler(finalReceivedGameModel);

            while (true) {
                receivedGameModel = gameClient.receiveGameModel();
                if (receivedGameModel != null) {
                    spectatorHandler.updateGameModel(receivedGameModel);

                    System.out.println("Empfangenes Spielmodell: " + receivedGameModel);
                    System.out.println("Score: " + receivedGameModel.getCurrentScore());
                    System.out.println("Spieler: " + receivedGameModel.getUsername());
                    System.out.println("PosX: " + receivedGameModel.getPlayerModel().getPlayerPosition().x);
                    System.out.println("Komplex: " + receivedGameModel.getLevelModel().getTiles().get(new Position(4, 5)).getTileType());
                } else {
                    break;
                }
            }
        } finally {
            gameClient.close();
        }
    }
}