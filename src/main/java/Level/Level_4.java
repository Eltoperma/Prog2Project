package Level;

import GameLogic.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_4 extends Level{
    @Override
    public void configure() {

        //user input
        ID = 4;
        height = 10;
        width = 10;
        startingPosition = new Position(5, 4);
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
        drawLineOfWalls(new Position(2, 1), new Position(7, 1));
        drawLineOfWalls(new Position(7, 3), new Position(9, 3));
        drawLineOfWalls(new Position(1, 5), new Position(3, 5));
        drawLineOfWalls(new Position(3, 7), new Position(8, 7));

        //Vertikal
        drawLineOfWalls(new Position(1, 3), new Position(1, 5));
        drawLineOfWalls(new Position(2, 1), new Position(2, 3));
        drawLineOfWalls(new Position(3, 4), new Position(3, 7));
        drawLineOfWalls(new Position(7, 1), new Position(7, 4));
        drawLineOfWalls(new Position(8, 5), new Position(8, 7));
        drawLineOfWalls(new Position(9, 3), new Position(9, 5));

        fillWithStandards(new Position(2, 2), new Position(7, 4));
        fillWithStandards(new Position(3, 4), new Position(8, 6));


    }
    private void configureGoal() {
        setGoal(new Position(8, 4));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(3, 3), Upgrades.TWO);
        upgrades.put(new Position(4, 6), Upgrades.TWO);
        upgrades.put(new Position(6, 2), Upgrades.TWO);
        upgrades.put(new Position(7, 5), Upgrades.TWO);

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
