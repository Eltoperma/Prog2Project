package NetworkLogic;

import Level.Levels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private ServerSocket serverSocket;
    private List<PrintWriter> clientOutputs;
    private Gson gson;

    public GameServer() throws IOException {
        serverSocket = new ServerSocket(41337); // Beispiel-Port
        clientOutputs = new ArrayList<>();
        gson = new GsonBuilder().create();
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
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clientOutputs.add(out);

                new Thread(() -> handleClient(out)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(PrintWriter out) {
        try {
            while (true) {
                GameModel gameModel = createDummyGameModel(); // Dummy-Daten für das Beispiel
                String json = gson.toJson(gameModel);

                out.println(json);
//                out.println("END_OF_MESSAGE");
                System.out.println("Gesendeter JSON-String: " + json);

                Thread.sleep(1000); // Sendet alle 1 Sekunde neue Daten
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private GameModel createDummyGameModel() {
        // Erzeugt ein Dummy-GameModel-Objekt für das Beispiel
        GameModel gameModel = new GameModel(Levels.getLevel(0));
        // Hier das GameModel entsprechend initialisieren
        return gameModel;
    }

    public static void main(String[] args) {
        try {
            new GameServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
