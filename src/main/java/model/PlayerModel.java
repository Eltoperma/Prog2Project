package model;

import GameLogic.Position;
import GameLogic.Upgrade;

import java.io.Serializable;

public class PlayerModel implements Serializable {
    private Position playerPosition;
    private boolean hasPlaceholder = false;
    private Upgrade playerUpgrades;

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isHasPlaceholder() {
        return hasPlaceholder;
    }

    public void setHasPlaceholder(boolean hasPlaceholder) {
        this.hasPlaceholder = hasPlaceholder;
    }

    public Upgrade getPlayerUpgrades() {
        return playerUpgrades;
    }

    public void setPlayerUpgrades(Upgrade playerUpgrades) {
        this.playerUpgrades = playerUpgrades;
    }
}
