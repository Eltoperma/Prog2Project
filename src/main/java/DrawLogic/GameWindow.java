package DrawLogic;


import GameLogic.*;
import jaco.mp3.player.MP3Player;
import model.GameModel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class GameWindow extends JFrame {


    private MP3Player backgroundMusicPlayer;
    private DrawPanel gamePanel;
    private GameModel gameModel;

    public GameWindow(GameModel gameModel) {
        this.gameModel = gameModel;
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
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                controlWindow(keyCode);
            }
        });
    }

    private void controlWindow(int keyCode) {
        if(!gamePanel.isAnimationFinished) return;
        Position from = gameModel.getPlayerModel().getPlayerPosition();
        Position to;
        switch (keyCode) {
            case KeyEvent.VK_M: {
                toggleMusic();
            }
            break;
            case KeyEvent.VK_COMMA:
                backgroundMusicPlayer.skipForward();
                break;
            case KeyEvent.VK_P:
                GameController.nextGame();
                gamePanel.refetchPlayer();
                gamePanel.recalculateDimensions();
                //gamePanel.resetDrawEngine();
                break;
            case KeyEvent.VK_O:
                GameController.lastGame();
                gamePanel.refetchPlayer();
                gamePanel.recalculateDimensions();
                //gamePanel.resetDrawEngine();
                break;
            case KeyEvent.VK_R:
                GameController.resetGame();
                gamePanel.refetchPlayer();
                gamePanel.recalculateDimensions();
                break;
            case KeyEvent.VK_W:
                GameController.move(Direction.UP);
                to = gameModel.getPlayerModel().getPlayerPosition();
                System.out.println("GameWindow Move: from = " + from.x + " " + from.y + " to " + to.x + " " + to.y);
                gamePanel.movePlayer(from, to);
                break;
            case KeyEvent.VK_D:
                GameController.move(Direction.RIGHT);
                to = gameModel.getPlayerModel().getPlayerPosition();
                gamePanel.movePlayer(from, to);
                break;
            case KeyEvent.VK_S:
                GameController.move(Direction.DOWN);
                to = gameModel.getPlayerModel().getPlayerPosition();
                gamePanel.movePlayer(from, to);
                break;
            case KeyEvent.VK_A:
                GameController.move(Direction.LEFT);
                to = gameModel.getPlayerModel().getPlayerPosition();
                gamePanel.movePlayer(from, to);
                break;
        }
        //Wait for Animation to finish
        Timer waiter = new Timer(180, e -> {
            System.out.println("TimerStarted");
            if(gamePanel.isAnimationFinished){
                System.out.println("Condition Met");
                GameController.nextGame();
                gamePanel.refetchPlayer();
                gamePanel.recalculateDimensions();
            }
        });
        if(gameModel.isFinished()){
            waiter.setRepeats(false);
            waiter.start();
        }


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
}