package GameLogic;
import java.util.TimerTask;

class GameTimerTask extends TimerTask {
    private int seconds = 0;
    private Game game;

    public GameTimerTask(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        seconds++;
        System.out.println("Sekunden vergangen: " + seconds);
        game.updateTimer();
        // Hier kannst du auch andere Spiel-logik einfügen, die jede Sekunde ausgeführt werden soll.
    }

    public int getSeconds() {
        return seconds;
    }
}