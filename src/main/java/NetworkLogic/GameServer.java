package NetworkLogic;

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

    public GameServer(GameModel gameModel) throws IOException {
        this.gameModel = gameModel;
        serverSocket = new ServerSocket(41337); // Example port
        clientOutputs = new ArrayList<>();
        new Thread(this::acceptClients).start();
        System.out.println("GameServer started on port " + getPort());
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    private void acceptClients() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                clientOutputs.add(out);
                handleClient(in, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(ObjectInputStream in, ObjectOutputStream out) {
        try {
            while (true) {
                // Send the current game state to the client
                out.writeObject(gameModel);
                out.flush(); // Ensure data is sent immediately

                // Receive the updated game model from the client
                gameModel = (GameModel) in.readObject();

                // Print debug information
                System.out.println("Server received game model: " + gameModel);

                // Update all clients with the new game model
                updateAllClients();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
