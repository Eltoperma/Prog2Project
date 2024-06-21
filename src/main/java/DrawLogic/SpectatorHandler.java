package DrawLogic;

import model.GameModel;

import javax.swing.*;

public class SpectatorHandler {

    GameModel gameModel;
    SpectatorWindow window;

    public SpectatorHandler(GameModel gameModel){
        this.gameModel = gameModel;

        SwingUtilities.invokeLater(() -> {
            window = new SpectatorWindow(gameModel);
            window.setVisible(true);
        });
    }


    public void updateGameModel(GameModel receivedGameModel) {
        window.updateGameModel(receivedGameModel);
    }
}
