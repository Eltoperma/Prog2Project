package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_16 extends Level {

    @Override
    public void configure() {

        //user input
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
        drawLineOfWalls(new Position(1, 1), new Position(5, 1));
        drawLineOfWalls(new Position(1, 7), new Position(5, 7));
        drawLineOfWalls(new Position(2, 9), new Position(4, 9));

        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 7));
        drawLineOfWalls(new Position(2, 7), new Position(2, 9));
        drawLineOfWalls(new Position(4, 7), new Position(4, 9));
        drawLineOfWalls(new Position(5, 1), new Position(5, 7));


        fillWithStandards(new Position(2, 2), new Position(4, 8));

        tiles.put(new Position(3, 5), new Tile(TileType.WALL));

    }
    private void configureGoal() {
        setGoal(new Position(3, 8));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(3, 2), Upgrades.TWO);
        upgrades.put(new Position(3, 3), Upgrades.ONE);
        upgrades.put(new Position(3, 6), Upgrades.PLACEHOLDER);

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
