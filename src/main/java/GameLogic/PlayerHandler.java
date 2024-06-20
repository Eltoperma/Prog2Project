package GameLogic;

import DrawLogic.TileType;
import Level.Levels;
import model.PlayerModel;


public class PlayerHandler {
    private PlayerModel playerModel;
    private GameHandler gameHandler;
    public PlayerHandler(GameHandler gameHandler){
        this.playerModel = gameHandler.getGameModel().getPlayerModel();
        this.gameHandler = gameHandler;
    }

    public PlayerHandler(Position position, Upgrade upgrades, GameHandler gameHandler) {
        this.playerModel = new PlayerModel(position);
        this.playerModel.setPlayerUpgrades(upgrades);
        this.gameHandler = gameHandler;
    }

    public Upgrade getPlayerUpgrades() {
        return playerModel.getPlayerUpgrades();
    }

    public boolean hasPlayerUpgradeOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> playerModel.getPlayerUpgrades().upUpgrade != Upgrades.NONE;
            case DOWN -> playerModel.getPlayerUpgrades().downUpgrade != Upgrades.NONE;
            case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade != Upgrades.NONE;
            case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade != Upgrades.NONE;
        };
    }

    public boolean isHasPlaceholder() {
        return playerModel.isHasPlaceholder();
    }

    public boolean hasAllUpgrades() {
        return playerModel.hasAllUpgrades();
    }

    public void setPlayerUpgradeOnDirection(Direction direction, Upgrades upgrade) {
        if (!hasPlayerUpgradeOnDirection(direction)) {
            switch (direction) {
                case UP -> playerModel.getPlayerUpgrades().upUpgrade = upgrade;
                case DOWN -> playerModel.getPlayerUpgrades().downUpgrade = upgrade;
                case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade = upgrade;
                case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade = upgrade;
                default -> throw new RuntimeException("Fehler bei Upgrade!");
            }
        }
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            default -> throw new RuntimeException("Fehler bei Move!");
        }
    }

    private void moveUp() {
        int movingFactor = switch (playerModel.getPlayerUpgrades().upUpgrade) {
            case TWO -> 2;
            case THREE -> 3;
            default -> 1;
        };
        adjustPosition(0, -movingFactor, Direction.UP);
    }

    private void moveLeft() {
        int movingFactor = switch (playerModel.getPlayerUpgrades().leftUpgrade) {
            case TWO -> 2;
            case THREE -> 3;
            default -> 1;
        };
        adjustPosition(-movingFactor, 0, Direction.LEFT);
    }

    private void moveRight() {
        int movingFactor = switch (playerModel.getPlayerUpgrades().rightUpgrade) {
            case TWO -> 2;
            case THREE -> 3;
            default -> 1;
        };
        adjustPosition(movingFactor, 0, Direction.RIGHT);
    }

    private void moveDown() {
        int movingFactor = switch (playerModel.getPlayerUpgrades().downUpgrade) {
            case TWO -> 2;
            case THREE -> 3;
            default -> 1;
        };
        adjustPosition(0, movingFactor, Direction.DOWN);
    }

    private void adjustPosition(int xDiff, int yDiff, Direction direction) {
        Position landOnPosition = new Position(playerModel.getPlayerPosition().x + xDiff, playerModel.getPlayerPosition().y + yDiff);

//        GameHandler.updateGameModel(game);

        Levels.getLevel(0).test();
        System.out.println("landOnPosition: x = " + landOnPosition.x + " y = " + landOnPosition.y);

        if (gameHandler.getCurrentLevel().isPlayable(landOnPosition)) {
            if (gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition) == null && !gameHandler.getCurrentLevel().getTiles().get(landOnPosition).isGoal()) {
                playerModel.setPlayerPosition(landOnPosition);
                gameHandler.updateMoves();
                return;
            }

            if (gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition) != null && (!hasPlayerUpgradeOnDirection(direction) || playerModel.isHasPlaceholder()) && !gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition).equals(Upgrades.NONE)) {
                collectUpgrade(direction, landOnPosition);
                playerModel.setPlayerPosition(landOnPosition);
                gameHandler.updateMoves();
                return;
            }

            Position upgradePushPosition = calcPositionByPosAndDir(landOnPosition, direction);

            if (gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition) != null && (hasPlayerUpgradeOnDirection(direction) || (!hasPlayerUpgradeOnDirection(direction) && getUpgradeByDirection(direction).equals(Upgrades.NONE))) && !gameHandler.getCurrentLevel().getTiles().get(upgradePushPosition).getTileType().equals(TileType.WALL) && !playerModel.isHasPlaceholder() && gameHandler.getCurrentLevel().getUpgrades().get(upgradePushPosition) == null) {
                Upgrades removedUpgrade = gameHandler.getCurrentLevel().getUpgrades().remove(landOnPosition);
                gameHandler.getCurrentLevel().getUpgrades().put(upgradePushPosition, removedUpgrade);
                playerModel.setPlayerPosition(landOnPosition);
                gameHandler.updateMoves();
                return;
            }

            if (gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition) != null && gameHandler.getCurrentLevel().getUpgrades().get(landOnPosition).equals(Upgrades.NONE) && (hasPlayerUpgradeOnDirection(direction) || playerModel.isHasPlaceholder())) {
                collectUpgrade(direction, landOnPosition);
                playerModel.setPlayerPosition(landOnPosition);
                gameHandler.updateMoves();
                return;
            }

            if (gameHandler.getCurrentLevel().getTiles().get(landOnPosition).isGoal()) {
                gameHandler.updateMoves();
                if (canFinish()) {
                    playerModel.setPlayerPosition(landOnPosition);
                    gameHandler.finish();
                    return;
                }
                playerModel.setPlayerPosition(landOnPosition);
                return;
            }
        }
    }

    private void collectUpgrade(Direction direction, Position pos) {
        if (!playerModel.isHasPlaceholder()) {
            if (gameHandler.getCurrentLevel().getUpgrades().get(pos).equals(Upgrades.PLACEHOLDER)) {
                playerModel.setHasPlaceholder(true);
                playerModel.setPlaceholderDirection(direction);
            }
            switch (direction) {
                case UP -> playerModel.getPlayerUpgrades().upUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case DOWN -> playerModel.getPlayerUpgrades().downUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                default -> throw new RuntimeException("Invalides Upgrade!");
            }
            gameHandler.getCurrentLevel().getUpgrades().remove(pos);
        } else {
            switch (playerModel.getPlaceholderDirection()) {
                case UP -> playerModel.getPlayerUpgrades().upUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                case DOWN -> playerModel.getPlayerUpgrades().downUpgrade = gameHandler.getCurrentLevel().getUpgrades().get(pos);
                default -> throw new RuntimeException("Invalides Upgrade!");
            }
            playerModel.setHasPlaceholder(false);
            playerModel.setPlaceholderDirection(null);
            gameHandler.getCurrentLevel().getUpgrades().remove(pos);
        }
    }

    public boolean canFinish() {
        return hasAllUpgrades() || gameHandler.getGameModel().allUpgradesCollected();
    }

    public Position calcPositionByPosAndDir(Position pos, Direction dir) {
        Position calculatedPos = new Position(pos.x, pos.y);
        switch (dir) {
            case UP -> calculatedPos.y--;
            case DOWN -> calculatedPos.y++;
            case RIGHT -> calculatedPos.x++;
            case LEFT -> calculatedPos.x--;
            default -> throw new RuntimeException("Da ist etwas schiefgelaufen beim Positionsprüfen!!!");
        }
        return calculatedPos;
    }

    public Upgrades getUpgradeByDirection(Direction direction) {
        return switch (direction) {
            case UP -> playerModel.getPlayerUpgrades().upUpgrade;
            case DOWN -> playerModel.getPlayerUpgrades().downUpgrade;
            case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade;
            case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade;
            default -> throw new RuntimeException("Invalides Upgrade!");
        };
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }
}