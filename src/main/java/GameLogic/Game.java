package GameLogic;

import Level.*;

public class Game {
    Player player;
    static Level currentlevel;

    public Game(){
        player = new Player(currentlevel.startingPosition, new Upgrade());
    }

    public void setCurrentLevel(Level level){
        this.currentlevel = level;
    }
}
