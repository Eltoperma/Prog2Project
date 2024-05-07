//import jaco.mp3.player.MP3Player;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.File;
//import java.net.URI;
//
//public class GameWindow extends JFrame {
//    private int rows = 10; // Initial number of rows
//    private int cols = 10; // Initial number of columns
//    private int playerX = 0; // X-GameLogic.Position of the player
//    private int playerY = 0; // Y-GameLogic.Position of the player
//
//    private int tileSize; // Size of each tile
//
//    public GameWindow() {
//        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
//        setIconImage(icon.getImage());
//        setTitle("UDLR Modify");
//        setSize(800, 600); // Initial window size
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // Centers the window
//        MP3Player mp3 = new MP3Player();
//        JPanel gamePanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                drawGameField(g);
//            }
//
//            @Override
//            public Dimension getPreferredSize() {
//                return new Dimension(cols * tileSize, rows * tileSize);
//            }
//        };
//        gamePanel.setBackground(Color.BLACK);
//        add(gamePanel, BorderLayout.CENTER);
//
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                updateTileSize(gamePanel);
//                gamePanel.repaint();
//            }
//        });
//
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                movePlayer(keyCode);
//            }
//        });
//
//        updateTileSize(gamePanel); // Initialize tile size
//    }
//    private void updateTileSize(JPanel gamePanel) {
//        int width = gamePanel.getWidth();
//        int height = gamePanel.getHeight();
//
//        int tileWidth = width / cols;
//        int tileHeight = height / rows;
//        tileSize = Math.min(tileWidth, tileHeight);
//    }
//
//    private void movePlayer(int keyCode) {
//        switch (keyCode) {
//            case KeyEvent.VK_W:
//                if (playerY > 0) {
//                    playerY--;
//                }
//                break;
//            case KeyEvent.VK_S:
//                if (playerY < rows - 1) {
//                    playerY++;
//                }
//                break;
//            case KeyEvent.VK_A:
//                if (playerX > 0) {
//                    playerX--;
//                }
//                break;
//            case KeyEvent.VK_D:
//                if (playerX < cols - 1) {
//                    playerX++;
//                }
//                break;
//        }
//        repaint(); // Update the window after the player moves
//    }
//
//    private void drawGameField(Graphics player) {
//        // Draw the game field
//        for (int y = 0; y < rows; y++) {
//            for (int x = 0; x < cols; x++) {
//                player.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
//            }
//        }
//
//        // Draw the player
//        player.setColor(Color.DARK_GRAY);
//        player.fillOval(playerX * tileSize, playerY * tileSize, tileSize, tileSize);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GameWindow window = new GameWindow();
//            window.setVisible(true);
//        });
//    }
//}
import jaco.mp3.player.MP3Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class GameWindow extends JFrame {
    private JButton toggleMusicButton;
    private MP3Player backgroundMusicPlayer;
    private URL[] playlist = new URL[2]; // Example array of URLs for the playlist
    private int currentSongIndex = 0;

    public GameWindow() {
        super("Game Window");

        try {
            // Initialize the playlist with URLs of songs
            playlist[0] = new URL("file:///C:/Users/thueter/IdeaProjects/Prog2Project/src/assets/sounds/music/crystaline.mp3");
            playlist[1] = new URL("file:///C:/Users/thueter/IdeaProjects/Prog2Project/src/assets/sounds/music/modify_my_brain.mp3");

            // Initialize the background music player with the first song
            backgroundMusicPlayer = new MP3Player(playlist[currentSongIndex]);
            backgroundMusicPlayer.setRepeat(true); // Loop the background music
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create UI components
        toggleMusicButton = new JButton("Toggle Music");

        // Add action listener to the toggle music button
        toggleMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMusic();
            }
        });

        // Set layout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(toggleMusicButton);

        // Set frame properties
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        // Start playing background music
        playBackgroundMusic();
    }

    private void playBackgroundMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                backgroundMusicPlayer.play();
            }
        }).start();
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
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }
}
