package DrawLogic;

import GameLogic.Position;
import GameLogic.Upgrades;
import model.GameModel;
import model.PlayerModel;
import GameLogic.Tile;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Map;


public class DrawPanel extends JPanel {
    private GameModel gameModel;
    private PlayerModel playerModel;
    private Dimension gameDimensions;
    private int tileSize;
    private Position playerPixelPosition;
    private Timer playerAnimationTimer;
    private int shadowSize, upgradeOffset;
    public boolean isAnimationFinished = true;
    private double[] pixeloffset;
    private boolean updatePixeloffset = false;

    public DrawPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        setSize(1000, 1000);
        playerModel = this.gameModel.getPlayerModel();
        pixeloffset = new double[]{-(tileSize / 6.0) / 2, tileSize - (tileSize / 6.0), 0, 0};
        recalculateDimensions();
        drawEngine(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameField(g);
    }

    public void refetchPlayer() {
        playerModel = gameModel.getPlayerModel();
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void resetDrawEngine() {
        drawEngine(false);
        drawEngine(true);
    }

    /**
     * <strong>Only call this function on level changes and only when animations are done!</strong>
     */
    public void recalculateDimensions() {
        gameDimensions = new Dimension(gameModel.getLevelModel().getWidth(), gameModel.getLevelModel().getHeight());
        int width = getWidth();
        int height = getHeight();

        int tileWidth = width / gameDimensions.width;
        int tileHeight = height / gameDimensions.height;

        tileSize = Math.min(tileWidth, tileHeight);
        playerPixelPosition = calculatePixelPos(playerModel.getPlayerPosition());

        System.out.println("DrawPos: " + playerModel.getPlayerPosition().x + " " + playerModel.getPlayerPosition().y);

        pixeloffset[2] = -(tileSize / 6.0) / 2;
        pixeloffset[3] = tileSize - (tileSize / 6.0); //breaks animations but is implemented right since this should only really be called on level changes
        updatePixeloffset = true;
    }

    public boolean movePlayer(Position from, Position to) {
        isAnimationFinished = false;
        animatePlayerMovement(from, to);
        playerModel.setPlayerPosition(to);
        return true;
    }


    private Position calculatePixelPos(Position pos) {
        return new Position(pos.x * tileSize, pos.y * tileSize);
    }

    private void drawGameField(Graphics graphics) {
        int elementSize = tileSize - (tileSize / 6);
        int elementOffset = (tileSize - elementSize) / 2;
        int objectOffsetFromShadow = 6;
        Map<Position, Upgrades> upgrades = gameModel.getLevelModel().getUpgrades();

        for (int y = 0; y < gameDimensions.height; y++) {
            for (int x = 0; x < gameDimensions.width; x++) {
                graphics.drawImage(gameModel.getLevelModel().getTiles().get(new Position(x, y)).getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null); //draw Tile
                //rainElementAnimation(tiles.get(new Position(x,y)).getImage(),graphics,new Position(x,y),tileSize);
                if (gameModel.getLevelModel().getUpgrades().get(new Position(x, y)) == null) continue;
                int shadowOffset = (tileSize - shadowSize) / 2;
                graphics.drawImage(MapUpgrade.getImage(), x * tileSize + shadowOffset, y * tileSize + shadowOffset, shadowSize, shadowSize, null); //draw Upgradeshadow
                graphics.drawImage(MapUpgrade.getImage(upgrades.get(new Position(x, y))), x * tileSize + elementOffset, y * tileSize - objectOffsetFromShadow + upgradeOffset, elementSize, elementSize, null);//draw Upgrade
            }
        }
        //Draw goals
        ArrayList<Position> goal = gameModel.getLevelModel().getFinishPositions();
        for (Position pos : goal) {
            if (playerModel.hasAllUpgrades()) {
                graphics.drawImage(Tile.getGoal(true), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            } else {
                graphics.drawImage(Tile.getGoal(false), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            }
        }
        drawPlayer(graphics, playerPixelPosition, elementSize, elementOffset);
        drawHighscores(graphics, gameModel.getCurrentScore());
    }

    private void drawHighscores(Graphics g, int score) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(("Score: " + String.valueOf(score)),50, 50);
//        g.drawString(("Highscore: " + String.valueOf(highscore)),700, 50);

    }



    private void drawPlayer(Graphics graphics, Position pos, int elementSize, int elementOffset) {

        int shadowOffsetx = 0;
        int shadowOffsety = 6;

        graphics.drawImage(playerModel.getPlayerIMG(), pos.x + elementOffset, pos.y, elementSize, elementSize, null);
        drawRotatedImage(graphics, this.playerModel.getUpgradeIMG(playerModel.getPlayerUpgrades().upUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, 0); //Upgrade Up
        drawRotatedImage(graphics, this.playerModel.getUpgradeIMG(playerModel.getPlayerUpgrades().rightUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 0.5); //Upgrade Right
        drawRotatedImage(graphics, this.playerModel.getUpgradeIMG(playerModel.getPlayerUpgrades().downUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI); //Upgrade Down
        drawRotatedImage(graphics, this.playerModel.getUpgradeIMG(playerModel.getPlayerUpgrades().leftUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 1.5); //Upgrade Left

    }

    private void drawRotatedImage(Graphics g, Image image, int x, int y, int width, int height, double angle) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform(); // Save current transform
        AffineTransform at = AffineTransform.getTranslateInstance(x + width / 2, y + height / 2);
        at.rotate(angle, 0, 0); // Rotate around the center of the image
        g2d.setTransform(at);
        g2d.drawImage(image, -width / 2, -height / 2, width, height, null); // Draw image
        g2d.setTransform(old); // Restore previous transform
    }

    private void animatePlayerMovement(Position oldPosition, Position newPosition) {
        Position newPosInPixels = calculatePixelPos(newPosition);
        Position oldPosInPixels = calculatePixelPos(oldPosition);

        int deltaX = newPosInPixels.x - oldPosInPixels.x;
        int deltaY = newPosInPixels.y - oldPosInPixels.y;

        int animationFrameCount = 60;

        playerAnimationTimer = new Timer(0, new ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame == animationFrameCount) {
                    if (playerAnimationTimer.isRunning()) {
                        playerAnimationTimer.stop();
                        isAnimationFinished = true;
                        refetchPlayer();
                        //recalculateDimensions();
                    }
                } else {
                    double t = (double) currentFrame / animationFrameCount;
                    double easingMultiplier = t * t / (t * t + (1 - t) * (1 - t));

                    int x = oldPosInPixels.x + (int) (deltaX * easingMultiplier);
                    int y = oldPosInPixels.y + (int) (deltaY * easingMultiplier);
                    //System.out.println(playerPixelPosition.x + " " + playerPixelPosition.y);
                    playerPixelPosition = new Position(x, y); // Update player's pixel position
                    currentFrame++;
                }
            }
        });
        playerAnimationTimer.start();
    }
    //Implementation als Timer hat zu performance-Problemen geführt
    private void drawEngine(boolean start) {

        Thread drawThread = new Thread(new Runnable() {

            int[] currentStep = {0};
            int[] countBy = {1};

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    if (updatePixeloffset) {
                        pixeloffset[0] = pixeloffset[2];
                        pixeloffset[1] = pixeloffset[3];
                        updatePixeloffset = false;
                        currentStep[0] = 0;
                        countBy[0] = 1;
                    }

                    if (currentStep[0] <= 75 && currentStep[0] > 0) {
                        pixeloffset[0] *= 1.008;
                        pixeloffset[1] *= 1.0005;
                    } else if (currentStep[0] >= -75 && currentStep[0] < 0) {
                        pixeloffset[0] *= 0.992;
                        pixeloffset[1] *= 0.9995;
                    }

                    if (currentStep[0] == 75 || currentStep[0] == -75) countBy[0] *= -1;
                    currentStep[0] -= countBy[0];

                    upgradeOffset = (int) pixeloffset[0];
                    shadowSize = (int) pixeloffset[1];

                    SwingUtilities.invokeLater(() -> repaint());

                    try {
                        Thread.sleep(12);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        if (!start) drawThread.interrupt();
        drawThread.start();
    }


}