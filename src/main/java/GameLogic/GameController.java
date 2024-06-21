package GameLogic;

import DrawLogic.GameWindow;
import GameData.LevelData;
import GameData.LevelUserData;
import Level.*;
import dataLogic.DataHandler;
import model.LevelModel;
import NetworkLogic.NetworkHandler;
import DrawLogic.LoginRegisterFrame;

import javax.swing.*;


import java.util.ArrayList;
import java.util.Scanner;


public class GameController {

    static GameHandler gameHandler;
    static int levelNo = 1;
    private static DataHandler dataHandler;
    private static NetworkHandler networkHandler;

    public static void init() {
        dataHandler = new DataHandler();
        networkHandler = new NetworkHandler();
        SwingUtilities.invokeLater(() -> {
            LoginRegisterFrame registerFrame = new LoginRegisterFrame();
            registerFrame.setVisible(true);
        });
    }

    public static void initLvl() {

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();

        gameHandler.setCurrentLevel(level);
        gameHandler.addPlayer();
    }

    public static GameHandler getGameHandler() {
        return gameHandler;
    }

    public static void nextLevel() {
        if (levelNo < Levels.levelList.size()) {
            levelNo++;
            GameController.initLvl();
        }
    }

    public static void recentLevel() {
        if (levelNo > 0) {
            levelNo--;
            GameController.initLvl();
        }
    }

    public static void resetLevel() {
        GameController.initLvl();
    }

    public static void savePersonalHighScore(LevelModel level, int score) {
        dataHandler.saveLevelUserData(level, score);
    }

    public static LevelData fetchLevelData(int levelId) {
        return dataHandler.fetchLevelData(levelId);
    }

    public static void saveHighscore(int levelId, int highscore) {
        dataHandler.saveLevelData(levelId, highscore);
    }

    public static LevelUserData fetchLevelUserData(int levelId) {
        return dataHandler.fetchLevelUserData(levelId);

    }

    public static NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public static void move(Direction dir) {
        gameHandler.getPlayerHandler().move(dir);
    }

    public static String fetchUsername() {
        return dataHandler.getUsername();
    }

    public static DataHandler getDataHandler() {
        return dataHandler;
    }

    public static void openGameWindow() {

        if (networkHandler.isHost()) {

            new Levels();

            Level level = Levels.getLevel(levelNo - 1);
            level.configure();
            gameHandler = new GameHandler(level);
            gameHandler.setCurrentLevel(level);

            gameHandler.addPlayer();

            networkHandler.startNetwork();

            SwingUtilities.invokeLater(() -> {
                GameWindow window = new GameWindow(gameHandler.getGameModel());
                window.setVisible(true);
            });
        } else {
            networkHandler.startNetwork();
        }
    }
}
