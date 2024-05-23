package GameLogic;

import Level.*;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    public Player player;
   public Level currentlevel;
    private  int movesCount;
    private  int timeCount;

   public  boolean isFinished = false;

    public Game(){
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
        initLevelParams();
    }

    public void initLevelParams(){
        movesCount = 0;
        timeCount = 0;
        countTime();
    }

    public void countTime(){
        Timer timer = new Timer();
        GameTimerTask task = new GameTimerTask();

        // Starte den Timer, die Aufgabe wird jede Sekunde ausgeführt
        timer.scheduleAtFixedRate(task, 0, 1000);

        // Zum Beispiel, um den Timer nach 10 Sekunden zu stoppen:
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                System.out.println("Timer gestoppt");
            }
        }, 10000);

        // Hier kannst du andere Spiel-logik einfügen
        // Der Timer läuft im Hintergrund

    }


    public void addPlayer(){
        System.out.println("addPlayer: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        player = new Player(currentlevel.startingPosition, new Upgrade());
    }

    public Player getPlayer() {
        return player;
    }

    public Level getCurrentlevel() {
        return currentlevel;
    }

    public void finish(){
        isFinished = true;
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
}
