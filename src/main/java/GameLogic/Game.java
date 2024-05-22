package GameLogic;

import Level.*;

import java.util.Timer;
import java.util.TimerTask;

public class Game {
    public static Player player;
   public static Level currentlevel;
    private static int movesCount;
    private static int timeCount;

   public static boolean isFinished = false;

    public Game(){
    }

    public static int getMovesCount() {
        return movesCount;
    }

    public static void setMovesCount(int movesCount) {
        Game.movesCount = movesCount;
    }

    public static void updateMoves(){
        movesCount++;
        System.out.println("Move: " + movesCount);
    }

    public static void updateTimer(){
        timeCount++;
    }

    public static long getTimeCount() {
        return timeCount;
    }

    public static void setTimeCount(int timeCount) {
        Game.timeCount = timeCount;
    }

    public static void setCurrentLevel(Level level){
        currentlevel = level;
        System.out.println("setCurrentLevel: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        initLevelParams();
    }

    public static void initLevelParams(){
        movesCount = 0;
        timeCount = 0;
        countTime();
    }

    public static void countTime(){
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


    public static void addPlayer(){
        System.out.println("addPlayer: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        player = new Player(currentlevel.startingPosition, new Upgrade());
    }

    public static Player getPlayer() {
        return player;
    }

    public static Level getCurrentlevel() {
        return currentlevel;
    }

    public static void finish(){
        isFinished = true;
        System.out.println("Win du wichser");
        System.out.println("Moves: " + movesCount + " Zeit: " + timeCount);
        testForBestScore();
        GameHandler.nextGame();
    }

    private static void testForBestScore() {
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
