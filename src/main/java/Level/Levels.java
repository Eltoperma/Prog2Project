package Level;

import java.util.ArrayList;

public class Levels {
    public ArrayList<Level> levelList;
    public Levels(){
        levelList.add(new Level_1());
       //levels.add(...)
    }
    public Level getLevel(Integer level){
        if(level >= levelList.size() || level < 0) throw new RuntimeException("Out of Range exeption");
        return levelList.get(level);
    }


}
