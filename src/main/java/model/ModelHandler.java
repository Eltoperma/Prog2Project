package model;

import GameLogic.Game;

public class ModelHandler {
    boolean isHost = false;
    boolean isSpectator = false;
    GameModel gameModel;

    public void initGameState(Game game){
        gameModel = new GameModel();
        gameModel = extractModelFromGame(game);
    }
    public GameModel updateGameState(Game game){
        gameModel = extractModelFromGame(game);
        return gameModel;
    }

    public GameModel extractModelFromGame(Game game){
        GameModel extractedGameModel = new GameModel();

        System.out.println("extract: " + game.currentlevel.bestScore);
        gameModel.setLevelModel(game.currentlevel);
        System.out.println("extracted: " + gameModel.getLevelModel().bestScore);

        gameModel.setFinished(game.isFinished);
        gameModel.setPlayer(game.player);
        gameModel.setCurrentScore(game.getCurrentScore());
        gameModel.setTimeCount(game.getTimeCount());
        gameModel.setMovesCount(game.getMovesCount());
        return extractedGameModel;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isSpectator() {
        return isSpectator;
    }

    public void setSpectator(boolean spectator) {
        isSpectator = spectator;
    }

    public GameModel getGameModel() {
        return gameModel;
    }
}
