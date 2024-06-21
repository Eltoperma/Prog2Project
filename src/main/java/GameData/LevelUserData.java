package GameData;

public class LevelUserData {
    private int highScore;
    private boolean finished;

    public LevelUserData(boolean finished, int highScore){
        this.finished = finished;
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }

}
