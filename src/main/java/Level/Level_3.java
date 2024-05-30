package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_3 extends Level{



    @Override
    public void configure() {

        //user input
        height = 11;
        width = 11;
        startingPosition = new Position(3, 3);
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
        drawLineOfWalls(new Position(1, 1), new Position(5, 1));
        drawLineOfWalls(new Position(5, 2), new Position(10, 2));
        drawLineOfWalls(new Position(5, 4), new Position(10, 4));
        drawLineOfWalls(new Position(4, 5), new Position(6, 5));
        drawLineOfWalls(new Position(3, 7), new Position(5, 7));
        drawLineOfWalls(new Position(7, 8), new Position(9, 8));
        drawLineOfWalls(new Position(2, 10), new Position(7, 10));



        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 9));
        drawLineOfWalls(new Position(4, 5), new Position(4, 8));
        drawLineOfWalls(new Position(5, 1), new Position(5, 5));
        drawLineOfWalls(new Position(7, 7), new Position(7, 10));
        drawLineOfWalls(new Position(9, 4), new Position(9, 8));

        fillWithStandards(new Position(2, 2), new Position(9, 8));
        fillWithStandards(new Position(2, 2), new Position(7, 9));
        tiles.put(new Position(9, 3), new Tile(TileType.STANDARD));

        tiles.put(new Position(8, 3), new Tile(TileType.WALL));
        tiles.put(new Position(10, 3), new Tile(TileType.WALL));
        tiles.put(new Position(2, 5), new Tile(TileType.WALL));
        tiles.put(new Position(5, 8), new Tile(TileType.WALL));
        tiles.put(new Position(2, 9), new Tile(TileType.WALL));

    }
    private void configureGoal() {
        setGoal(new Position(9, 3));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 2), Upgrades.ONE);
        upgrades.put(new Position(4, 2), Upgrades.TWO);
        upgrades.put(new Position(2, 4), Upgrades.ONE);
        upgrades.put(new Position(4, 4), Upgrades.TWO);

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
