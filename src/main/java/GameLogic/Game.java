package GameLogic;

import Level.*;

public class Game {
    Player player;
    static Level currentlevel;

    public Game(){
    }

    public void setCurrentLevel(Level level){
        this.currentlevel = level;
    }

    public void addPlayer(){
        player = new Player(currentlevel.startingPosition, new Upgrade());
    }
}
