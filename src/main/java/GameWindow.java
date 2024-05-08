import AssetManager.Tile;
import GameLogic.Direction;
import GameLogic.Game;
import GameLogic.Position;
import GameLogic.Upgrades;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.util.Map;

public class GameWindow extends JFrame {

    Game game;
    public Map<Position, Tile> tiles;               //spielbare Fläche
    public Map<Position, Upgrades> upgrades;
    private int rows = 10; // Initial number of rows
    private int cols = 10; // Initial number of columns
    private int playerX = 0; // X-GameLogic.Position of the player
    private int playerY = 0; // Y-GameLogic.Position of the player

    private int tileSize; // Size of each tile


    public GameWindow() {
        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        setTitle("UDLR Modify");
        setSize(800, 600); // Initial window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window

        fetchDataFromGame();

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
                movePlayer(keyCode);
            }
        });

        updateTileSize(gamePanel); // Initialize tile size
    }

    private void fetchDataFromGame() {
//        game = GameHandler.getGame();
        rows = Game.getCurrentlevel().height;
        cols = Game.getCurrentlevel().width;
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

    private void movePlayer(int keyCode) {
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
        }
        repaint(); // Update the window after the player moves
    }

    private void drawGameField(Graphics player) {
        // Draw the game field
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                player.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);

            }
        }

        // Draw the player
        player.setColor(Color.DARK_GRAY);
        player.fillOval(playerX * tileSize, playerY * tileSize, tileSize, tileSize);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
