package Level;

import DrawLogic.Tile;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.Map;

public class LoadedLevel extends Level {
    public LoadedLevel(String title, Map<Position, Tile> tiles, Map<Position, Upgrades> upgrades, Position startingPosition, ArrayList<Position> finishPositions, int height, int width){
        this.title = title;
        this.tiles = tiles;
        this.upgrades = upgrades;
        this.startingPosition = startingPosition;
        this.finishPositions = finishPositions;
        this.height = height;
        this.width = width;
    }

    public Level loadLevel(String title, Map<Position, Tile> tiles, Map<Position, Upgrades> upgrades, Position startingPosition, ArrayList<Position> finishPositions, int height, int width){
        return new LoadedLevel(title, tiles, upgrades, startingPosition, finishPositions, height, width);
    }
}
