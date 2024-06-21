package DrawLogic;

import model.GameModel;

import javax.swing.*;

public class SpectatorHandler {

    GameModel gameModel;
    SpectatorWindow window;

    public SpectatorHandler(GameModel gameModel){
        System.out.println("SpectatorHandler Constructor: " + gameModel.getCurrentScore());
        this.gameModel = gameModel;
        window = new SpectatorWindow(gameModel);
        window.setVisible(true);

    }

    public void updateGameModel(GameModel receivedGameModel) {
        window.updateGameModel(receivedGameModel);
    }
}
