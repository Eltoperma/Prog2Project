package GameLogic;

import AssetManager.TileType;

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
        }
    }

    public void moveUp(Direction direction){
        int movingFactor = 1;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
        }
        adjustPosition(0, -movingFactor, direction);
    }

    public void moveLeft(Direction direction){
        int movingFactor = 1;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
        }
        adjustPosition(-movingFactor, 0, direction);
    }

    public void moveRight(Direction direction){
        int movingFactor = 1;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
        }
        adjustPosition(movingFactor, 0, direction);
    }

    public void moveDown(Direction direction){
        int movingFactor = 1;
        switch(playerUpgrades.upUpgrade){
            case TWO -> movingFactor = 2;
            case THREE -> movingFactor = 3;
        }
        adjustPosition(0, movingFactor, direction);
    }

    public void adjustPosition(int x, int y, Direction direction){
        Position landOnPosition = new Position(playerPosition.x + x, playerPosition.y + y);
        TileType landOnTile = Game.currentlevel.tiles.get(landOnPosition).getTileType();

        if(landOnTile == TileType.DARK && landOnTile == TileType.LIGHT){
             if(Game.currentlevel.upgrades.get(landOnPosition) == null || (Game.currentlevel.upgrades.get(landOnPosition) != null && !hasPlayerUpgradeOnDirection(direction)){
                 setPlayerPosition(landOnPosition);
                 return;
            }
        }
        throw new RuntimeException("Dort kann man sich nicht hinbewegen!");
    }
}
