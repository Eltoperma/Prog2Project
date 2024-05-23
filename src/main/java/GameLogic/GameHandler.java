package GameLogic;

import DrawLogic.GameWindow;
import Level.*;
import javax.swing.*;


import java.util.ArrayList;


public class GameHandler {

    static Levels levels;
    static ArrayList<Level> levelList;
    static Game game;
    static int levelNo = 1;

    public static void init(){
        //System.out.println("init");
//        game = new Game();

        game = new Game();
        new Levels();

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        game.setCurrentLevel(level);

        game.addPlayer();

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(game);
            window.setVisible(true);
        });
    }

    public static void initLvl(){
        //System.out.println("init");
//        game = new Game();

        game = new Game();

        new Levels();

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        game.setCurrentLevel(level);

        game.addPlayer();

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
        if(levelNo < Levels.levelList.size()) {
            levelNo++;
            //ladebildschirm
            GameHandler.initLvl();

        }
    }

    public static void lastGame(){
        if(levelNo > 0){
            levelNo--;
            GameHandler.initLvl();
        }
    }

    public static void resetGame(){
        GameHandler.initLvl();
    }




}
