package NetworkLogic;

import GameLogic.GameController;
import model.GameModel;
import model.ModelHandler;

import java.io.IOException;

public class NetworkHandler {

    private GameServer gameServer;
    private GameClient gameClient;
    private ModelHandler modelHandler;

    public NetworkHandler(boolean isHost, boolean isSpectator){
        modelHandler = GameController.getModelHandler();

        if(isHost){
            try {
                gameServer = new GameServer(modelHandler.getGameModel());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(isSpectator){
            try {
                gameClient = new GameClient("localhost", 41337);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void updateGameState(GameModel gameModel) {
        gameServer.setGameModel(gameModel);
    }
}
