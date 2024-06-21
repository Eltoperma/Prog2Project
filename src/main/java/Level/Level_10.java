package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_10 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 20;
        height = 11;
        width = 11;
        startingPosition = new Position(4, 2);
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
        drawLineOfWalls(new Position(3, 1), new Position(5, 1));
        drawLineOfWalls(new Position(1, 2), new Position(3, 2));
        drawLineOfWalls(new Position(5, 2), new Position(8, 2));
        drawLineOfWalls(new Position(3, 5), new Position(5, 5));
        drawLineOfWalls(new Position(1, 8), new Position(3, 8));
        drawLineOfWalls(new Position(5, 8), new Position(8, 8));
        drawLineOfWalls(new Position(3, 9), new Position(5, 9));

        //Vertikal
        drawLineOfWalls(new Position(1, 2), new Position(1, 8));
        drawLineOfWalls(new Position(3, 4), new Position(3, 6));
        drawLineOfWalls(new Position(8, 2), new Position(8, 8));

        fillWithStandards(new Position(2, 2), new Position(7, 8));

        tiles.put(new Position(6, 3), new Tile(TileType.WALL));
        tiles.put(new Position(7, 4), new Tile(TileType.WALL));
        tiles.put(new Position(7, 6), new Tile(TileType.WALL));
        tiles.put(new Position(6, 7), new Tile(TileType.WALL));


    }
    private void configureGoal() {
        setGoal(new Position(7, 5));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(5, 3), Upgrades.TWO);
        upgrades.put(new Position(2, 5), Upgrades.TWO);
        upgrades.put(new Position(6, 5), Upgrades.TWO);
        upgrades.put(new Position(5, 7), Upgrades.TWO);

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
