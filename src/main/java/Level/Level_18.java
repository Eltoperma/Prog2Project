package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_18 extends Level {

    @Override
    public void configure() {

        //user input
        height = 9;
        width = 9;
        startingPosition = new Position(3, 5);
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
        drawLineOfWalls(new Position(1, 3), new Position(6, 3));
        drawLineOfWalls(new Position(1, 7), new Position(5, 7));


        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 7));
        drawLineOfWalls(new Position(3, 1), new Position(3, 3));
        drawLineOfWalls(new Position(5, 1), new Position(5, 3));
        drawLineOfWalls(new Position(5, 5), new Position(5, 7));
        drawLineOfWalls(new Position(6, 3), new Position(6, 5));

        fillWithStandards(new Position(2, 2), new Position(5, 6));


    }
    private void configureGoal() {
        setGoal(new Position(5, 4));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 2), Upgrades.TWO);
        upgrades.put(new Position(4, 2), Upgrades.THREE);
        upgrades.put(new Position(2, 4), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(3, 4), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(4, 4), Upgrades.PLACEHOLDER);
        upgrades.put(new Position(2, 6), Upgrades.TWO);
        upgrades.put(new Position(4, 6), Upgrades.TWO);

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
