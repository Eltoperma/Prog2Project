package Level;

import model.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_14 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 14;
        height = 12;
        width = 12;
        startingPosition = new Position(2, 5);
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
        drawLineOfWalls(new Position(8, 1), new Position(10, 1));
        drawLineOfWalls(new Position(1, 2), new Position(8, 2));
        drawLineOfWalls(new Position(1, 4), new Position(3, 4));
        drawLineOfWalls(new Position(5, 4), new Position(8, 4));
        drawLineOfWalls(new Position(8, 5), new Position(10, 5));
        drawLineOfWalls(new Position(6, 7), new Position(10, 7));
        drawLineOfWalls(new Position(2, 9), new Position(7, 9));

        //Vertikal
        drawLineOfWalls(new Position(1, 2), new Position(1, 7));
        drawLineOfWalls(new Position(2, 7), new Position(2, 9));
        drawLineOfWalls(new Position(3, 2), new Position(3, 4));
        drawLineOfWalls(new Position(4, 7), new Position(4, 9));
        drawLineOfWalls(new Position(7, 7), new Position(7, 9));
        drawLineOfWalls(new Position(10, 1), new Position(10, 7));

        fillWithStandards(new Position(2, 2), new Position(9, 6));
        fillWithStandards(new Position(3, 7), new Position(6, 8));

        tiles.put(new Position(5, 5), new Tile(TileType.WALL));


    }
    private void configureGoal() {
        setGoal(new Position(2, 3));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 6), Upgrades.ONE);
        upgrades.put(new Position(3, 6), Upgrades.ONE);
        upgrades.put(new Position(4, 6), Upgrades.NONE);
        upgrades.put(new Position(5, 6), Upgrades.TWO);
        upgrades.put(new Position(3, 7), Upgrades.NONE);
        upgrades.put(new Position(5, 7), Upgrades.NONE);
        upgrades.put(new Position(5, 8), Upgrades.TWO);

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
