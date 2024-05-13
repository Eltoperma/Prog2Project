package GameLogic;

import DrawLogic.TileType;

import javax.swing.*;
import java.awt.*;



public class Player {
    private Position playerPosition;

    public Upgrade getPlayerUpgrades() {
        return playerUpgrades;
    }

    private Upgrade playerUpgrades;

    public Player(Position position, Upgrade upgrades) {
        playerPosition = position;
        playerUpgrades = upgrades;
    }

    public boolean hasPlayerUpgradeOnDirection(Direction direction) {
        boolean state = false;
        switch (direction) {
            case UP -> {
                if (playerUpgrades.upUpgrade != Upgrades.NONE) state = true;
            }
            case DOWN -> {
                if (playerUpgrades.downUpgrade != Upgrades.NONE) state = true;
            }
            case RIGHT -> {
                if (playerUpgrades.rightUpgrade != Upgrades.NONE) state = true;
            }
            case LEFT -> {
                if (playerUpgrades.leftUpgrade != Upgrades.NONE) state = true;
            }
        }
        return state;
    }

    public boolean hasAllUpgrades() {
        return playerUpgrades.downUpgrade != Upgrades.NONE && playerUpgrades.upUpgrade != Upgrades.NONE && playerUpgrades.leftUpgrade != Upgrades.NONE && playerUpgrades.rightUpgrade != Upgrades.NONE;
    }

    public void setPlayerUpgradeOnDirection(Direction direction, Upgrades upgrade) {
        if (!hasPlayerUpgradeOnDirection(direction)) {
            switch (direction) {
                case UP -> playerUpgrades.upUpgrade = upgrade;
                case DOWN -> playerUpgrades.downUpgrade = upgrade;
                case RIGHT -> playerUpgrades.rightUpgrade = upgrade;
                case LEFT -> playerUpgrades.leftUpgrade = upgrade;
                default -> {
                    throw new RuntimeException("Fehler bei Upgrade!");
                }
            }
        }
    }

    public void setPlayerPosition(Position pos) {
        playerPosition = pos;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Image getPlayerIMG(){
        return new ImageIcon("src/assets/player/player_shadow.png").getImage();
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

    public void move(Direction direction){
        switch (direction){
            case UP -> moveUp(direction);
            case DOWN -> moveDown(direction);
            case LEFT -> moveLeft(direction);
            case RIGHT -> moveRight(direction);
            default -> throw new RuntimeException("Fehler bei Move!");
        }
    }

    private void moveUp(Direction direction) {
        int movingFactor;
        System.out.println("UpUpgrade: " + playerUpgrades.upUpgrade);
        switch (playerUpgrades.upUpgrade) {
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(0, -movingFactor, direction);
    }

    private void moveLeft(Direction direction) {
        int movingFactor;
        switch (playerUpgrades.leftUpgrade) {
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(-movingFactor, 0, direction);
    }

    private void moveRight(Direction direction) {
        int movingFactor;
        switch (playerUpgrades.rightUpgrade) {
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(movingFactor, 0, direction);
    }

    private void moveDown(Direction direction) {
        int movingFactor;
        switch (playerUpgrades.downUpgrade) {
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(0, movingFactor, direction);
    }

    public void adjustPosition(int xDiff, int yDiff, Direction direction) {
        Position landOnPosition = new Position((playerPosition.x + xDiff), (playerPosition.y + yDiff));

        System.out.println("IsPlayable: landOnPosition: " + landOnPosition.x + " " + landOnPosition.y);
        if (Game.currentlevel.isPlayable(landOnPosition)) {
            //landing Position is empty

            Game.currentlevel.test();

            if (Game.currentlevel.upgrades.get(landOnPosition) == null && !Game.currentlevel.tiles.get(landOnPosition).isGoal()) {
                System.out.println("noUpgrade");
                setPlayerPosition(landOnPosition);
                return;
            }
            //landing Position contains upgrade and is collectable
            if (Game.currentlevel.upgrades.get(landOnPosition) != null && !hasPlayerUpgradeOnDirection(direction)) {
                System.out.println("collectUpgrade");
                collectUpgrade(direction, landOnPosition);
                setPlayerPosition(landOnPosition);
                return;
            }
            //landing position is goal
            if (Game.currentlevel.tiles.get(landOnPosition).isGoal()) {
                System.out.println("finish?");
                if(canFinish()){
                    System.out.println("finish!");
                    Game.getPlayer().setPlayerPosition(landOnPosition);
                    Game.finish();
                    return;
                };
                System.out.println("goal but not sufficient upgrades");
                setPlayerPosition(landOnPosition);
                return;
            }
            throw new RuntimeException("Dort müsste man sich hinbewegen können, geht aber nicht. Vllt ein Upgrade.");

        }
        throw new RuntimeException("Dort kann man sich nicht hinbewegen!");
    }

    private void collectUpgrade(Direction direction, Position pos) {
        switch (direction) {
            case UP -> playerUpgrades.upUpgrade = Game.currentlevel.upgrades.get(pos);
            case LEFT -> playerUpgrades.leftUpgrade = Game.currentlevel.upgrades.get(pos);
            case RIGHT -> playerUpgrades.rightUpgrade = Game.currentlevel.upgrades.get(pos);
            case DOWN -> playerUpgrades.downUpgrade = Game.currentlevel.upgrades.get(pos);
            default -> throw new RuntimeException("Invalides Upgrade!");
        }
        Game.currentlevel.upgrades.remove(pos);
    }

    public boolean canFinish(){
        return hasAllUpgrades();
    }
}
