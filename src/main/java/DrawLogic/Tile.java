package DrawLogic;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {
    public TileType tile;
    private boolean isGoal = false;
    public Tile(TileType tile){
        this.tile = tile;
    }
    public Image getImage(){
        ImageIcon img;
        switch (tile){
            case DARK -> {
                img =  new ImageIcon("src/assets/board/tile_dark.png");
            }
            case WALL -> {
                img = new ImageIcon("src/assets/board/wall.png");
            }
            case LIGHT -> {
                img = new ImageIcon("src/assets/board/tile.png");
            }
            case NOTHING -> {
                img = new ImageIcon("src/assets/board/nothing.png");
            }
            default -> img = new ImageIcon("src/assets/board/nothing.png");
        }
        return img.getImage();
    }
    public static Image getGoal(boolean winnable){
        ImageIcon img;
        if(winnable){
            return new ImageIcon("src/assets/board/goal.png").getImage();
        }
        else {
            return new ImageIcon("src/assets/board/goal_not_met.png").getImage();
        }
    }
    public TileType getTileType() {
        return tile;
    }

    public boolean isGoal(){
        return isGoal;
    }

    public void setGoal(){
        isGoal = true;
    }
}
