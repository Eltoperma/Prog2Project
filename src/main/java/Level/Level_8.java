package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_8 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 8;
        height = 13;
        width = 13;
        startingPosition = new Position(2, 3);
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
        drawLineOfWalls(new Position(6, 1), new Position(8, 1));
        drawLineOfWalls(new Position(1, 2), new Position(3, 2));
        drawLineOfWalls(new Position(8, 2), new Position(10, 2));
        drawLineOfWalls(new Position(3, 4), new Position(8, 4));
        drawLineOfWalls(new Position(1, 7), new Position(4, 7));
        drawLineOfWalls(new Position(4, 10), new Position(8, 10));
        drawLineOfWalls(new Position(1, 11), new Position(4, 11));

        //Vertikal
        drawLineOfWalls(new Position(1, 2), new Position(1, 11));
        drawLineOfWalls(new Position(3, 2), new Position(3, 4));
        drawLineOfWalls(new Position(4, 6), new Position(4, 8));
        drawLineOfWalls(new Position(6, 1), new Position(6, 4));
        drawLineOfWalls(new Position(7, 4), new Position(7, 6));
        drawLineOfWalls(new Position(9, 6), new Position(9, 9));
        drawLineOfWalls(new Position(10, 2), new Position(10, 6));


        fillWithStandards(new Position(2, 3), new Position(3, 10));
        fillWithStandards(new Position(4, 5), new Position(6, 9));
        fillWithStandards(new Position(7, 2), new Position(9, 9));

        tiles.put(new Position(6, 6), new Tile(TileType.WALL));
        tiles.put(new Position(6, 8), new Tile(TileType.WALL));
        tiles.put(new Position(8, 9), new Tile(TileType.WALL));

    }
    private void configureGoal() {
        setGoal(new Position(7, 2));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(4, 5), Upgrades.ONE);
        upgrades.put(new Position(3, 6), Upgrades.ONE);
        upgrades.put(new Position(3, 8), Upgrades.TWO);
        upgrades.put(new Position(6, 9), Upgrades.TWO);

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
