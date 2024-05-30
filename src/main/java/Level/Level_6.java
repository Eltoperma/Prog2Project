package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_6 extends Level {

    @Override
    public void configure() {

        //user input
        height = 9;
        width = 9;
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
        drawLineOfWalls(new Position(1, 1), new Position(7, 1));
        drawLineOfWalls(new Position(1, 5), new Position(7, 5));

        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 5));
        drawLineOfWalls(new Position(7, 1), new Position(7, 5));

        fillWithStandards(new Position(2, 2), new Position(6, 4));

        tiles.put(new Position(3, 3), new Tile(TileType.WALL));
        tiles.put(new Position(5, 3), new Tile(TileType.WALL));

    }
    private void configureGoal() {
        setGoal(new Position(4, 3));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(3, 2), Upgrades.ONE);
        upgrades.put(new Position(5, 2), Upgrades.TWO);
        upgrades.put(new Position(3, 4), Upgrades.TWO);
        upgrades.put(new Position(5, 4), Upgrades.ONE);

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
