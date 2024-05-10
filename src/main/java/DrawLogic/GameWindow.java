package DrawLogic;


import GameLogic.*;
import jaco.mp3.player.MP3Player;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.File;

import java.util.Map;
import java.net.URL;


public class GameWindow extends JFrame {

    Game game;
    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;

    private int rows;
    private int cols;
    private Player player;
    private int playerX;
    private int playerY;
    private int tileSize;
    private JPanel gamePanel;

    private MP3Player backgroundMusicPlayer;


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
        player = new Player(new Position(0,0),new Upgrade());
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

        updateTileSize(gamePanel); // Initialize tile size
        playBackgroundMusic();
    }

    private void fetchDataFromGame() {
//        game = GameHandler.getGame();
        rows = Game.getCurrentlevel().height;
        cols = Game.getCurrentlevel().width;
        player = Game.getPlayer();
        playerX = Game.getPlayer().getPlayerPosition().x;
        System.out.println("Pos: " + Game.getPlayer().getPlayerPosition() + " x " + Game.getPlayer().getPlayerPosition().x + " y " + Game.getPlayer().getPlayerPosition().y);
        playerY = Game.getPlayer().getPlayerPosition().y;
        this.player.setPlayerPosition(new Position(playerX,playerY));
        tiles = Game.getCurrentlevel().tiles;
        upgrades = Game.getCurrentlevel().upgrades;
    }

    private   void updateGamefield(){
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
    private void playBackgroundMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundMusicPlayer.play();
            }
        }).start();
    }
    private File[] getSongs(){
        File song1 = new File("src/assets/sounds/music/modify_my_brain.mp3");
        File song2 = new File("src/assets/sounds/music/crystaline.mp3");
        return new File[] {song1, song2};
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

    private void controlGame(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                if (playerY > 0) {
                    try {
                        Game.getPlayer().move(Direction.UP);
                        playerY = Game.getPlayer().getPlayerPosition().y;
                    }
                    catch(Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_S:
                if (playerY < rows - 1) {
                    try {
                        Game.getPlayer().move(Direction.DOWN);
                        playerY = Game.getPlayer().getPlayerPosition().y;
                    }
                    catch(Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_A:
                if (playerX > 0) {
                    try {
                        Game.getPlayer().move(Direction.LEFT);
                        playerX = Game.getPlayer().getPlayerPosition().x;
                    }
                    catch(Exception e) {
                        System.err.println("Fehler: " + e.getMessage());
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (playerX < cols - 1) {
                    try {
                        Game.getPlayer().move(Direction.RIGHT);
                        playerX = Game.getPlayer().getPlayerPosition().x;
                    }
                    catch(Exception e) {
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
        player.setPlayerPosition(new Position(playerX,playerY));
        updateGamefield();
    }

    private void drawGameField(Graphics graphics) {
       int shadowOffsetx = 0;
       int shadowOffsety = 10;
        Map<Position, Upgrades> upgrades = Game.currentlevel.upgrades;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                graphics.drawImage(tiles.get(new Position(x,y)).getImage(),x*tileSize,y*tileSize,tileSize,tileSize,null); //draw Tile
                if(upgrades.get(new Position(x,y)) == null) continue;
                graphics.drawImage(MapUpgrade.getImage(),x*tileSize,y*tileSize,tileSize,tileSize,null); //draw Upgradeshadow
                graphics.drawImage(MapUpgrade.getImage(upgrades.get(new Position(x,y))),x*tileSize-shadowOffsetx,y*tileSize-shadowOffsety,tileSize,tileSize,null);//draw Upgrade
            }
        }

        Position pos = this.player.getPlayerPosition();
        graphics.drawImage(player.getPlayerIMG(),pos.x*tileSize,pos.y*tileSize,tileSize,tileSize,null); //draw player
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().upUpgrade), pos.x * tileSize-shadowOffsetx, pos.y * tileSize-shadowOffsety,tileSize , tileSize, 0); //Upgrade Up
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().rightUpgrade), pos.x * tileSize-shadowOffsetx, pos.y * tileSize-shadowOffsety, tileSize, tileSize, Math.PI*0.5); //Upgrade Right
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().downUpgrade), pos.x * tileSize-shadowOffsetx, pos.y * tileSize-shadowOffsety, tileSize, tileSize, Math.PI); //Upgrade Down
        drawRotatedImage(graphics, this.player.getUpgradeIMG(player.getPlayerUpgrades().leftUpgrade), pos.x * tileSize-shadowOffsetx, pos.y * tileSize-shadowOffsety, tileSize, tileSize, Math.PI*1.5); //Upgrade Left

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
