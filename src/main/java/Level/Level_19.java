package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_19 extends Level {

    @Override
    public void configure() {

        //user input
        height = 10;
        width = 10;
        startingPosition = new Position(2, 4);
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
        drawLineOfWalls(new Position(1, 2), new Position(6, 2));
        drawLineOfWalls(new Position(6, 3), new Position(8, 3));
        drawLineOfWalls(new Position(2, 7), new Position(8, 7));

        //Vertikal
        drawLineOfWalls(new Position(1, 2), new Position(1, 5));
        drawLineOfWalls(new Position(2, 5), new Position(2, 7));
        drawLineOfWalls(new Position(6, 1), new Position(6, 5));
        drawLineOfWalls(new Position(8, 1), new Position(8, 7));

        fillWithStandards(new Position(2, 2), new Position(7, 6));

        tiles.put(new Position(5, 4), new Tile(TileType.WALL));
        tiles.put(new Position(4, 6), new Tile(TileType.WALL));


    }
    private void configureGoal() {
        setGoal(new Position(7, 2));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(4, 3), Upgrades.TWO);
        upgrades.put(new Position(5, 3), Upgrades.TWO);
        upgrades.put(new Position(7, 4), Upgrades.ONE);
        upgrades.put(new Position(3, 5), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(4, 5), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(7, 5), Upgrades.TWO);
        upgrades.put(new Position(3, 6), Upgrades.NONE);


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
