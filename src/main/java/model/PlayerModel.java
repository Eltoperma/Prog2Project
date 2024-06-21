package model;

import GameLogic.Direction;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class PlayerModel implements Serializable {
    private Position playerPosition;
    private boolean hasPlaceholder = false;
    private Upgrade playerUpgrades;
    private Direction placeholderDirection = null;

    public PlayerModel(Position pos){
        playerUpgrades = new Upgrade();
        playerPosition = pos;

    }
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

    public boolean hasAllUpgrades() {
        return playerUpgrades.downUpgrade != Upgrades.NONE && playerUpgrades.upUpgrade != Upgrades.NONE && playerUpgrades.leftUpgrade != Upgrades.NONE && playerUpgrades.rightUpgrade != Upgrades.NONE;
    }

    public Image getPlayerIMG(){
        return new ImageIcon("src/assets/player/player_shadow.png").getImage();
    }


    public Direction getPlaceholderDirection() {
        return placeholderDirection;
    }

    public void setPlaceholderDirection(Direction placeholderDirection) {
        this.placeholderDirection = placeholderDirection;
    }
    public Image getUpgradeIMG(Upgrades up) {
        switch (up) {
            case ONE -> {
                return new ImageIcon("src/assets/player/upgrades/move1.png").getImage();
            }
            case TWO -> {
                return new ImageIcon("src/assets/player/upgrades/move2.png").getImage();
            }
            case THREE -> {
                return new ImageIcon("src/assets/player/upgrades/move3.png").getImage();
            }
            case NONE -> {
                return new ImageIcon("src/assets/player/upgrades/empty.png").getImage();
            }
            case PLACEHOLDER -> {
                return new ImageIcon("src/assets/player/upgrades/placeholder.png").getImage();
            }
            default -> throw new RuntimeException();
        }
    }
}
