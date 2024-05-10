package Level;

import AssetManager.Tile;
import AssetManager.TileType;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;

public class Level_1 extends Level{

    @Override
    public void configure(){

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
//        tiles.put(new Position(1, 1), new Tile(TileType.WALL));
//        tiles.put(new Position(1, 2), new Tile(TileType.STANDARD));
//        drawLineOfWalls(new Position(1, 1), new Position(1, 8));

        //Start und Ziel tile festlegen
        //
        drawLineOfWalls(new Position(1, 1), new Position(6, 1));

        drawLineOfWalls(new Position(1, 1), new Position(1, 5));

        tiles.put(new Position(2, 2), new Tile(TileType.STANDARD));

        tiles.put(new Position(3, 2), new Tile(TileType.STANDARD));

        tiles.put(new Position(4, 2), new Tile(TileType.STANDARD));

        tiles.put(new Position(5, 2), new Tile(TileType.WALL));

        tiles.put(new Position(6, 2), new Tile(TileType.WALL));

        tiles.put(new Position(2, 3), new Tile(TileType.STANDARD));

        tiles.put(new Position(3, 3), new Tile(TileType.STANDARD));

        tiles.put(new Position(4, 3), new Tile(TileType.STANDARD));

        tiles.put(new Position(5, 3), new Tile(TileType.STANDARD));

        tiles.put(new Position(6, 3), new Tile(TileType.WALL));

        tiles.put(new Position(2, 4), new Tile(TileType.STANDARD));

        tiles.put(new Position(3, 4), new Tile(TileType.STANDARD));

        tiles.put(new Position(4, 4), new Tile(TileType.STANDARD));

        tiles.put(new Position(5, 4), new Tile(TileType.WALL));

        tiles.put(new Position(6, 4), new Tile(TileType.WALL));

        drawLineOfWalls(new Position(1, 5), new Position(3, 5));

        tiles.put(new Position(4, 5), new Tile(TileType.STANDARD));

        tiles.put(new Position(5, 5), new Tile(TileType.WALL));

        drawLineOfWalls(new Position(3, 6), new Position(5, 6));
    }

   private void configureUpgrades(){
       //user input
        upgrades.put(new Position(1,2), Upgrades.ONE);
        //
   }

    private void configureRest(){
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                if(tiles.get(new Position(x, y)) == null){
                    tiles.put(new Position(x, y), new Tile(TileType.NOTHING));
                }
            }
        }
    }
}
