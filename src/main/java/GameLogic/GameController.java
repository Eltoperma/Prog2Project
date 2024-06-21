package GameLogic;

import DrawLogic.GameWindow;
import GameData.LevelData;
import GameData.LevelUserData;
import Level.*;
import dataLogic.DataHandler;
import model.LevelModel;
import model.ModelHandler;
import NetworkLogic.NetworkHandler;
import DrawLogic.LoginRegisterFrame;

import javax.swing.*;


import java.util.ArrayList;
import java.util.Scanner;


public class GameController {

    static Levels levels;
    static ArrayList<Level> levelList;
    static GameHandler gameHandler;
    static int levelNo = 1;
    private static DataHandler dataHandler;
    private static ModelHandler modelHandler;
    private static NetworkHandler networkHandler;
    private static Scanner scanner;

    public static void init(){
        dataHandler = new DataHandler();
        networkHandler = new NetworkHandler();
        SwingUtilities.invokeLater(() -> {
            LoginRegisterFrame registerFrame = new LoginRegisterFrame();
            registerFrame.setVisible(true);
        });
    }

    public static void initLvl(){

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        gameHandler.setCurrentLevel(level);

        gameHandler.addPlayer();

//        modelHandler.updateGameState(game);

    }

    public static Levels getLevels() {
        return levels;
    }

    public static ArrayList<Level> getLevelList() {
        return levelList;
    }

    public static GameHandler getGameHandler() {
        return gameHandler;
    }

    public static void nextGame(){
        if(levelNo < Levels.levelList.size()) {
            levelNo++;
            //ladebildschirm
            GameController.initLvl();
//            updateGameModel(game);
        }
    }

    public static void lastGame(){
        if(levelNo > 0){
            levelNo--;
            GameController.initLvl();
//            updateGameModel(game);
        }
    }

    public static void resetGame(){
        GameController.initLvl();
//        updateGameModel(game);
    }


    public static void savePersonalHighScore(LevelModel level, int score) {
        dataHandler.saveLevelUserData(level, score);
    }
    public static LevelData fetchLevelData(int levelId){
        return dataHandler.fetchLevelData(levelId);
    }

    public static void saveHighscore(int levelId, int highscore){
        dataHandler.saveLevelData(levelId, highscore);
    }

    public static LevelUserData fetchLevelUserData(int levelId) {
        return dataHandler.fetchLevelUserData(levelId);

    }

    public static NetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    public static ModelHandler getModelHandler() {
        return modelHandler;
    }

//    public static void updateGameModel(Game game){
//        GameModel gameModel = modelHandler.updateGameState(game);
//        networkHandler.updateGameState(gameModel);
//    }

    public static void move(Direction dir){
        gameHandler.getPlayerHandler().move(dir);
    }

    public static String fetchUsername(){
        return dataHandler.getUsername();
    }

    public static DataHandler getDataHandler() {
        return dataHandler;
    }

    public static void setDataHandler(DataHandler dataHandler) {
        GameController.dataHandler = dataHandler;
    }

    public static void openGameWindow(){

        new Levels();

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        gameHandler = new GameHandler(level);
        gameHandler.setCurrentLevel(level);

        gameHandler.addPlayer();
        scanner = new Scanner(System.in);

        modelHandler = new ModelHandler();
        modelHandler.setGameModel(gameHandler.getGameModel());

        networkHandler.startNetwork();

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(gameHandler.getGameModel());
            window.setVisible(true);
        });
    }
}
