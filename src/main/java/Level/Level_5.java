package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_5 extends Level {
    @Override
    public void configure() {

        //user input
        ID = 5;
        height = 11;
        width = 11;
        startingPosition = new Position(6, 4);
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
        drawLineOfWalls(new Position(3, 1), new Position(7, 1));
        drawLineOfWalls(new Position(1, 2), new Position(3, 2));
        drawLineOfWalls(new Position(5, 5), new Position(7, 5));
        drawLineOfWalls(new Position(1, 6), new Position(5, 6));
        drawLineOfWalls(new Position(7, 6), new Position(9, 6));
        drawLineOfWalls(new Position(1, 8), new Position(3, 8));
        drawLineOfWalls(new Position(3, 9), new Position(5, 9));
        drawLineOfWalls(new Position(5, 10), new Position(8, 10));

        //Vertikal
        drawLineOfWalls(new Position(1, 2), new Position(1, 7));
        drawLineOfWalls(new Position(5, 3), new Position(5, 8));
        drawLineOfWalls(new Position(7, 1), new Position(7, 6));
        drawLineOfWalls(new Position(9, 6), new Position(9, 9));


        fillWithStandards(new Position(2, 2), new Position(6, 5));
        fillWithStandards(new Position(3, 6), new Position(8, 9));

        tiles.put(new Position(3, 5), new Tile(TileType.WALL));
        tiles.put(new Position(8, 9), new Tile(TileType.WALL));

        tiles.put(new Position(2, 7), new Tile(TileType.STANDARD));

    }
    private void configureGoal() {
        setGoal(new Position(4, 8));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 4), Upgrades.TWO);
        upgrades.put(new Position(3, 4), Upgrades.TWO);
        upgrades.put(new Position(3, 7), Upgrades.TWO);
        upgrades.put(new Position(7, 8), Upgrades.TWO);

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
