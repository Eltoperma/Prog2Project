//package DrawLogic;
//
//import GameLogic.*;
//import jaco.mp3.player.MP3Player;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.AffineTransform;
//import java.io.File;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class GamePanel extends JPanel {
//    private int rows, cols, tileSize;
//    private Position playerPixelPosition;
//    private Position oldPlayerPosition;
//    private Player player;
//    private Map<Position,Tile> tiles;
//    private Map<Position, Upgrades> upgrades;
//    private boolean moveFinished = true;
//    private Timer animationTimer;
//    private boolean firstDraw = true;
//    GamePanel() {
//        updateGamefield();
//
//    };
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        drawGameField(g);
//    }
//
//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(1000,1000);
//    }
//
//
//
//private void fetchDataFromGame() {
//
////        game = GameHandler.getGame();
//    rows = Game.getCurrentlevel().height;
//    cols = Game.getCurrentlevel().width;
//    player = Game.getPlayer();
//    player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
//    //System.out.println("Pos: " + Game.getPlayer().getPlayerPosition() + " x " + Game.getPlayer().getPlayerPosition().x + " y " + Game.getPlayer().getPlayerPosition().y);
//    tiles = Game.getCurrentlevel().tiles;
//    upgrades = Game.getCurrentlevel().upgrades;
//
//
//}
//
//private void updateGamefield() {
//    fetchDataFromGame();
//    updateTileSize();
//}
//
//private void updateTileSize() {
//    int width = getWidth();
//    int height = getHeight();
//
//    int tileWidth = width / cols;
//    int tileHeight = height / rows;
//    tileSize = Math.min(tileWidth, tileHeight);
//
//}
//
//public void controlGame(int keyCode) {
//    if(!moveFinished)return;
//    updateOldPlayerPosition();
//    switch (keyCode) {
//        case KeyEvent.VK_W:
//            if (player.getPlayerPosition().y > 0) {
//                try {
//                    Game.getPlayer().move(Direction.UP);
//                    player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
//                } catch (Exception e) {
//                    System.err.println("Fehler: " + e.getMessage());
//                }
//            }
//            break;
//        case KeyEvent.VK_S:
//            if (player.getPlayerPosition().y < rows - 1) {
//                try {
//                    Game.getPlayer().move(Direction.DOWN);
//                    player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
//                } catch (Exception e) {
//                    System.err.println("Fehler: " + e.getMessage());
//                }
//            }
//            break;
//        case KeyEvent.VK_A:
//            if (player.getPlayerPosition().x > 0) {
//                try {
//                    Game.getPlayer().move(Direction.LEFT);
//                    player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
//                } catch (Exception e) {
//                    System.err.println("Fehler: " + e.getMessage());
//                }
//            }
//            break;
//        case KeyEvent.VK_D:
//            if (player.getPlayerPosition().x < cols - 1) {
//                try {
//                    Game.getPlayer().move(Direction.RIGHT);
//                    player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
//                } catch (Exception e) {
//                    System.err.println("Fehler: " + e.getMessage());
//                }
//            }
//            break;
//    }
//    if (animationTimer != null) {
//        animationTimer.stop();
//    }
//    playerPixelPosition = new Position(oldPlayerPosition.x * tileSize, oldPlayerPosition.y * tileSize);
//    moveFinished = false;
//    startAnimation();
//    updateGamefield();
//    //repaint();
//
//}
//
//private void updateOldPlayerPosition() {
//    oldPlayerPosition = Game.getPlayer().getPlayerPosition();
//}
//public void updatePanel(){
//        animationTimer.stop();
//        firstDraw = true;
//        updateGamefield();
//        //playerPixelPosition = new Position(player.getPlayerPosition().x*tileSize,player.getPlayerPosition().y*tileSize);
//        repaint();
//    }
//private void drawGameField(Graphics graphics) {
//    updateTileSize();
//    int elementSize = tileSize - (tileSize / 6);
//    int elementOffset = (tileSize - elementSize) / 2;
//    Position shadowOffset = new Position(0, 6);
//    Map<Position, Upgrades> upgrades = Game.currentlevel.upgrades;
//    for (int y = 0; y < rows; y++) {
//        for (int x = 0; x < cols; x++) {
//            graphics.drawImage(tiles.get(new Position(x, y)).getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null); //draw Tile
//            //rainElementAnimation(tiles.get(new Position(x,y)).getImage(),graphics,new Position(x,y),tileSize);
//            if (upgrades.get(new Position(x, y)) == null) continue;
//            graphics.drawImage(MapUpgrade.getImage(), x * tileSize + elementOffset, y * tileSize, elementSize, elementSize, null); //draw Upgradeshadow
//            graphics.drawImage(MapUpgrade.getImage(upgrades.get(new Position(x, y))), x * tileSize - shadowOffset.x + elementOffset, y * tileSize - shadowOffset.y, elementSize, elementSize, null);//draw Upgrade
//        }
//    }
//    //Draw goals
//    ArrayList<Position> goal = Game.currentlevel.finishPositions;
//    for (Position pos : goal) {
//        if (player.hasAllUpgrades()) {
//            graphics.drawImage(Tile.getGoal(true), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
//        } else {
//            graphics.drawImage(Tile.getGoal(false), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
//        }
//    }
//    drawPlayer(graphics,playerPixelPosition, elementSize, elementOffset);
//}
//
//private void drawPlayer(Graphics graphics, Position pos, int elementSize, int elementOffset) {
//        if(firstDraw) {
//            playerPixelPosition = new Position(player.getPlayerPosition().x*tileSize,player.getPlayerPosition().y*tileSize);
//            firstDraw = false;
//            pos = playerPixelPosition;
//        }
//    int shadowOffsetx = 0;
//    int shadowOffsety = 6;
//    System.out.println("Drawing player on: " + pos.x + " " + pos.y);
//
//    graphics.drawImage(player.getPlayerIMG(), pos.x + elementOffset, pos.y, elementSize, elementSize, null);
//    drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().upUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, 0); //Upgrade Up
//    drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().rightUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 0.5); //Upgrade Right
//    drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().downUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI); //Upgrade Down
//    drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().leftUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 1.5); //Upgrade Left
//
//}
//
//private void drawRotatedImage(Graphics g, Image image, int x, int y, int width, int height, double angle) {
//    if (image != null) {
//        Graphics2D g2d = (Graphics2D) g;
//        AffineTransform old = g2d.getTransform(); // Save current transform
//        AffineTransform at = AffineTransform.getTranslateInstance(x + width / 2, y + height / 2);
//        at.rotate(angle, 0, 0); // Rotate around the center of the image
//        g2d.setTransform(at);
//        g2d.drawImage(image, -width / 2, -height / 2, width, height, null); // Draw image
//        g2d.setTransform(old); // Restore previous transform
//    }
//}
//
//private void startAnimation() {
//    Position pos = Game.player.getPlayerPosition();
//    Position newPosInPixels = new Position(pos.x * tileSize, pos.y * tileSize);
//    Position oldPosInPixels = new Position(oldPlayerPosition.x * tileSize, oldPlayerPosition.y * tileSize);
//
//    int deltaX = newPosInPixels.x - oldPosInPixels.x;
//    int deltaY = newPosInPixels.y - oldPosInPixels.y;
//
//    int steps = 12;
//
//    animationTimer = new Timer(0, new ActionListener() {
//        int currentStep  = 0;
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            updateGamefield();
//            if (currentStep >= steps) {
//                animationTimer.stop();
//                moveFinished = true;
//            } else {
//                double t = (double) currentStep / steps;
//                double easingMultiplier = t * t / (t * t + (1 - t) * (1 - t));
//
//                int x = oldPosInPixels.x + (int) (deltaX * easingMultiplier);
//                int y = oldPosInPixels.y + (int) (deltaY * easingMultiplier);
//
//                playerPixelPosition = new Position(x, y); // Update player's pixel position
//                repaint();
//
//                currentStep++;
//            }
//        }
//    });
//    animationTimer.start();
//}
////    private void rainElementAnimation(Image img, Graphics g, Position targetPos, int elementSize){
////
////        int deltaY = targetPos.y - (-10);
////
////        int steps = 24;
////
////        animationTimer = new Timer(4, new ActionListener() {
////            int currentStep  = 0;
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                if (currentStep >= steps) {
////                    ((Timer)e.getSource()).stop(); // Stop the timer
////                } else {
////                    double t = (double) currentStep / steps;
////                    double easingMultiplier = t * t / (t * t + (1 - t) * (1 - t));
////
////
////                    int y = -10 + (int) (deltaY * easingMultiplier);
////
////                    g.drawImage(img,targetPos.x,y,elementSize,elementSize,null);
////                    repaint();
////
////                    currentStep++;
////                }
////            }
////        });
////        animationTimer.start();
////    }
//}
