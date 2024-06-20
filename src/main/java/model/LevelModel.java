package model;

import DrawLogic.TileType;
import GameLogic.Position;
import DrawLogic.Tile;
import GameLogic.Upgrades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import Level.Level;

public class LevelModel implements Serializable {

    private String title;
    private int ID = 0;
    private Map<Position, Tile> tiles;               //spielbare Fläche
    private Map<Position, Upgrades> upgrades;
    private int height = 100;                              //inkl. unspielbarer Fläche
    private int width = 100;                               //inkl. unspielbarer Fläche
    private Position startingPosition;
    private ArrayList<Position> finishPositions;
    private int bestScore = 0;

    public LevelModel(Level level) {
        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }
        this.title = level.title;
        this.ID = level.ID;
        this.tiles = level.tiles;
        this.upgrades = level.upgrades;
        this.height = level.height;
        this.width = level.width;
        this.startingPosition = level.startingPosition;
        this.finishPositions = level.finishPositions;
        this.bestScore = level.bestScore;
    }

    public LevelModel(){

    }
    // Getter und Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Map<Position, Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Map<Position, Tile> tiles) {
        this.tiles = tiles;
    }

    public Map<Position, Upgrades> getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(Map<Position, Upgrades> upgrades) {
        this.upgrades = upgrades;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
    }

    public ArrayList<Position> getFinishPositions() {
        return finishPositions;
    }

    public void setFinishPositions(ArrayList<Position> finishPositions) {
        this.finishPositions = finishPositions;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public boolean isPlayable(Position pos){
        Position newPos = new Position(pos.x, pos.y);
        if(tiles.containsKey(newPos)){
            return tiles.get(newPos).getTileType().equals(TileType.DARK) || tiles.get(newPos).getTileType().equals(TileType.LIGHT) || tiles.get(pos).isGoal();
        }
        throw new RuntimeException("Diese Position existiert nicht!?");
    }
}
