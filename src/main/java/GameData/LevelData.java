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

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public String getBestUserName() {
        return bestUserName;
    }

    public void setBestUserName(String bestUserName) {
        this.bestUserName = bestUserName;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        LevelData levelData = (LevelData) o;
//        return levelId == levelData.levelId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(levelId);
//    }
}
