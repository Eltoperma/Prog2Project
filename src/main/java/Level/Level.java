package Level;

import AssetManager.Tile;
import AssetManager.TileType;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;


import java.util.Map;

public abstract class Level {


    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;
    public int height;                              //inkl. unspielbarer Fläche
    public int width;                               //inkl. unspielbarer Fläche

    public void configure() {

    }
    public void addCheckPattern(){
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                if(tiles.get(new Position(x, y)).getTileType().equals(TileType.STANDARD)){
                    if(y % 2 == 0) {
                        if (x % 2 == 0) tiles.put(new Position(x, y), new Tile(TileType.DARK));
                        else tiles.put(new Position(x, y), new Tile(TileType.LIGHT));
                    }
                    else{
                        if (x % 2 != 0) tiles.put(new Position(x, y), new Tile(TileType.DARK));
                        else tiles.put(new Position(x, y), new Tile(TileType.LIGHT));
                    }
                }
            }
        }
    }

    public void drawLineOfWalls(Position start, Position end){
        if(start.x <= end.x && start.y <= end.y) {
            if (start.x != end.x) {
                for (int i = start.x; i <= end.x; i++) {
                    tiles.put(new Position(i, start.y), new Tile(TileType.WALL));
                }
            }
            else if (start.y != end.y) {
                for (int i = start.y; i <= end.y; i++) {
                    tiles.put(new Position(i, start.x), new Tile(TileType.WALL));
                }
            }
        }
        else{
            throw new RuntimeException("Du dummer HS, das war falsch herum >:(");
        }
    }

}
