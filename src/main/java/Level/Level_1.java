package Level;

import AssetManager.Tile;
import AssetManager.TileType;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;

public class Level_1 extends Level {

    @Override
    public void configure() {

        //user input
        height = 10;
        width = 10;
        //

        configureTiles();
        configureUpgrades();
        configureRest();
        addCheckPattern();
    }

    private void configureTiles() {

        //user input
        tiles.put(new Position(1, 1), new Tile(TileType.WALL));
        tiles.put(new Position(1, 2), new Tile(TileType.STANDARD));
        drawLineOfWalls(new Position(1, 1), new Position(1, 8));
        //
    }

    private void configureUpgrades() {
        //user input
        upgrades.put(new Position(1, 2), Upgrades.ONE);
        //
    }

    private void configureRest() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (tiles.get(new Position(x, y)) == null) {
                    tiles.put(new Position(x, y), new Tile(TileType.NOTHING));
                }
            }
        }
    }
}