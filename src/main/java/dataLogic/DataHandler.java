package dataLogic;

import GameData.LevelData;
import GameData.LevelUserData;
import GameData.User;
import model.LevelModel;

public class DataHandler {
    private User user;

    public void setUser(User user){
        this.user = user;
    }
    public void login(String username) {
        try {
            User user = UserDataService.authenticate(username);
            if (user == null) throw new RuntimeException("Benutzername wurde nicht gefunden!");
            setUser(user);
            user.setUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public LevelData fetchLevelData(int levelId) {
        try {
            LevelData levelData = LevelDataService.loadLevelData(levelId);
            System.out.println("LevelData: " + levelData.getLevelId() + " Highscore: " + levelData.getHighscore());
            return levelData;
        } catch (Exception e) {
            throw new RuntimeException("fetchLevelDataError" + e.getMessage());
        }
    }

    public void saveLevelUserData(LevelModel levelModel, int score) {
        try {
            LevelUserData levelUserData = new LevelUserData(true, score);
            UserDataService.saveLevelUserData(user, levelModel, levelUserData);
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern von LevelUserData: " + e.getMessage());
        }
    }

    public LevelUserData fetchLevelUserData(int levelId) {
        try {
            LevelUserData levelUserData = UserDataService.loadLevelUserData(levelId, user);
            return levelUserData;
        } catch (NullPointerException npe) {
            return new LevelUserData(false, 0);
        } catch (Exception e) {
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
