package Level;

import GameLogic.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_15 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 15;
        height = 12;
        width = 12;
        startingPosition = new Position(5, 8);
        //
        finishPositions = new ArrayList<>();

        configureTiles();
        configureUpgrades();
        configureRest();
        addCheckPattern();
        configureGoal();
    }


    private void configureTiles() {

        tiles = new HashMap<>();
        //user input


        //Horizontal
        drawLineOfWalls(new Position(2, 1), new Position(8, 1));
        drawLineOfWalls(new Position(8, 2), new Position(10, 2));
        drawLineOfWalls(new Position(1, 4), new Position(4, 4));
        drawLineOfWalls(new Position(6, 4), new Position(8, 4));
        drawLineOfWalls(new Position(3, 9), new Position(7, 9));
        drawLineOfWalls(new Position(1, 10), new Position(3, 10));
        drawLineOfWalls(new Position(1, 10), new Position(3, 10));
        drawLineOfWalls(new Position(7, 10), new Position(9, 10));

        //Vertikal
        drawLineOfWalls(new Position(1, 4), new Position(1, 10));
        drawLineOfWalls(new Position(2, 1), new Position(2, 4));
        drawLineOfWalls(new Position(3, 4), new Position(3, 10));
        drawLineOfWalls(new Position(4, 4), new Position(4, 10));
        drawLineOfWalls(new Position(7, 4), new Position(7, 10));
        drawLineOfWalls(new Position(8, 1), new Position(8, 4));
        drawLineOfWalls(new Position(9, 6), new Position(9, 10));
        drawLineOfWalls(new Position(10, 2), new Position(10, 6));

        fillWithStandards(new Position(2, 2), new Position(9, 9));

        tiles.put(new Position(2, 8), new Tile(TileType.WALL));
        tiles.put(new Position(5, 6), new Tile(TileType.WALL));
        tiles.put(new Position(6, 8), new Tile(TileType.WALL));


    }
    private void configureGoal() {
        setGoal(new Position(2, 9));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(6, 2), Upgrades.NONE);
        upgrades.put(new Position(4, 3), Upgrades.NONE);
        upgrades.put(new Position(2, 5), Upgrades.THREE);
        upgrades.put(new Position(5, 5), Upgrades.NONE);
        upgrades.put(new Position(6, 5), Upgrades.THREE);
        upgrades.put(new Position(8, 5), Upgrades.THREE);
        upgrades.put(new Position(5, 7), Upgrades.THREE);
        upgrades.put(new Position(8, 9), Upgrades.THREE);

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

