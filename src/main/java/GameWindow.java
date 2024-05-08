import AssetManager.Tile;
import GameLogic.Direction;
import GameLogic.Game;


import GameLogic.Player;
import GameLogic.Position;
import GameLogic.Upgrade;
import GameLogic.Upgrades;
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
    private int rows = 10; // Initial number of rows
    private int cols = 10; // Initial number of columns
    private Player player;
    private int playerX = 0; // X-GameLogic.Position of the player
    private int playerY = 0; // Y-GameLogic.Position of the player
    private int tileSize; // Size of each tile

    private MP3Player backgroundMusicPlayer;


    public GameWindow() {
        //set Window Icon
        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        //set Window title
        setTitle("UDLR Modify");
        // Initial window size
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centers the window
        setLocationRelativeTo(null);


        fetchDataFromGame();

        try {
            backgroundMusicPlayer = new MP3Player(getSongs());
            backgroundMusicPlayer.setRepeat(true); // Loop the background music
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel gamePanel = new JPanel() {
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

        tiles = Game.getCurrentlevel().tiles;
        upgrades = Game.getCurrentlevel().upgrades;
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
        repaint();
    }

    private void drawGameField(Graphics graphics) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {

                graphics.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);

            }
        }

        Position pos = this.player.getPlayerPosition();
        graphics.drawImage(player.getPlayerIMG(),pos.x*tileSize,pos.y*tileSize,tileSize,tileSize,null);
        drawRotatedImage(graphics, this.player.getUpgradeIMG(Upgrades.NONE), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, 0); //Upgrade 1
        drawRotatedImage(graphics, this.player.getUpgradeIMG(Upgrades.NONE), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, Math.PI*0.5); //Upgrade 2
        drawRotatedImage(graphics, this.player.getUpgradeIMG(Upgrades.NONE), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, Math.PI); //Upgrade 3
        drawRotatedImage(graphics, this.player.getUpgradeIMG(Upgrades.NONE), pos.x * tileSize, pos.y * tileSize, tileSize, tileSize, Math.PI*1.5); //Upgrade 4

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
