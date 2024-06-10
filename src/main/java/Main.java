import GameLogic.GameHandler;
import NetworkLogic.DataHandler;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1");
        DataHandler dataHandler = new DataHandler();
        dataHandler.init();
        GameHandler.init();
    }
}