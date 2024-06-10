package GameData;

import Level.Level;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private Map<Integer, LevelUserData> levelData;

    public User(String username){
        this.username = username;
        this.levelData = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<Integer, LevelUserData> getLevelData() {
        return levelData;
    }

    public void setLevelData(Map<Integer, LevelUserData> levelData) {
        this.levelData = levelData;
    }
}
