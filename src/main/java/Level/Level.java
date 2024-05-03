package Level;

import AssetManager.Tile;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;


import java.util.Map;

public abstract class Level {
//    List<List<Tile>> tiles = new ArrayList<List<Tile>>();
//    List<List<Upgrade>> upgrades = new ArrayList<List<Upgrade>>();
//    List<List<Wall>> walls = new ArrayList<List<Wall>>();

    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;
    public int height;                              //inkl. unspielbarer Fläche
    public int width;                               //inkl. unspielbarer Fläche

    public void configure() {

    }
}
