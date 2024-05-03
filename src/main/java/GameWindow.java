import GameLogic.Player;
import GameLogic.Position;
import GameLogic.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JFrame {
    private int rows = 10; // Initial number of rows
    private int cols = 10; // Initial number of columns

    private Position playerPosition = new Position();
    private Player player = new Player(playerPosition,new Upgrade());
    private int tileSize; // Size of each tile

    public GameWindow() {
        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        setTitle("UDLR Modify");
        setSize(800, 600); // Initial window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window

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
    private void updateTileSize(JPanel gamePanel) {
        int width = gamePanel.getWidth();
        int height = gamePanel.getHeight();

        int tileWidth = width / cols;
        int tileHeight = height / rows;
        tileSize = Math.min(tileWidth, tileHeight);
    }

    private void movePlayer(int keyCode) {
        Position pos = player.getPlayerPosition();
        switch (keyCode) {
            case KeyEvent.VK_W:
                if (pos.y > 0) {
                    player.setPlayerPosition(new Position(pos.x,pos.y-1));
                }
                break;
            case KeyEvent.VK_S:
                if (pos.y < rows - 2) {
                    player.setPlayerPosition(new Position(pos.x,pos.y+1));
                }
                break;
            case KeyEvent.VK_A:
                if (pos.x > 0) {
                    player.setPlayerPosition(new Position(pos.x-1,pos.y));
                }
                break;
            case KeyEvent.VK_D:
                if (pos.x < cols - 2) {
                    player.setPlayerPosition(new Position(pos.x+1,pos.y));
                }
                break;
        }
        repaint();
    }

    private void drawGameField(Graphics player) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                player.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }

        // Draw the player
        Position pos = this.player.getPlayerPosition();
        player.drawImage(this.player.getPlayerIMG(),pos.x*tileSize,pos.y*tileSize,tileSize,tileSize,null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
