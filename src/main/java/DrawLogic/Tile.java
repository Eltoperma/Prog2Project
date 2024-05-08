package DrawLogic;

import javax.swing.*;
import java.awt.*;

public class Tile {
    TileType tile;
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

    public TileType getTileType() {
        return tile;
    }
}
