package GameLogic;

import javax.swing.*;
import java.awt.*;

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
    public Image getUpgradeIMG(Upgrades up){
        switch(up){
            case ONE -> {return new ImageIcon("src/assets/player/upgrades/move1.png").getImage();}
            case TWO -> {
                return new ImageIcon("src/assets/player/upgrades/move2.png").getImage();
            }
            case THREE -> {return new ImageIcon("src/assets/player/upgrades/move3.png").getImage();}
            case NONE -> {return new ImageIcon("src/assets/player/upgrades/empty.png").getImage();}
            case PLACEHOLDER -> {return new ImageIcon("src/assets/player/upgrades/placeholder.png").getImage();}
            default -> throw new RuntimeException();
        }
    }
}
