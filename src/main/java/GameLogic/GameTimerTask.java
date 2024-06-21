package GameLogic;
import java.util.TimerTask;

class GameTimerTask extends TimerTask {
    private int seconds = 0;
    private GameHandler gameHandler;

    public GameTimerTask(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

    @Override
    public void run() {
        seconds++;
        System.out.println("Sekunden vergangen: " + seconds);
        gameHandler.updateTimer();
    }
}