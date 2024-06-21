package Level;

import GameLogic.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_20 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 20;
        height = 14;
        width = 14;
        startingPosition = new Position(11, 6);
        //
        finishPositions = new ArrayList<>();

        configureTiles();
        configureUpgrades();
        configureRest();
        addCheckPattern();
        configureGoals();
    }


    private void configureTiles() {

        tiles = new HashMap<>();
        //user input

        //Horizontal
        drawLineOfWalls(new Position(1, 1), new Position(3, 1));
        drawLineOfWalls(new Position(6, 3), new Position(11, 3));
        drawLineOfWalls(new Position(1, 4), new Position(6, 4));
        drawLineOfWalls(new Position(1, 8), new Position(6, 8));
        drawLineOfWalls(new Position(6, 9), new Position(11, 9));
        drawLineOfWalls(new Position(1, 11), new Position(3, 11));


        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 11));
        drawLineOfWalls(new Position(3, 1), new Position(3, 5));
        drawLineOfWalls(new Position(3, 7), new Position(3, 11));
        drawLineOfWalls(new Position(12, 4), new Position(12, 8));

        fillWithStandards(new Position(2, 4), new Position(12, 8));

        tiles.put(new Position(2, 3), new Tile(TileType.WALL));
        tiles.put(new Position(11, 4), new Tile(TileType.WALL));
        tiles.put(new Position(5, 5), new Tile(TileType.WALL));
        tiles.put(new Position(6, 5), new Tile(TileType.WALL));
        tiles.put(new Position(9, 5), new Tile(TileType.WALL));
        tiles.put(new Position(10, 6), new Tile(TileType.WALL));
        tiles.put(new Position(5, 7), new Tile(TileType.WALL));
        tiles.put(new Position(6, 7), new Tile(TileType.WALL));
        tiles.put(new Position(9, 7), new Tile(TileType.WALL));
        tiles.put(new Position(11, 8), new Tile(TileType.WALL));
        tiles.put(new Position(2, 9), new Tile(TileType.WALL));

        tiles.put(new Position(2, 2), new Tile(TileType.STANDARD));
        tiles.put(new Position(2, 10), new Tile(TileType.STANDARD));

        //
    }
    private void configureGoals() {
        setGoal(new Position(2, 2));
        setGoal(new Position(2, 10));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(8, 4), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(8, 5), Upgrades.THREE);
        upgrades.put(new Position(8, 6), Upgrades.TWO);
        upgrades.put(new Position(8, 7), Upgrades.ONE);
        upgrades.put(new Position(8, 8), Upgrades.NONE);

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
