import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

public class GameWindow extends JFrame {
    private int rows = 10; // Initial number of rows
    private int cols = 10; // Initial number of columns
    private int playerX = 0; // X-Position of the player
    private int playerY = 0; // Y-Position of the player

    private int mouseX, mouseY;
    private int tileSize; // Size of each tile

    public GameWindow() {
        setTitle("Game Window");
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
        switch (keyCode) {
            case KeyEvent.VK_W:
                if (playerY > 0) {
                    playerY--;
                }
                break;
            case KeyEvent.VK_S:
                if (playerY < rows - 1) {
                    playerY++;
                }
                break;
            case KeyEvent.VK_A:
                if (playerX > 0) {
                    playerX--;
                }
                break;
            case KeyEvent.VK_D:
                if (playerX < cols - 1) {
                    playerX++;
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
