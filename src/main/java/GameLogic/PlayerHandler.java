package GameLogic;

import Level.Levels;
import model.PlayerModel;


public class PlayerHandler {
    private PlayerModel playerModel;
    private GameHandler gameHandler;
    public PlayerHandler(GameHandler gameHandler){
        this.playerModel = gameHandler.getGameModel().getPlayerModel();
        this.gameHandler = gameHandler;
    }

    public boolean hasPlayerUpgradeOnDirection(Direction direction) {
        return switch (direction) {
            case UP -> playerModel.getPlayerUpgrades().upUpgrade != Upgrades.NONE;
            case DOWN -> playerModel.getPlayerUpgrades().downUpgrade != Upgrades.NONE;
            case RIGHT -> playerModel.getPlayerUpgrades().rightUpgrade != Upgrades.NONE;
            case LEFT -> playerModel.getPlayerUpgrades().leftUpgrade != Upgrades.NONE;
        };
    }
    public boolean hasAllUpgrades() {
        return playerModel.hasAllUpgrades();
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
        Position landOnPosition = calculateLandOnPosition(xDiff, yDiff);
        Levels.getLevel(0).test();
        System.out.println("landOnPosition: x = " + landOnPosition.x + " y = " + landOnPosition.y);

        if (isPlayable(landOnPosition)) {
            if (handleEmptyTile(landOnPosition)) return;
            if (handleUpgradeCollection(landOnPosition, direction)) return;
            if (handleUpgradePush(landOnPosition, direction)) return;
            if (handleNoneUpgradeCollection(landOnPosition, direction)) return;
            if (handleGoalTile(landOnPosition)) return;
        }
    }

    private Position calculateLandOnPosition(int xDiff, int yDiff) {
        return new Position(playerModel.getPlayerPosition().x + xDiff, playerModel.getPlayerPosition().y + yDiff);
    }

    private boolean isPlayable(Position position) {
        return gameHandler.getCurrentLevel().isPlayable(position);
    }

    private boolean handleEmptyTile(Position position) {
        if (gameHandler.getCurrentLevel().getUpgrades().get(position) == null
                && !gameHandler.getCurrentLevel().getTiles().get(position).isGoal()) {
            playerModel.setPlayerPosition(position);
            gameHandler.updateMoves();
            return true;
        }
        return false;
    }

    private boolean handleUpgradeCollection(Position position, Direction direction) {
        if (gameHandler.getCurrentLevel().getUpgrades().get(position) != null
                && (!hasPlayerUpgradeOnDirection(direction) || playerModel.isHasPlaceholder())
                && !gameHandler.getCurrentLevel().getUpgrades().get(position).equals(Upgrades.NONE)) {
            collectUpgrade(direction, position);
            playerModel.setPlayerPosition(position);
            gameHandler.updateMoves();
            return true;
        }
        return false;
    }

    private boolean handleUpgradePush(Position position, Direction direction) {
        Position upgradePushPosition = calcPositionByPosAndDir(position, direction);
        if (gameHandler.getCurrentLevel().getUpgrades().get(position) != null
                && (hasPlayerUpgradeOnDirection(direction)
                || (!hasPlayerUpgradeOnDirection(direction) && getUpgradeByDirection(direction).equals(Upgrades.NONE)))
                && !gameHandler.getCurrentLevel().getTiles().get(upgradePushPosition).getTileType().equals(TileType.WALL)
                && !playerModel.isHasPlaceholder()
                && gameHandler.getCurrentLevel().getUpgrades().get(upgradePushPosition) == null) {
            Upgrades removedUpgrade = gameHandler.getCurrentLevel().getUpgrades().remove(position);
            gameHandler.getCurrentLevel().getUpgrades().put(upgradePushPosition, removedUpgrade);
            playerModel.setPlayerPosition(position);
            gameHandler.updateMoves();
            return true;
        }
        return false;
    }

    private boolean handleNoneUpgradeCollection(Position position, Direction direction) {
        if (gameHandler.getCurrentLevel().getUpgrades().get(position) != null
                && gameHandler.getCurrentLevel().getUpgrades().get(position).equals(Upgrades.NONE)
                && (hasPlayerUpgradeOnDirection(direction) || playerModel.isHasPlaceholder())) {
            collectUpgrade(direction, position);
            playerModel.setPlayerPosition(position);
            gameHandler.updateMoves();
            return true;
        }
        return false;
    }

    private boolean handleGoalTile(Position position) {
        if (gameHandler.getCurrentLevel().getTiles().get(position).isGoal()) {
            gameHandler.updateMoves();
            if (canFinish()) {
                playerModel.setPlayerPosition(position);
                gameHandler.finish();
                return true;
            }
            playerModel.setPlayerPosition(position);
            return true;
        }
        return false;
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
            default -> throw new RuntimeException("Fehler beim Berechnen der Position!");
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
    public void setPlayerModel(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }
}