package Level;

import GameLogic.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_13 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 13;
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
        drawLineOfWalls(new Position(1, 1), new Position(9, 1));
        drawLineOfWalls(new Position(1, 5), new Position(9, 5));
        drawLineOfWalls(new Position(1, 9), new Position(9, 9));

        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 9));
        drawLineOfWalls(new Position(5, 1), new Position(5, 9));
        drawLineOfWalls(new Position(9, 1), new Position(9, 9));

        fillWithStandards(new Position(2, 2), new Position(8, 8));


    }
    private void configureGoal() {
        setGoal(new Position(8, 8));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(3, 2), Upgrades.TWO);
        upgrades.put(new Position(6, 2), Upgrades.TWO);
        upgrades.put(new Position(2, 3), Upgrades.TWO);
        upgrades.put(new Position(8, 4), Upgrades.TWO);
        upgrades.put(new Position(2, 6), Upgrades.TWO);
        upgrades.put(new Position(7, 6), Upgrades.NONE);
        upgrades.put(new Position(6, 7), Upgrades.NONE);
        upgrades.put(new Position(4, 8), Upgrades.TWO);


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
