package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_7 extends Level {

    @Override
    public void configure() {

        //user input
        height = 10;
        width = 10;
        startingPosition = new Position(2, 2);
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
        drawLineOfWalls(new Position(3, 6), new Position(6, 6));
        drawLineOfWalls(new Position(1, 7), new Position(3, 7));
        drawLineOfWalls(new Position(6, 7), new Position(8, 7));

        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 7));
        drawLineOfWalls(new Position(7, 1), new Position(7, 4));
        drawLineOfWalls(new Position(8, 4), new Position(8, 7));

        fillWithStandards(new Position(2, 2), new Position(7, 6));

        tiles.put(new Position(3, 2), new Tile(TileType.WALL));
        tiles.put(new Position(5, 2), new Tile(TileType.WALL));
        tiles.put(new Position(3, 4), new Tile(TileType.WALL));
        tiles.put(new Position(5, 4), new Tile(TileType.WALL));


    }
    private void configureGoal() {
        setGoal(new Position(7, 6));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(4, 2), Upgrades.TWO);
        upgrades.put(new Position(3, 3), Upgrades.ONE);
        upgrades.put(new Position(5, 3), Upgrades.ONE);
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
