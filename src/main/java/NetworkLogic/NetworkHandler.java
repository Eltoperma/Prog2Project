package NetworkLogic;

import GameLogic.GameHandler;
import model.GameModel;
import model.ModelHandler;

import java.io.IOException;

public class NetworkHandler {

    GameServer gameServer;
    GameClient gameClient;
    ModelHandler modelHandler;

    public NetworkHandler(boolean isHost, boolean isSpectator){
        modelHandler = GameHandler.getModelHandler();

        if(isHost){
            try {
                gameServer = new GameServer(modelHandler.getGameModel());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(isSpectator){
            try {
                gameClient = new GameClient("10.25.0.230", 12345);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void updateGameState(GameModel gameModel) {

    }
}
