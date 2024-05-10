package Level;

import java.util.ArrayList;

public class Levels {
    public static ArrayList<Level> levelList;
    public Levels(){
        levelList = new ArrayList<>();
        levelList.add(new Level_1());
        levelList.add(new Level_Test());
       //levels.add(...)
    }
    public static Level getLevel(Integer level){
        if(level >= levelList.size() || level < 0) throw new RuntimeException("Out of Range exception");
        return levelList.get(level);
    }


}
