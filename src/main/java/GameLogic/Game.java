package GameLogic;

import Level.*;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    public Player player;
   public Level currentlevel;
    private  int movesCount;
    private  int timeCount;
    private Timer timer;
    private GameTimerTask task;

    public  boolean isFinished = false;

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
        System.out.println("Move: " + movesCount);
    }

    public void updateTimer(){
        timeCount++;
    }

    public long getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        timeCount = timeCount;
    }

    public void setCurrentLevel(Level level){
        currentlevel = level;
        System.out.println("setCurrentLevel: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
    }

    public void initLevelParams(){
        movesCount = 0;
        timeCount = 0;
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
        System.out.println("Win du wichser");
        System.out.println("Moves: " + movesCount + " Zeit: " + timeCount);
        testForBestScore();
        GameHandler.nextGame();
    }

    private void testForBestScore() {
        if(isFinished) {
            if(movesCount < currentlevel.bestMoves){
                System.out.println("Neuer Rekord an Zügen!");
                currentlevel.bestMoves = movesCount;
            }
            if(timeCount < currentlevel.bestTime){
                System.out.println("Neue Bestzeit!");
                currentlevel.bestTime = timeCount;
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
}
