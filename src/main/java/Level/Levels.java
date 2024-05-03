package Level;

import java.util.ArrayList;

public class Levels {
    ArrayList<Level> levels;
    public Levels(){
       //levels.add(...)
    }
    public Level getLevel(Integer level){
        if(level >= levels.size() || level < 0) throw new RuntimeException("Out of Range exeption");
        return levels.get(level);
    }


}
