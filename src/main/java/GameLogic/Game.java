package GameLogic;

import Level.*;

import java.util.Map;
import java.util.Timer;

public class Game {
    public Player player;
   public Level currentlevel;
    private  int movesCount;
    private  int timeCount;
    private Timer timer;
    private final int BASE_SCORE = 10000;
    private int currentScore;
    private GameTimerTask task;

    public boolean isFinished = false;

    public Game(){
        initLevelParams();
    }

    public  int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        movesCount = movesCount;
    }

    public void updateMoves(){
        movesCount++;
        currentScore = (int) (currentScore * 0.98);
        System.out.println("Move: " + movesCount);
    }

    public void updateTimer(){
        timeCount++;
        currentScore = (int) (currentScore * 0.992);
        System.out.println("Score: " + currentScore);
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        timeCount = timeCount;
    }

    public void setCurrentLevel(Level level){
        currentlevel = level;
        System.out.println("setCurrentLevel: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        clearValues();
        currentlevel.bestScore = GameHandler.fetchLevelUserData(currentlevel.ID).getHighScore();
        initLevelParams();
    }

    private void clearValues() {
        timer.cancel();
    }

    public void initLevelParams(){
        isFinished = false;
        movesCount = 0;
        timeCount = 0;
        currentScore = BASE_SCORE;
        countTime();
    }

    public void countTime(){
        timer = new Timer();
        task = new GameTimerTask(this);

        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void addPlayer(){
        System.out.println("addPlayer: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        player = new Player(currentlevel.startingPosition, new Upgrade(), this);
    }

    public Player getPlayer() {
        return player;
    }

    public Level getCurrentlevel() {
        return currentlevel;
    }

    public void finish(){
        isFinished = true;
        timer.cancel();
        System.out.println("Moves: " + movesCount + " Zeit: " + timeCount);
        testForBestScore();
//        GameHandler.nextGame();
    }

    private void testForBestScore() {
        if(isFinished) {
//            if(movesCount < currentlevel.bestMoves){
//                System.out.println("Neuer Rekord an Zügen!");
//                currentlevel.bestMoves = movesCount;
//            }
//            if(timeCount < currentlevel.bestTime){
//                System.out.println("Neue Bestzeit!");
//                currentlevel.bestTime = timeCount;
//            }
            if(currentScore > currentlevel.bestScore){
                System.out.println("Neuer Highscore!");
                currentlevel.bestScore = currentScore;
                GameHandler.savePersonalHighScore(currentlevel, currentScore);
            }
            if(currentScore > GameHandler.fetchLevelData(currentlevel.ID).getHighscore()){
                GameHandler.saveHighscore(currentlevel.ID, currentScore);
            }
        }
    }

    public boolean allUpgradesCollected() {
        for (Map.Entry<Position, Upgrades> entry : currentlevel.upgrades.entrySet()) {
            if (entry.getValue() != null) {
                return false;
            }
        }
        return true;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setCurrentlevel(Level currentlevel) {
        this.currentlevel = currentlevel;
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
}
