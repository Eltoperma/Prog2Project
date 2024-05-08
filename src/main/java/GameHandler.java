import GameLogic.Game;
import Level.*;
import javax.swing.*;


import java.util.ArrayList;


public class GameHandler {

    static Levels levels;
    static ArrayList<Level> levelList;
    static Game game;
    static int levelNo;

//    private GameHandler(){
//
//    }
    public static void init(){
        System.out.println("init");

        levelNo = 1;
//        game = new Game();

        new Levels();

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        Game.setCurrentLevel(level);

        Game.addPlayer();

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }

    public static Levels getLevels() {
        return levels;
    }

    public static ArrayList<Level> getLevelList() {
        return levelList;
    }

    public static Game getGame() {
        return game;
    }

    public static void nextGame(){
        levelNo++;
        //ladebildschirm
        Game.setCurrentLevel(Levels.getLevel(levelNo-1));
    }
}
