package Level;

import DrawLogic.Tile;
import DrawLogic.TileType;
import GameLogic.Position;
import GameLogic.Upgrades;


import java.util.Map;

public abstract class Level {


    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;
    public int height;                              //inkl. unspielbarer Fläche
    public int width;                               //inkl. unspielbarer Fläche
    public Position startingPosition;
    public Position finishPosition;

    public void configure() {
    }
    public void addCheckPattern(){
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                // Überprüfen, ob die Position in der Karte existiert
                Position position = new Position(x, y);
                if(tiles.containsKey(position) && tiles.get(position).getTileType().equals(TileType.STANDARD)){
                    if(y % 2 == 0) {
                        if (x % 2 == 0) tiles.put(position, new Tile(TileType.DARK));
                        else tiles.put(position, new Tile(TileType.LIGHT));
                    }
                    else{
                        if (x % 2 != 0) tiles.put(position, new Tile(TileType.DARK));
                        else tiles.put(position, new Tile(TileType.LIGHT));
                    }
                }
            }
        }
    }


    public void drawLineOfWalls(Position start, Position end){
        if(start.x <= end.x && start.y <= end.y) {
            if (start.x != end.x) {
                for (int i = start.x; i <= end.x; i++) {
                    tiles.put(new Position(i, start.y), new Tile(TileType.WALL));
                }
            }
            else if (start.y != end.y) {
                for (int i = start.y; i <= end.y; i++) {
                    tiles.put(new Position(start.x, i), new Tile(TileType.WALL));
                }
            }
        }
        else{
            throw new RuntimeException("Du dummer HS, das war falsch herum >:(");
        }
    }

    public void drawLineOfStandards(Position start, Position end){
        if(start.x <= end.x && start.y <= end.y) {
            if (start.x != end.x) {
                for (int i = start.x; i <= end.x; i++) {
                    tiles.put(new Position(i, start.y), new Tile(TileType.STANDARD));
                }
            }
            else if (start.y != end.y) {
                for (int i = start.y; i <= end.y; i++) {
                    tiles.put(new Position(start.x, i), new Tile(TileType.STANDARD));
                }
            }
        }
        else{
            throw new RuntimeException("Du dummer HS, das war falsch herum >:(");
        }
    }

    public void fillWithStandards(Position start, Position end){
        for(int y = start.x; y <= end.y; y++){
            for(int x = start.x; x <= end.x; x++) {
                tiles.computeIfAbsent(new Position(x, y), k -> new Tile(TileType.STANDARD));
            }
        }
    }

    public boolean isPlayable(Position pos){
        Position newPos = new Position(pos.x, pos.y);
        if(tiles.containsKey(newPos)){
            return tiles.get(newPos).getTileType().equals(TileType.DARK) || tiles.get(newPos).getTileType().equals(TileType.LIGHT) || tiles.get(newPos).getTileType().equals(TileType.GOAL);
        }
        throw new RuntimeException("Diese Position existiert nicht!?");
    }

    public void test(){
        tiles.forEach((position, tile) -> {
            System.out.println("Position : " + position.x + " " + position.y + " Tile: " + tile.getTileType());
        });
    }

}
