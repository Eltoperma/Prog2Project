package GameLogic;

import Level.Level;
import java.util.Timer;
import model.GameModel;
import model.LevelModel;
import model.PlayerModel;

public class GameHandler {
    private GameModel gameModel;
    private Timer timer;
    private GameTimerTask task;
    private PlayerHandler playerHandler;

    public GameHandler(Level currentLevel) {
        this.gameModel = new GameModel(currentLevel);
        playerHandler = new PlayerHandler(this);
        gameModel.setUsername(GameController.fetchUsername());
        initLevelParams();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setCurrentLevel(Level level) {
        gameModel.setLevelModel(new LevelModel(level));
        clearValues();
        gameModel.getLevelModel().setBestScore(GameController.fetchLevelUserData(level.getID()).getHighScore());
        initLevelParams();
    }

    public LevelModel getCurrentLevel(){
        return gameModel.getLevelModel();
    }

    public void addPlayer() {

        gameModel.setPlayerModel(new PlayerModel(gameModel.getLevelModel().getStartingPosition()));
        playerHandler.setPlayerModel(gameModel.getPlayerModel());
        System.out.println("addPlayer: " + gameModel.getLevelModel().getStartingPosition() + " class: " + gameModel.getLevelModel().getClass());
    }

    public void updateMoves() {
        gameModel.setMovesCount(gameModel.getMovesCount() + 1);
        gameModel.setCurrentScore((int) (gameModel.getCurrentScore() * 0.98));
    }

    public void updateTimer() {
        gameModel.setTimeCount(gameModel.getTimeCount() + 1);
        gameModel.setCurrentScore((int) (gameModel.getCurrentScore() * 0.992));
    }

    public void finish() {
        gameModel.setFinished(true);
        timer.cancel();
        testForBestScore();
    }

    private void testForBestScore() {
        if (gameModel.isFinished()) {
            if (gameModel.getCurrentScore() > gameModel.getLevelModel().getBestScore()) {
                System.out.println("Neuer Highscore!");
                gameModel.getLevelModel().setBestScore(gameModel.getCurrentScore());
                GameController.savePersonalHighScore(gameModel.getLevelModel(), gameModel.getCurrentScore());
            }
            if (gameModel.getCurrentScore() > GameController.fetchLevelData(gameModel.getLevelModel().getID()).getHighscore()) {
                GameController.saveHighscore(gameModel.getLevelModel().getID(), gameModel.getCurrentScore());
            }
        }
    }

    private void clearValues() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void initLevelParams() {
        gameModel.setFinished(false);
        gameModel.setMovesCount(0);
        gameModel.setTimeCount(0);
        gameModel.setCurrentScore(gameModel.getBASE_SCORE());
        countTime();
    }

    public void countTime() {
        timer = new Timer();
        task = new GameTimerTask(this);
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}
