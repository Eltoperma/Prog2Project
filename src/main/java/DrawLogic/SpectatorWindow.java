package DrawLogic;

import jaco.mp3.player.MP3Player;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SpectatorWindow extends JFrame{

    private MP3Player backgroundMusicPlayer;
    private DrawPanel gamePanel;

    public SpectatorWindow(GameModel gameModel) {

        ImageIcon icon = new ImageIcon("src/assets/icons/Logo.png", "Logo");
        setIconImage(icon.getImage());
        setTitle("UDLR Modify");
        this.getContentPane().setBackground(Color.DARK_GRAY);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundMusicPlayer = new MP3Player(getSongs());
        backgroundMusicPlayer.setRepeat(true);

        setBackground(new Color(51, 51, 51));
        gamePanel = new DrawPanel(gameModel);
        gamePanel.setPreferredSize(new Dimension(1000, 1000));
        add(gamePanel);
        gamePanel.grabFocus();
        pack();
        playBackgroundMusic();
        toggleMusic();
    }

    private void playBackgroundMusic() {
        new Thread(() -> backgroundMusicPlayer.play()).start();
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

    public void updateGameModel(GameModel receivedGameModel) {
        System.out.println("UpdateGameModel: " + receivedGameModel.getCurrentScore());
        gamePanel.setGameModel(receivedGameModel);
        gamePanel.recalculateDimensions();
        gamePanel.refetchPlayer();
    }
}
