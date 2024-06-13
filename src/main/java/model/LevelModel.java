package model;

import DrawLogic.Tile;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class LevelModel implements Serializable {
    public String title;
    public int ID = 0;
    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;
    public int height;                              //inkl. unspielbarer Fläche
    public int width;                               //inkl. unspielbarer Fläche
    public Position startingPosition;
    public ArrayList<Position> finishPositions;
    public int bestScore = 0;
}
