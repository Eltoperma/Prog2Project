package NetworkLogic;

import GameLogic.GameController;
import model.GameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private GameModel gameModel;
    private ServerSocket serverSocket;
    private List<ObjectOutputStream> clientOutputs;
    Socket clientSocket;
    private int PORT;

    public GameServer(int PORT) throws IOException {
        this.PORT = PORT;
        serverSocket = new ServerSocket(41337); // Example port
        clientOutputs = new ArrayList<>(); //Wo für?


        new Thread(this::acceptClients).start();
        System.out.println("GameServer started on port " + getPort());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    private void acceptClients() {
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                //ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                clientOutputs.add(out);

                handleClient(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Spielstand wird nicht beim Übersenden aktualisiert. Dies geschieht aus nicht bekannten Gründen und auf Ihr Anraten hin schreiben
    wir diesen Kommentar als Hinweis auf unser Gespräch.
     */
    private void handleClient(ObjectOutputStream out) {
        try {
            while (true) {
                // Send the current game state to the client

                gameModel = GameController.getGameHandler().getGameModel();
                System.out.println("Network GameModel: " + gameModel.getCurrentScore());

                out.writeUnshared(gameModel);
                out.flush(); // Ensure data is sent immediately
                out.reset();
                // Print debug information
                System.out.println("Server received game model: " + gameModel);

                // Update all clients with the new game model
                //updateAllClients();

                Thread.sleep(12);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateAllClients() {
        for (ObjectOutputStream out : clientOutputs) {
            try {
                out.writeObject(gameModel);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGameModel(GameModel gm) {
        this.gameModel = gm;
    }
}