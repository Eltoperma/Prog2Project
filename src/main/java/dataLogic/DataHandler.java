package dataLogic;

import GameData.LevelData;
import GameData.LevelUserData;
import GameData.User;
import Level.Level;
import model.LevelModel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class DataHandler {
    private final Scanner scanner = new Scanner(System.in);
    private static User user;

    public void init() {
        try {
//            UserDataService.
            if (!isRegistered()) {
                register();
            }
            user = login();
        } catch (Exception e) {
            System.err.println("Fehler!");
        }
    }

    private boolean isRegistered() {
        //Frage Logik
        System.out.println("Bist du registriert? Ja (1) Nein (2):");
        try {
//            scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            scanner.nextLine(); //clears scanner

//            scanner.close();
            return input == 1;
        } catch (Exception e) {
            System.err.println("Ungültige Eingabe!");
            return isRegistered();
        }
    }

    public void register() throws InterruptedException {
        try {
            String username = askUsername();
            System.out.println("Eingegebener Benutzername: " + username);

            if(UserDataService.userExists(username)) throw new RuntimeException("Dieser Nutzer existiert bereits!");

//            scanner.nextLine();
            User user = new User(username.trim());
            UserDataService.addUser(user);
        } catch (Exception e) {
            System.err.println("Etwas scheint nicht funktioniert zu haben: " + e.getMessage());
            TimeUnit.MILLISECONDS.sleep(1000);
            register();
        }
    }

    public String askUsername(){
        System.out.println("Lege einen Benutzernamen fest:");
        return scanner.nextLine();
    }

    public String askPassword(){
        System.out.println("Lege ein Passwort fest:");
        return scanner.nextLine();
    }
    public User login() {
        try {
            System.out.println("Dein Benutzername:");
            String username = scanner.nextLine();

            System.out.println("Eingegebener Benutzername: " + username);


            User user = fetchUser(username);
            if(user == null) throw new RuntimeException("Benutzername wurde nicht gefunden!");
            System.out.println("Herzlich Willkommen " + user.getUsername() + "!");
            return user;
        } catch (Exception e) {
            System.err.println("Etwas scheint nicht funktioniert zu haben: " + e.getMessage());
            login();
            throw new RuntimeException(e.getMessage());
        }
    }

    public User fetchUser(String username) {
        try {
            User user = UserDataService.authenticate(username);
            return user;
        } catch (Exception e) {
            System.err.println("Fehler beim Laden des Benutzers!" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public LevelData fetchLevelData(int levelId){
        try{
            LevelData levelData = LevelDataService.loadLevelData(levelId);
            System.out.println("LevelData: " + levelData.getLevelId() + " Highscore: " + levelData.getHighscore());
            return levelData;
        }
        catch(Exception e){
            throw new RuntimeException("fetchLevelDataError" + e.getMessage());
        }
    }

    public void saveLevelUserData(LevelModel level, int score) {
        try{
            LevelUserData levelUserData = new LevelUserData(true, score);
            UserDataService.saveLevelUserData(user, level, levelUserData);
        }
        catch (Exception e){
            System.err.println("Fehler beim Speichern von LevelUserData: " + e.getMessage());
        }
    }

    public LevelUserData fetchLevelUserData(int levelId) {
        try{
            LevelUserData levelUserData = UserDataService.loadLevelUserData(levelId, user);
            return levelUserData;
        }
        catch(NullPointerException npe){
            return new LevelUserData(false, 0);
        }
        catch(Exception e){
            throw new RuntimeException("Level Daten konnten nicht geladen werden: " + e.getMessage());
        }
    }

    public void saveLevelData(int levelId, int highscore) {
        LevelDataService.saveLevelData(levelId, user, highscore);
    }

    public String getUsername(){
        return user.getUsername();
    }
}
