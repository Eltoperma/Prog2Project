package Level;

import GameLogic.Tile;
import GameLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;

import java.util.ArrayList;
import java.util.HashMap;

public class Level_1 extends Level {

    @Override
    public void configure() {

        //user input
        ID = 1;
        height = 8;
        width = 8;
        startingPosition = new Position(4, 5);
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
        drawLineOfWalls(new Position(1, 5), new Position(3, 5));
        drawLineOfWalls(new Position(3, 6), new Position(5, 6));

        //Vertikal
        drawLineOfWalls(new Position(1, 1), new Position(1, 5));
        drawLineOfWalls(new Position(5, 4), new Position(5, 6));
        drawLineOfWalls(new Position(6, 2), new Position(6, 4));

        fillWithStandards(new Position(2, 2), new Position(4, 5));

        tiles.put(new Position(5, 2), new Tile(TileType.WALL));

        tiles.put(new Position(5, 3), new Tile(TileType.STANDARD));

    }
    private void configureGoal() {
        setGoal(new Position(5, 3));
    }

    private void configureUpgrades() {
        //user input
        upgrades = new HashMap<>();
        upgrades.put(new Position(2, 2), Upgrades.ONE);
        upgrades.put(new Position(4, 2), Upgrades.ONE);
        upgrades.put(new Position(2, 4), Upgrades.ONE);
        upgrades.put(new Position(4, 4), Upgrades.ONE);

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
