package model;

import events.*;
import GameLogic.Direction;
import GameLogic.Player;
import Level.Level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Serializable {
    private PlayerModel player;
    private Level currentlevel;
    private  int movesCount;
    private int timeCount;
    private int currentScore;
    public boolean isFinished = false;

    public GameModel(){
        player = new PlayerModel();
    }
    private List<GameEventListener> listeners = new ArrayList<>();

    public void addGameEventListener(GameEventListener listener) {
        listeners.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(GameEvent event) {
        for (GameEventListener listener : listeners) {
            listener.handleGameEvent(event);
        }
    }

    public void movePlayer(Direction direction) {
        // Spiel-Logik zum Bewegen des Spielers
        System.out.println("Player moved " + direction);
        notifyListeners(new GameEvent(this, EventType.PLAYER_MOVED, direction));

    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player.setPlayerPosition(player.getPlayerPosition());
        this.player.setPlayerUpgrades(player.getPlayerUpgrades());
        this.player.setHasPlaceholder(player.isHasPlaceholder());
    }

    public Level getCurrentlevel() {
        return currentlevel;
    }

    public void setCurrentlevel(Level currentlevel) {
        this.currentlevel = currentlevel;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    public int getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<GameEventListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<GameEventListener> listeners) {
        this.listeners = listeners;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}
