package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_Test extends Level {

    @Override
    public void configure() {

        //user input
        height = 10;
        width = 10;
        startingPosition = new Position(3, 3);
        //
        finishPositions = new ArrayList<>();

        configureTiles();
        configureUpgrades();
        configureRest();
        addCheckPattern();
        setGoal(new Position(7, 4));

    }

    private void configureTiles() {

        tiles = new HashMap<>();
        //user input

        drawLineOfWalls(new Position(1, 1), new Position(8, 1));
        drawLineOfWalls(new Position(1, 8), new Position(8, 8));
        drawLineOfWalls(new Position(1, 1), new Position(1, 8));
        drawLineOfWalls(new Position(8, 1), new Position(8, 8));

        fillWithStandards(new Position(2, 2), new Position(7, 7));

        tiles.put(new Position(7, 3), new Tile(TileType.WALL));
        tiles.put(new Position(6, 4), new Tile(TileType.WALL));
        tiles.put(new Position(7, 5), new Tile(TileType.WALL));

        //
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 3), Upgrades.TWO);
        upgrades.put(new Position(2, 7), Upgrades.ONE);
        upgrades.put(new Position(7, 2), Upgrades.THREE);
        upgrades.put(new Position(7, 7), Upgrades.TWO);

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