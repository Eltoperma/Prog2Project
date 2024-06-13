package events;

import GameLogic.GameHandler;
import model.ModelHandler;
import NetworkLogic.NetworkHandler;

public class GameEventListener {
    NetworkHandler networkHandler;
    ModelHandler modelHandler;
    public GameEventListener(){
        networkHandler = GameHandler.getNetworkHandler();
        modelHandler = GameHandler.getModelHandler();
    }
    public void handleGameEvent(GameEvent event) {
        this.networkHandler.updateGameState(modelHandler.getGameModel());
    }
}
