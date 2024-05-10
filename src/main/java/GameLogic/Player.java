package GameLogic;

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
                if(playerUpgrades.downUpgrade == Upgrades.NONE) state = true;
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
                default -> {
                    throw new RuntimeException("Fehler bei Upgrade!");
                }
            }
        }
    }
    public void setPlayerPosition(Position pos){
        playerPosition = pos;
    }
    public Position getPlayerPosition(){
        return playerPosition;
    }

    public void move(Direction direction){
        switch (direction){
            case UP -> moveUp(direction);
            case DOWN -> moveDown(direction);
            case LEFT -> moveLeft(direction);
            case RIGHT -> moveRight(direction);
            default -> {
                throw new RuntimeException("Fehler bei Move!");
            }
        }
    }

    public void moveUp(Direction direction){
        int movingFactor;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(0, -movingFactor, direction);
    }

    public void moveLeft(Direction direction){
        int movingFactor;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(-movingFactor, 0, direction);
    }

    public void moveRight(Direction direction){
        int movingFactor;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(movingFactor, 0, direction);
    }

    public void moveDown(Direction direction){
        int movingFactor;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
            default -> movingFactor = 1;
        }
        adjustPosition(0, movingFactor, direction);
    }

    public void adjustPosition(int x, int y, Direction direction){
        Position landOnPosition = new Position(playerPosition.x + x, playerPosition.y + y);

        if(Game.currentlevel.tileIsPlayable(landOnPosition)){
            //landing Position is empty
             if(Game.currentlevel.upgrades.get(landOnPosition) == null){
                 setPlayerPosition(landOnPosition);
                 return;
            }
             //landing Position contains upgrade and is collectable
             if(Game.currentlevel.upgrades.get(landOnPosition) != null && !hasPlayerUpgradeOnDirection(direction)){
                collectUpgrade(direction, landOnPosition);
             }
        }
        throw new RuntimeException("Dort kann man sich nicht hinbewegen!");
    }

    private void collectUpgrade(Direction direction, Position pos) {
        switch(direction){
            case UP -> playerUpgrades.upUpgrade = Game.currentlevel.upgrades.get(pos);
            case LEFT -> playerUpgrades.leftUpgrade = Game.currentlevel.upgrades.get(pos);
            case RIGHT -> playerUpgrades.rightUpgrade = Game.currentlevel.upgrades.get(pos);
            case DOWN -> playerUpgrades.downUpgrade = Game.currentlevel.upgrades.get(pos);
            default -> throw new RuntimeException("Invalides Upgrade!");
        }
    }
}
