import GameLogic.GameController;
import dataLogic.DataHandler;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1");
        DataHandler dataHandler = new DataHandler();
        dataHandler.init();
        GameController.init();
    }
}