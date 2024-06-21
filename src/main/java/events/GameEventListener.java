package events;

import GameLogic.GameController;
import model.ModelHandler;
import NetworkLogic.NetworkHandler;

public class GameEventListener {
    NetworkHandler networkHandler;
    ModelHandler modelHandler;
    public GameEventListener(){
        networkHandler = GameController.getNetworkHandler();
        modelHandler = GameController.getModelHandler();
    }
    public void handleGameEvent(GameEvent event) {
        this.networkHandler.updateGameState(modelHandler.getGameModel());
    }
}
