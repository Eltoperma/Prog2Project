import GameLogic.Game;
import Level.*;

import javax.swing.*;
import java.util.ArrayList;


public class GameHandler {

    static Levels levels;
    static ArrayList<Level> levelList;
    static Game game;

    public static void init(){
        levels = new Levels();
        levelList = levels.levelList;
        game = new Game();
        game.setCurrentLevel(new Level_1());
//        SwingUtilities.invokeLater(() -> {
//            GameWindow window = new GameWindow();
//            window.setVisible(true);
//        });
    }
}
