package DrawLogic;

import GameLogic.Game;
import GameLogic.Player;
import GameLogic.Position;
import GameLogic.Upgrades;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Map;


public class DrawPanel extends JPanel {
    private Game game;
    private Player player;
    private Dimension gameDimensions;
    private int tileSize;
    private Position playerPixelPosition;
    private Timer playerAnimationTimer;
    private Position upgradeOffset;

    DrawPanel(Game game) {
        this.game = game;
        player = this.game.getPlayer();
        startDrawEngine();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameField(g);
    }

    public void refetchPlayer() {
        player = game.getPlayer();
    }
    public void setGame(Game game){
        this.game = game;
    }
    /**
     * <strong>Only call this function on level changes and only when animations are done!</strong>
     */
    public void recalculateDimensions(){
        gameDimensions = new Dimension(game.getCurrentlevel().width, game.getCurrentlevel().height);
        int width = getWidth();
        int height = getHeight();

        int tileWidth = width / gameDimensions.width;
        int tileHeight = height / gameDimensions.height;

        tileSize = Math.min(tileWidth, tileHeight);
        playerPixelPosition = calculatePixelPos(player.getPlayerPosition());
    }
    public boolean movePlayer(Position from, Position to){
        if(playerAnimationTimer.isRunning()) return false;
        animatePlayerMovement(from, to);
        player.setPlayerPosition(to);
        return true;
    }


    private Position calculatePixelPos(Position pos) {
        return new Position(pos.x * tileSize, pos.y * tileSize);
    }

    private void drawGameField(Graphics graphics) {
        int elementSize = tileSize - (tileSize / 6);
        int elementOffset = (tileSize - elementSize) / 2;
        Position shadowOffset = new Position(0, 6);
        Map<Position, Upgrades> upgrades = game.currentlevel.upgrades;

        for (int y = 0; y < gameDimensions.height; y++) {
            for (int x = 0; x < gameDimensions.width; x++) {
                graphics.drawImage(game.getCurrentlevel().tiles.get(new Position(x, y)).getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null); //draw Tile
                //rainElementAnimation(tiles.get(new Position(x,y)).getImage(),graphics,new Position(x,y),tileSize);
                if (game.getCurrentlevel().upgrades.get(new Position(x, y)) == null) continue;
                graphics.drawImage(MapUpgrade.getImage(), x * tileSize + elementOffset, y * tileSize, upgradeOffset.y, upgradeOffset.y, null); //draw Upgradeshadow
                graphics.drawImage(MapUpgrade.getImage(upgrades.get(new Position(x, y))), x * tileSize - shadowOffset.x + upgradeOffset.x, y * tileSize - shadowOffset.y, elementSize, elementSize, null);//draw Upgrade
            }
        }
        //Draw goals
        ArrayList<Position> goal = game.currentlevel.finishPositions;
        for (Position pos : goal) {
            if (player.hasAllUpgrades()) {
                graphics.drawImage(Tile.getGoal(true), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            } else {
                graphics.drawImage(Tile.getGoal(false), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            }
        }
        drawPlayer(graphics, calculatePixelPos(player.getPlayerPosition()), elementSize, elementOffset);
    }

    private void drawPlayer(Graphics graphics, Position pos, int elementSize, int elementOffset) {

        int shadowOffsetx = 0;
        int shadowOffsety = 6;

        graphics.drawImage(player.getPlayerIMG(), pos.x + elementOffset, pos.y, elementSize, elementSize, null);
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().upUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, 0); //Upgrade Up
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().rightUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 0.5); //Upgrade Right
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().downUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI); //Upgrade Down
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().leftUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 1.5); //Upgrade Left

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

        int animationFrameCount = 12;

        playerAnimationTimer = new Timer(0, new ActionListener() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFrame >= animationFrameCount) {
                    if(playerAnimationTimer.isRunning()) playerAnimationTimer.stop();
                } else {
                    double t = (double) currentFrame / animationFrameCount;
                    double easingMultiplier = t * t / (t * t + (1 - t) * (1 - t));

                    int x = oldPosInPixels.x + (int) (deltaX * easingMultiplier);
                    int y = oldPosInPixels.y + (int) (deltaY * easingMultiplier);

                    playerPixelPosition = new Position(x, y); // Update player's pixel position
                    repaint();
                    currentFrame++;
                }
            }
        });
        playerAnimationTimer.start();
    }
    private void startDrawEngine() {
        final double[] pixeloffset = {(double) (-(tileSize / 6)) / 2, tileSize - (tileSize / 6)};
        final int[] currentStep = {0};
        final int[] countBy = {1};
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(currentStep[0] >= 5){
                pixeloffset[0] *= 1.01;
                pixeloffset[1] *= 1.01;
                }
                else{
                    pixeloffset[0] *= 0.99;
                    pixeloffset[1] *= 0.99;
                }
                if(currentStep[0] == 5 || currentStep[0] == -5) countBy[0] *= -1;
                currentStep[0] -= countBy[0];
                upgradeOffset.x = (int)pixeloffset[0];
                upgradeOffset.y = (int)pixeloffset[1];
                try {
                    Thread.sleep(1000/120);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        repaint();
    }

}