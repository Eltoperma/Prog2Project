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


    private MP3Player backgroundMusicPlayer;
    private GamePanel gamePanel;


    public GameWindow() {
        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        setTitle("UDLR Modify");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundMusicPlayer = new MP3Player(getSongs());
        backgroundMusicPlayer.setRepeat(true);

        setBackground(new Color(51,51,51));
        gamePanel=  new GamePanel();

        add(gamePanel);
        pack();
        playBackgroundMusic();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                controlWindow(keyCode);
            }
        });


    }
    private void controlWindow(int keyCode){
        switch (keyCode) {
            case KeyEvent.VK_M:{
                toggleMusic();
            }
            break;
            case KeyEvent.VK_COMMA:
                backgroundMusicPlayer.skipForward();
                break;
            default:
                gamePanel.controlGame(keyCode);
        }
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
