package DrawLogic;


import GameLogic.*;
import jaco.mp3.player.MP3Player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;

import java.util.ArrayList;
import java.util.Map;
import java.net.URL;


public class GameWindow extends JFrame {

    Game game;
    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;

    private int rows;
    private int cols;
    private Player player;
    private Position oldPlayerPosition = null;
    private Timer animationTimer;
    private int tileSize;
    private JPanel gamePanel;
    private MP3Player backgroundMusicPlayer;
    private boolean moveFinished = true;


    public GameWindow() {
        //set Window Icon
        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        //set Window title
        setTitle("UDLR Modify");
        // Initial window size
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centers the window
        setLocationRelativeTo(null);
        //Local instance of the player Object
        player = new Player(new Position(0, 0), new Upgrade());
        //loads Level Data
        fetchDataFromGame();
        //setup Backgroundmusic
        backgroundMusicPlayer = new MP3Player(getSongs());
        backgroundMusicPlayer.setRepeat(true);


        gamePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGameField(g);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(cols * tileSize, rows * tileSize);
            }
        };
        gamePanel.setBackground(Color.BLACK);
        add(gamePanel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTileSize(gamePanel);
                gamePanel.repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                controlGame(keyCode);
            }
        });

        oldPlayerPosition = Game.getCurrentlevel().startingPosition;
        updateTileSize(gamePanel); // Initialize tile size
        playBackgroundMusic();
        //runUpdater();
    }

    private void fetchDataFromGame() {
//        game = GameHandler.getGame();
        rows = Game.getCurrentlevel().height;
        cols = Game.getCurrentlevel().width;
        player = Game.getPlayer();
        //System.out.println("Pos: " + Game.getPlayer().getPlayerPosition() + " x " + Game.getPlayer().getPlayerPosition().x + " y " + Game.getPlayer().getPlayerPosition().y);
        this.player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
        tiles = Game.getCurrentlevel().tiles;
        upgrades = Game.getCurrentlevel().upgrades;
    }

    private void updateGamefield() {
        fetchDataFromGame();
        updateTileSize(gamePanel);
        repaint();
    }

    private void updateTileSize(JPanel gamePanel) {
        int width = gamePanel.getWidth();
        int height = gamePanel.getHeight();

        int tileWidth = width / cols;
        int tileHeight = height / rows;
        tileSize = Math.min(tileWidth, tileHeight);
    }

    private void runUpdater() {
        Timer timer = new Timer(12, null);
        timer.setRepeats(false);
        new Thread(() -> {
            while (true) {
                timer.start();
                if (!timer.isRunning()) updateGamefield();
            }
        }).start();
    }

    private void playBackgroundMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundMusicPlayer.play();
            }
        }).start();
    }

    private File[] getSongs() {
        File song1 = new File("src/assets/sounds/music/modify_my_brain.mp3");
        File song2 = new File("src/assets/sounds/music/crystaline.mp3");
        return new File[]{song1, song2};
    }

    private void toggleMusic() {
        if (backgroundMusicPlayer.isPaused()) {
            backgroundMusicPlayer.play();
        } else {
            backgroundMusicPlayer.pause();
        }
    }

    // Method to play sound effects
    private void playSoundEffect(final String soundFilePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Play the sound effect
                try {
                    MP3Player soundEffectPlayer = new MP3Player(new URL(soundFilePath));
                    soundEffectPlayer.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //TODO ADD INPUT TIMEOUT TO LET ANIMATION PLAY OUT
    private void controlGame(int keyCode) {
        updateOldPlayerPosition();
        switch (keyCode) {
            case KeyEvent.VK_W:
                if (player.getPlayerPosition().y > 0) {
                    try {
                        Game.getPlayer().move(Direction.UP);
                        player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
                    } catch (Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_S:
                if (player.getPlayerPosition().y < rows - 1) {
                    try {
                        Game.getPlayer().move(Direction.DOWN);
                        player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
                    } catch (Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_A:
                if (player.getPlayerPosition().x > 0) {
                    try {
                        Game.getPlayer().move(Direction.LEFT);
                        player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
                    } catch (Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (player.getPlayerPosition().x < cols - 1) {
                    try {
                        Game.getPlayer().move(Direction.RIGHT);
                        player.setPlayerPosition(Game.getPlayer().getPlayerPosition());
                    } catch (Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_M:
                toggleMusic();
                break;
            case KeyEvent.VK_COMMA:
                backgroundMusicPlayer.skipForward();
                break;
        }
        moveFinished = false;
        updateGamefield();
    }

    private void updateOldPlayerPosition() {
        oldPlayerPosition = Game.getPlayer().getPlayerPosition();
    }

    private void drawGameField(Graphics graphics) {

        int elementSize = tileSize - (tileSize / 6);
        int elementOffset = (tileSize - elementSize) / 2;
        Position shadowOffset = new Position(0, 10);
        Map<Position, Upgrades> upgrades = Game.currentlevel.upgrades;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                graphics.drawImage(tiles.get(new Position(x, y)).getImage(), x * tileSize, y * tileSize, tileSize, tileSize, null); //draw Tile
                if (upgrades.get(new Position(x, y)) == null) continue;
                graphics.drawImage(MapUpgrade.getImage(), x * tileSize + elementOffset, y * tileSize, elementSize, elementSize, null); //draw Upgradeshadow
                graphics.drawImage(MapUpgrade.getImage(upgrades.get(new Position(x, y))), x * tileSize - shadowOffset.x + elementOffset, y * tileSize - shadowOffset.y, elementSize, elementSize, null);//draw Upgrade
            }
        }
        //Draw goals
        ArrayList<Position> goal = Game.currentlevel.finishPositions;
        for (Position pos : goal) {
            if (player.hasAllUpgrades()) {
                graphics.drawImage(Tile.getGoal(true), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            } else {
                graphics.drawImage(Tile.getGoal(false), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, null);
            }
        }

        if (!moveFinished) {
            animationTimer = new Timer(10, new ActionListener() {
                Position pos = Game.player.getPlayerPosition();
                Position newPosInPixels = new Position(pos.x * tileSize, pos.y * tileSize);
                Position oldPosInPixels = new Position(oldPlayerPosition.x * tileSize, oldPlayerPosition.y * tileSize);

                int deltaX = newPosInPixels.x - oldPosInPixels.x;
                int deltaY = newPosInPixels.y - oldPosInPixels.y;

                int steps = 12; // amount of frames for animation
                int currentStep = 0;


                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentStep >= steps) {
                        moveFinished = true;
                        animationTimer.stop();

                    } else {

                        double t = (double) currentStep / steps;
                        double easingMultiplier = t * t / (t * t + (1 - t) * (1 - t));

                        int x = oldPlayerPosition.x + (int) (deltaX * easingMultiplier);
                        int y = oldPlayerPosition.y + (int) (deltaY * easingMultiplier);

                        drawPlayer(graphics, new Position(x, y), elementSize, elementOffset);

                        currentStep++;
                        //repaint();
                    }
                }
            });
            animationTimer.start();

        } else {
            Position pos = Game.player.getPlayerPosition();
            Position newPosInPixels = new Position(pos.x * tileSize, pos.y * tileSize);
            drawPlayer(graphics,newPosInPixels, elementSize,elementOffset);
        }
    }

    private void drawPlayer(Graphics graphics, Position pos, int elementSize, int elementOffset) {
        int shadowOffsetx = 0;
        int shadowOffsety = 10;
        System.out.println("Drawing player on: " + pos.x + " " + pos.y);

        graphics.drawImage(player.getPlayerIMG(), pos.x + elementOffset, pos.y, elementSize, elementSize, null);
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().upUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, 0); //Upgrade Up
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().rightUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 0.5); //Upgrade Right
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().downUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI); //Upgrade Down
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().leftUpgrade), pos.x - shadowOffsetx + elementOffset, pos.y - shadowOffsety, elementSize, elementSize, Math.PI * 1.5); //Upgrade Left
    }

    private void drawRotatedImage(Graphics g, Image image, int x, int y, int width, int height, double angle) {
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform old = g2d.getTransform(); // Save current transform
            AffineTransform at = AffineTransform.getTranslateInstance(x + width / 2, y + height / 2);
            at.rotate(angle, 0, 0); // Rotate around the center of the image
            g2d.setTransform(at);
            g2d.drawImage(image, -width / 2, -height / 2, width, height, null); // Draw image
            g2d.setTransform(old); // Restore previous transform
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
