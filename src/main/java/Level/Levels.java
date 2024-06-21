package Level;

import java.util.ArrayList;

public class Levels {
    public static ArrayList<Level> levelList;
    public Levels(){
        levelList = new ArrayList<>();
        levelList.add(new Level_1());
        levelList.add(new Level_2());
        levelList.add(new Level_3());
        levelList.add(new Level_4());
        levelList.add(new Level_5());
        levelList.add(new Level_6());
        levelList.add(new Level_7());
        levelList.add(new Level_8());
        levelList.add(new Level_9());
        levelList.add(new Level_10());
        levelList.add(new Level_11());
        levelList.add(new Level_12());
        levelList.add(new Level_13());
        levelList.add(new Level_14());
        levelList.add(new Level_15());
        levelList.add(new Level_16());
        levelList.add(new Level_17());
        levelList.add(new Level_18());
        levelList.add(new Level_19());
        levelList.add(new Level_20());
        levelList.add(new Level_Test());

       //levels.add(...)
    }
    public static Level getLevel(Integer level){
        if(level >= levelList.size() || level < 0) throw new RuntimeException("Out of Range exception");
        return levelList.get(level);
    }

//    public static void changeToCustomLevel(String title, Map<Position, Tile> tiles, Map<Position, Upgrades> upgrades, Position startingPosition, ArrayList<Position> finishPositions, int height, int width){
//        levelList.clear();
//        levelList.add(new LoadedLevel(title, tiles, upgrades, startingPosition, finishPositions, height, width));
//    }


}