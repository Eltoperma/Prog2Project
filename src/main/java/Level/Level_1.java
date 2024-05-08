package Level;

import AssetManager.Tile;
import AssetManager.TileType;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;

import java.util.HashMap;

public class Level_1 extends Level {

    @Override
    public void configure() {

        //user input
        height = 10;
        width = 10;
        startingPosition = new Position(3, 3);
        //

        configureTiles();
        configureUpgrades();
        configureRest();
        addCheckPattern();
    }

    private void configureTiles() {

        tiles = new HashMap<>();
        //user input

        drawLineOfWalls(new Position(0, 0), new Position(9, 1));
        drawLineOfWalls(new Position(0, 9), new Position(9, 9));
        drawLineOfWalls(new Position(0, 1), new Position(0, 8));
        drawLineOfWalls(new Position(9, 1), new Position(9, 8));

        fillWithStandards(new Position(2, 2), new Position(7, 7));

        tiles.put(new Position(7, 3), new Tile(TileType.WALL));
        tiles.put(new Position(6, 4), new Tile(TileType.WALL));
        tiles.put(new Position(7, 5), new Tile(TileType.WALL));

        tiles.put(new Position(7, 4), new Tile(TileType.GOAL));

        //
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 2), Upgrades.ONE);
        upgrades.put(new Position(2, 7), Upgrades.ONE);
        upgrades.put(new Position(6, 3), Upgrades.THREE);
        upgrades.put(new Position(6, 6), Upgrades.TWO);

        //
    }

    private void configureRest() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                tiles.computeIfAbsent(new Position(x, y), k -> new Tile(TileType.NOTHING));
            }
        }
    }


}