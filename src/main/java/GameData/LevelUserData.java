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

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
