package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_17 extends Level {

    @Override
    public void configure() {

        //user input
        height = 11;
        width = 11;
        startingPosition = new Position(7, 4);
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
        drawLineOfWalls(new Position(6, 5), new Position(8, 5));
        drawLineOfWalls(new Position(1, 7), new Position(7, 7));
        drawLineOfWalls(new Position(3, 9), new Position(5, 9));


        //Vertikal
        drawLineOfWalls(new Position(1, 5), new Position(1, 7));
        drawLineOfWalls(new Position(2, 1), new Position(2, 5));
        drawLineOfWalls(new Position(3, 7), new Position(3, 9));
        drawLineOfWalls(new Position(5, 7), new Position(5, 9));
        drawLineOfWalls(new Position(6, 3), new Position(6, 5));
        drawLineOfWalls(new Position(7, 5), new Position(7, 7));
        drawLineOfWalls(new Position(8, 1), new Position(8, 5));


        fillWithStandards(new Position(2, 2), new Position(7, 6));

        tiles.put(new Position(4, 3), new Tile(TileType.WALL));

        tiles.put(new Position(4, 8), new Tile(TileType.STANDARD));

    }
    private void configureGoal() {
        setGoal(new Position(4, 8));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(5, 2), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(7, 2), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(5, 3), Upgrades.ONE);
        upgrades.put(new Position(4, 4), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(2, 6), Upgrades.TWO);
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
