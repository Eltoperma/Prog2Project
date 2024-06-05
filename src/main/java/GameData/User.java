package GameData;

import Level.Level;

import java.util.Map;

public class User {
    private String username;
    private String password;
    private Map<Level, LevelUserData> levelData;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Level, LevelUserData> getLevelData() {
        return levelData;
    }

    public void setLevelData(Map<Level, LevelUserData> levelData) {
        this.levelData = levelData;
    }
}
