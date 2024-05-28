package GameLogic;

import DrawLogic.TileType;

import javax.swing.*;
import java.awt.*;



public class Player {
    private Position playerPosition;
    private Game game;
    private boolean hasPlaceholder = false;
    private Direction placeholderDirection = null;


    public Upgrade getPlayerUpgrades() {
        return playerUpgrades;
    }

    private Upgrade playerUpgrades;

    public Player(Position position, Upgrade upgrades, Game game) {
        playerPosition = position;
        playerUpgrades = upgrades;
        this.game = game;
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
        if (game.currentlevel.isPlayable(landOnPosition)) {
            //landing Position is empty

            game.currentlevel.test();

            if (game.currentlevel.upgrades.get(landOnPosition) == null && !game.currentlevel.tiles.get(landOnPosition).isGoal()) {
                System.out.println("no Upgrade");
                setPlayerPosition(landOnPosition);
                game.updateMoves();
                return;
            }
            //landing Position contains upgrade and is collectable
            if (game.currentlevel.upgrades.get(landOnPosition) != null && (!hasPlayerUpgradeOnDirection(direction) || hasPlaceholder) && !game.currentlevel.upgrades.get(landOnPosition).equals(Upgrades.NONE)) {
                System.out.println("collect Upgrade");
                collectUpgrade(direction, landOnPosition);
                setPlayerPosition(landOnPosition);
                game.updateMoves();
                return;
            }

            //landing Position contains upgrade and is movable
            Position upgradePushPosition = calcPositionByPosAndDir(landOnPosition, direction);
            System.out.println("pushPosition: posx: " + upgradePushPosition.x + " posy: " + upgradePushPosition.y);
            System.out.println("landOnPosition: posx: " + landOnPosition.x + " posy: " + landOnPosition.y);

            if (game.currentlevel.upgrades.get(landOnPosition) != null && (hasPlayerUpgradeOnDirection(direction) || ((!hasPlayerUpgradeOnDirection(direction)) && getUpgradeByDirection(direction).equals(Upgrades.NONE))) && !game.currentlevel.tiles.get(upgradePushPosition).getTileType().equals(TileType.WALL)
                    && !hasPlaceholder && game.currentlevel.upgrades.get(upgradePushPosition) == null) {
                System.out.println("move Upgrade");
                Upgrades removedUpgrade = game.currentlevel.upgrades.remove(landOnPosition);
                game.currentlevel.upgrades.put(upgradePushPosition, removedUpgrade);
                setPlayerPosition(landOnPosition);
                game.updateMoves();
                return;
            }
            //landing Position contains none-upgrade and is collectable
            if (game.currentlevel.upgrades.get(landOnPosition) != null && game.currentlevel.upgrades.get(landOnPosition).equals(Upgrades.NONE) && (hasPlayerUpgradeOnDirection(direction) || hasPlaceholder)) {
                System.out.println("collect Upgrade");
                collectUpgrade(direction, landOnPosition);
                setPlayerPosition(landOnPosition);
                game.updateMoves();
                return;
            }
            //landing position is goal
            if (game.currentlevel.tiles.get(landOnPosition).isGoal()) {
                System.out.println("finish?");
                game.updateMoves();
                if(canFinish()){
                    System.out.println("finish!");
                    game.getPlayer().setPlayerPosition(landOnPosition);
                    game.finish();
                    return;
                };
                System.out.println("goal but not sufficient upgrades");
                setPlayerPosition(landOnPosition);
                return;
            }
//            throw new RuntimeException("Dort müsste man sich hinbewegen können, geht aber nicht.");

        }
//        throw new RuntimeException("Dort kann man sich nicht hinbewegen!");
    }

    private void collectUpgrade(Direction direction, Position pos) {
        if(!hasPlaceholder) {
        if(game.currentlevel.upgrades.get(pos).equals(Upgrades.PLACEHOLDER)){
            hasPlaceholder = true;
            placeholderDirection = direction;
        }
            switch (direction) {
                case UP -> playerUpgrades.upUpgrade = game.currentlevel.upgrades.get(pos);
                case LEFT -> playerUpgrades.leftUpgrade = game.currentlevel.upgrades.get(pos);
                case RIGHT -> playerUpgrades.rightUpgrade = game.currentlevel.upgrades.get(pos);
                case DOWN -> playerUpgrades.downUpgrade = game.currentlevel.upgrades.get(pos);
                default -> throw new RuntimeException("Invalides Upgrade!");
            }
            game.currentlevel.upgrades.remove(pos);
        }
        else{
            switch (placeholderDirection) {
                case UP -> playerUpgrades.upUpgrade = game.currentlevel.upgrades.get(pos);
                case LEFT -> playerUpgrades.leftUpgrade = game.currentlevel.upgrades.get(pos);
                case RIGHT -> playerUpgrades.rightUpgrade = game.currentlevel.upgrades.get(pos);
                case DOWN -> playerUpgrades.downUpgrade = game.currentlevel.upgrades.get(pos);
                default -> throw new RuntimeException("Invalides Upgrade!");
            }
            hasPlaceholder = false;
            placeholderDirection = null;
            game.currentlevel.upgrades.remove(pos);

        }
    }

    public boolean canFinish(){
        return hasAllUpgrades() || game.allUpgradesCollected();
    }

    public Position calcPositionByPosAndDir(Position pos, Direction dir){
        System.out.println("calcMoveUpgrade: posx = " + pos.x + " posy= " + pos.y + ", dir = " + dir);
        Position calculatedPos = new Position(pos.x, pos.y);
        switch (dir){
            case UP -> {
                calculatedPos.y--;
                return calculatedPos;
            }
            case DOWN -> {
                calculatedPos.y++;
                return calculatedPos;
            }
            case RIGHT -> {
                calculatedPos.x++;
                return calculatedPos;
            }
            case LEFT -> {
                calculatedPos.x--;
                return calculatedPos;
            }
            default -> {
                throw new RuntimeException("Da ist etwas schiefgelaufen beim Positionsprüfen!!!");
            }
        }
    }

    public Upgrades getUpgradeByDirection(Direction direction){
        switch(direction){
            case UP -> { return playerUpgrades.upUpgrade; }
            case DOWN -> { return playerUpgrades.downUpgrade; }
            case LEFT -> { return playerUpgrades.leftUpgrade; }
            case RIGHT -> { return playerUpgrades.rightUpgrade; }
            default -> throw new RuntimeException("Invalides Upgrade!");
        }
    }
}
