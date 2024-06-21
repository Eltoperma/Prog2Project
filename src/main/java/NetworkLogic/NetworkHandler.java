package NetworkLogic;

import GameLogic.GameController;
import model.GameModel;
import model.ModelHandler;

import java.io.IOException;

public class NetworkHandler {

    private GameServer gameServer;
    private GameClient gameClient;
    private ModelHandler modelHandler;
    private boolean isHost;
    private String ip = "localhost";
    private final int PORT = 41337;

    public NetworkHandler(){

    }
    public void updateGameState(GameModel gameModel) {

    }

    public void startNetwork(){
        modelHandler = GameController.getModelHandler();

        if(isHost){
            try {
                gameServer = new GameServer(PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                gameClient = new GameClient(ip, PORT);
            } catch (Exception e) {
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
