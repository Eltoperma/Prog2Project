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
        tiles.put(new Position(3, 1), new Tile(TileType.STANDARD));
        tiles.put(new Position(1, 2), new Tile(TileType.STANDARD));
        tiles.put(new Position(3, 2), new Tile(TileType.STANDARD));
        tiles.put(new Position(3, 3), new Tile(TileType.STANDARD));

        drawLineOfWalls(new Position(0, 0), new Position(9, 0));
        //
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(3, 1), Upgrades.ONE);
        upgrades.put(new Position(3, 0), Upgrades.ONE);
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