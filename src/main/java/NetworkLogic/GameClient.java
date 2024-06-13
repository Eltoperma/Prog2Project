package NetworkLogic;

import model.GameModel;

import java.io.*;
import java.net.Socket;

public class GameClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public GameClient(String host, int port) throws IOException {
        socket = new Socket(host, port); // Verbindet zum Server
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendGameModel(GameModel game) throws IOException {
        out.writeObject(game); // Sende das Spielmodell zum Server
    }

    public GameModel receiveGameModel() throws IOException, ClassNotFoundException {
        return (GameModel) in.readObject(); // Empfange das Spielmodell vom Server
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}