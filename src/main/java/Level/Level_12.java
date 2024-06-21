package Level;

import model.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_12 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 12;
        height = 11;
        width = 11;
        startingPosition = new Position(3, 4);
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
        drawLineOfWalls(new Position(2, 1), new Position(6, 1));
        drawLineOfWalls(new Position(2, 3), new Position(4, 3));
        drawLineOfWalls(new Position(4, 4), new Position(9, 4));
        drawLineOfWalls(new Position(1, 8), new Position(9, 8));

        //Vertikal
        drawLineOfWalls(new Position(1, 4), new Position(1, 8));
        drawLineOfWalls(new Position(2, 1), new Position(2, 4));
        drawLineOfWalls(new Position(4, 1), new Position(4, 4));
        drawLineOfWalls(new Position(5, 4), new Position(5, 8));
        drawLineOfWalls(new Position(6, 1), new Position(6, 4));
        drawLineOfWalls(new Position(9, 4), new Position(9, 8));


        fillWithStandards(new Position(2, 2), new Position(5, 7));
        fillWithStandards(new Position(6, 5), new Position(8, 8));



    }
    private void configureGoal() {
        setGoal(new Position(5, 3));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 6), Upgrades.TWO);
        upgrades.put(new Position(3, 6), Upgrades.NONE);
        upgrades.put(new Position(4, 6), Upgrades.TWO);
        upgrades.put(new Position(6, 6), Upgrades.TWO);
        upgrades.put(new Position(7, 6), Upgrades.NONE);
        upgrades.put(new Position(8, 6), Upgrades.TWO);


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
