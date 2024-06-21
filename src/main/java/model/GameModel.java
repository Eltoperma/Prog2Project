package model;

import java.io.Serializable;
import java.util.Map;
import GameLogic.Direction;
import GameLogic.Upgrades;
import GameLogic.Position;
import Level.Level;

public class GameModel implements Serializable {
    private PlayerModel playerModel;
    private LevelModel levelModel;
    private int movesCount;
    private int timeCount;
    private int currentScore;
    private boolean isFinished;
    private final int BASE_SCORE = 10000;
    private int version = 0;


    private String username;

    public GameModel(Level currentLevel) {
        this.playerModel = new PlayerModel(currentLevel.startingPosition);
        this.levelModel = new LevelModel(currentLevel);
        this.movesCount = 0;
        this.timeCount = 0;
        this.currentScore = BASE_SCORE;
        this.isFinished = false;
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public LevelModel getLevelModel() {
        return levelModel;
    }

    public void setLevelModel(LevelModel levelModel) {
        this.levelModel = levelModel;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }



    public void movePlayer(Direction direction) {
        // Spiel-Logik zum Bewegen des Spielers
        System.out.println("Player moved " + direction);
    }

    public boolean allUpgradesCollected() {
        for (Map.Entry<Position, Upgrades> entry : levelModel.getUpgrades().entrySet()) {
            if (entry.getValue() != null) {
                return false;
            }
        }
        return true;
    }

    public int getBASE_SCORE() {
        return BASE_SCORE;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
