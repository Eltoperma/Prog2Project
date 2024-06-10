package NetworkLogic;

import GameData.LevelData;
import GameData.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LevelDataService {
    private static final String FILE_PATH = "data/level/levelData.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Speichert eine Liste von LevelData-Objekten in einer JSON-Datei

    static {
        createFileIfNotExists();
        fillLevelsIfNotExist();
    }

    private static void fillLevelsIfNotExist() {
        List<LevelData> existingLevelData = loadAllLevelData();

        // Prüfe und füge Level hinzu, wenn sie nicht existieren
        for (int i = 1; i <= 20; i++) {
            final int levelId = i;
            boolean exists = existingLevelData.stream().anyMatch(ld -> ld.getLevelId() == levelId);
            if (!exists) {
                LevelData newLevelData = new LevelData(levelId, 0, null);
                existingLevelData.add(newLevelData);
            }
        }

        // Speichere die aktualisierte Level-Datenliste
        saveAllLevelData(existingLevelData);
    }

    private static void createFileIfNotExists() {
        try {
            java.io.File file = new java.io.File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Erstelle das Verzeichnis, falls es nicht existiert
                file.createNewFile(); // Erstelle die Datei, falls sie nicht existiert
                // Initialisiere die Datei mit einer leeren Liste von LevelData
                saveAllLevelData(new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveAllLevelData(List<LevelData> levelDataList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(levelDataList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLevelData(int levelId, User user, int highscore){
        try{
            List<LevelData> levelDataList = loadAllLevelData();
            LevelData levelData = new LevelData(levelId, highscore, user.getUsername());
            levelDataList.set(levelId-1, levelData);
            saveAllLevelData(levelDataList);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Lädt eine Liste von LevelData-Objekten aus einer JSON-Datei
    public static List<LevelData> loadAllLevelData() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type levelDataListType = new TypeToken<ArrayList<LevelData>>() {}.getType();
            return gson.fromJson(reader, levelDataListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (JsonSyntaxException jse) {
            return new ArrayList<>();
        }
    }

    public static LevelData loadLevelData(int levelId){
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type levelDataListType = new TypeToken<ArrayList<LevelData>>() {}.getType();
            List<LevelData> levelData = gson.fromJson(reader, levelDataListType);

            for(LevelData member : levelData){
                if(member.getLevelId() == levelId){
                    return member;
                }
            }
            throw new RuntimeException("Diese LevelID scheint es nicht zu geben!");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Level konnte nicht gefunden werden! " + e.getMessage());
        } catch (JsonSyntaxException jse) {
            throw new RuntimeException("Level konnte nicht gefunden werden! " + jse.getLocalizedMessage());
        }
    }

    // Fügt ein neues LevelData-Objekt zur bestehenden Liste hinzu und speichert es in der JSON-Datei
    public static void addLevelData(LevelData newLevelData) {
        List<LevelData> levelDataList = loadAllLevelData();
        levelDataList.add(newLevelData);
        saveAllLevelData(levelDataList);
    }

    // Aktualisiert die Highscore-Daten eines Levels
    public static void updateLevelData(LevelData updatedLevelData) {
        List<LevelData> levelDataList = loadAllLevelData();
        for (int i = 0; i < levelDataList.size(); i++) {
            if (levelDataList.get(i).getLevelId() == updatedLevelData.getLevelId()) {
                levelDataList.set(i, updatedLevelData);
                saveAllLevelData(levelDataList);
                return;
            }
        }
        throw new RuntimeException("Level konnte nicht gefunden werden!");
    }


    // Beispiel zur Veranschaulichung
    public static void main(String[] args) {
        // Beispiel zum Speichern und Laden von LevelData
        List<LevelData> levelDataList = new ArrayList<>();
        saveAllLevelData(levelDataList);

        // Beispiel zum Aktualisieren eines Highscores

        // LevelData laden und anzeigen
        List<LevelData> loadedLevelDataList = loadAllLevelData();
        for (LevelData levelData : loadedLevelDataList) {
            System.out.println("Level ID: " + levelData.getLevelId() + ", Highscore: " + levelData.getHighscore() + ", Best User: " + levelData.getBestUserName());
        }
    }


}