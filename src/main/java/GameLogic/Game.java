package GameLogic;

import Level.*;

public class Game {
    public static Player player;
   public static Level currentlevel;

   public static boolean isFinished = false;

    public Game(){
    }

    public static void setCurrentLevel(Level level){
        currentlevel = level;
        System.out.println("setCurrentLevel: " + currentlevel.startingPosition + " class: " + currentlevel.getClass());
        
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
//        GameHandler.nextGame();
    }
}
