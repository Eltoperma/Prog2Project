package GameLogic;

import DrawLogic.GameWindow;
import GameData.LevelData;
import GameData.LevelUserData;
import Level.*;
import dataLogic.DataHandler;
import model.GameModel;
import model.ModelHandler;
import NetworkLogic.NetworkHandler;

import javax.swing.*;


import java.util.ArrayList;
import java.util.Scanner;


public class GameHandler {

    static Levels levels;
    static ArrayList<Level> levelList;
    static Game game;
    static int levelNo = 1;
    private static DataHandler dataHandler;
    private static ModelHandler modelHandler;
    private static NetworkHandler networkHandler;
    private static Scanner scanner;

    public static void init(){

        dataHandler = new DataHandler();
        game = new Game();

        new Levels();

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        game.setCurrentLevel(level);

        game.addPlayer();
        scanner = new Scanner(System.in);

        modelHandler = new ModelHandler();
//        if(isHost()){
        modelHandler.setHost(true);
//        }
//        else if(isSpectator()){
//            modelHandler.setSpectator(true);
//        }

        modelHandler.initGameState(game);

        networkHandler = new NetworkHandler(modelHandler.isHost(), modelHandler.isSpectator());

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow(modelHandler.getGameModel());
            window.setVisible(true);
        });
    }

    private static boolean isHost() {
        System.out.println("Wollen sie ein Spiel hosten?");
        int input = scanner.nextInt();
        scanner.nextLine();
        return input == 1;
    }

    private static boolean isSpectator() {
        System.out.println("Wollen sie ein Spiel beobachten?");
        int input = scanner.nextInt();
        scanner.nextLine();
        return input == 1;
    }

    public static void initLvl(){

        Level level = Levels.getLevel(levelNo - 1);
        level.configure();
        game.setCurrentLevel(level);

        game.addPlayer();

        modelHandler.updateGameState(game);

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
            updateGameModel(game);
        }
    }

    public static void lastGame(){
        if(levelNo > 0){
            levelNo--;
            GameHandler.initLvl();
            updateGameModel(game);
        }
    }

    public static void resetGame(){
        GameHandler.initLvl();
        updateGameModel(game);
    }


    public static void savePersonalHighScore(Level level, int score) {
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

    public static void updateGameModel(Game game){
        GameModel gameModel = modelHandler.updateGameState(game);
        networkHandler.updateGameState(gameModel);
    }

    public static void move(Direction dir){
        game.player.move(dir);
    }
}
