package Level;

import AssetManager.Tile;
import AssetManager.TileType;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;

public class Level_1 extends Level{

    @Override
    public void configure(){
        height = 10;
        width = 10;

        configureTiles();
        configureUpgrades();
        configureRest();
    }

    private void configureTiles() {
        tiles.put(new Position(1, 1), new Tile(TileType.WALL));
        // ...
    }

   private void configureUpgrades(){
        upgrades.put(new Position(1,2), Upgrades.ONE);
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
