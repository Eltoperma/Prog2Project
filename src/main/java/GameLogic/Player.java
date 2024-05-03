package GameLogic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Player {
    private Position playerPosition;
    private Upgrade playerUpgrades;
    public Player(Position position, Upgrade upgrades){
        playerPosition = position;
        playerUpgrades = upgrades;
    }
    public boolean hasPlayerUpgradeOnDirection(Direction direction){
        boolean state = false;
        switch (direction){
            case UP -> {
               if(playerUpgrades.upUpgrade == Upgrades.NONE) state = true;
            }
            case DOWN -> {
                if(playerUpgrades.downUpgrade == Upgrades.NONE)state = true;
            }
            case RIGHT -> {
                if(playerUpgrades.rightUpgrade == Upgrades.NONE) state = true;
            }
            case LEFT -> {
                if(playerUpgrades.leftUpgrade == Upgrades.NONE) state = true;
            }
        }
        return state;
    }
    public void setPlayerUpgradeOnDirection(Direction direction, Upgrades upgrade){
        if(!hasPlayerUpgradeOnDirection(direction)){
            switch (direction){
                case UP -> playerUpgrades.upUpgrade = upgrade;
                case DOWN -> playerUpgrades.downUpgrade = upgrade;
                case RIGHT -> playerUpgrades.rightUpgrade = upgrade;
                case LEFT -> playerUpgrades.leftUpgrade = upgrade;
            }
        }
    }
    public void setPlayerPosition(Position pos){
        playerPosition = pos;
    }
    public Position getPlayerPosition(){
        return playerPosition;
    }
    public Image getPlayerIMG(){
        return new ImageIcon("src/assets/player/player.png").getImage();
    }
    public Image getUpgradeIMG(Upgrades upgrade) {
        ImageIcon img;
        switch (upgrade) {
            case ONE -> img = new ImageIcon("src/assets/player/upgrades/move1.png");
            case TWO -> img = new ImageIcon("src/assets/player/upgrades/move2.png");
            case THREE -> img = new ImageIcon("src/assets/player/upgrades/move3.png");
            case NONE -> img = new ImageIcon("src/assets/player/upgrades/empty.png");
            case PLACEHOLDER -> img = new ImageIcon("src/assets/player/upgrades/placeholder.png");
            default -> throw new RuntimeException("Error getting upgrade");
        }
        return img.getImage();
    }

}
