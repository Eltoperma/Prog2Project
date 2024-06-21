package GameData;

import Level.Level;

public class LevelData {

    private int levelId;
    private int highscore;
    private String bestUserName;
    private Level level;

    public LevelData(int levelId, int highscore, String bestUserName) {
        this.levelId = levelId;
        this.highscore = highscore;
        this.bestUserName = bestUserName;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getHighscore() {
        return highscore;
    }

    public String getBestUserName() {
        return bestUserName;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}
