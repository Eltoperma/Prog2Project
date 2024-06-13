package NetworkLogic;

import model.GameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private GameModel game;
    private ServerSocket serverSocket;

    public GameServer(GameModel game) throws IOException {
        this.game = game;
        serverSocket = new ServerSocket(12345); // Beispiel-Port
        new Thread(this::handleClient).start();
        System.out.println("GameServer gestartet auf dem Port " + getPort());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    private void handleClient() {
        try {
            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                out.writeObject(game);
                game = (GameModel) in.readObject();
                // Spiel-Logik hier aktualisieren
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}