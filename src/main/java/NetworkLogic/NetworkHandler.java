package NetworkLogic;

import GameLogic.GameController;
import model.GameModel;

import java.io.IOException;

public class NetworkHandler {

    private GameServer gameServer;
    private GameClient gameClient;
    private boolean isHost;
    private String ip = "localhost";
    private final int PORT = 41337;

    public NetworkHandler(){

    }

    public void startNetwork(){
        System.out.println("Starting network...");

        if(isHost){
            try {
                gameServer = new GameServer(PORT);
                System.out.println("Game server started on port: " + PORT);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                gameClient = new GameClient(ip, PORT);
                System.out.println("Game client started with IP: " + ip + " and port: " + PORT);
                gameClient.handleClient();

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
