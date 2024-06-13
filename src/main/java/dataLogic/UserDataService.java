package dataLogic;


import GameData.LevelUserData;
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
import java.util.Map;
import java.io.File;

import Level.Level;

public class UserDataService {
    private static final String FILE_PATH = "data/user/userData.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Speichert eine Liste von User-Objekten in einer JSON-Datei
    // Fügt einen neuen Benutzer zur bestehenden Liste hinzu und speichert sie in der JSON-Datei

    static {
        // Überprüft, ob der Datei-Pfad existiert, und erstellt ihn gegebenenfalls
        createFileIfNotExists();
    }

    private static void createFileIfNotExists() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Erstelle das Verzeichnis, falls es nicht existiert
                file.createNewFile(); // Erstelle die Datei, falls sie nicht existiert
                // Initialisiere die Datei mit einer leeren Liste von Usern
                saveUsers(new ArrayList<>());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User newUser) {
        List<User> users = loadUsers();
        users.add(newUser);
        saveUsers(users);
    }

    // Speichert eine Liste von User-Objekten in einer JSON-Datei
    private static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lädt eine Liste von User-Objekten aus einer JSON-Datei
    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        catch (JsonSyntaxException jse){
            return new ArrayList<>();
        }
    }

    public static boolean userExists(String username){
        List<User> users = loadUsers();
        for (User loadedUser : users) {
            if (username.equals(loadedUser.getUsername())) {
                return true;
            }
        }
        return false;
    }

    // Authentifiziert einen Benutzer anhand von Benutzername und Passwort
    public static User authenticate(String username) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void saveLevelUserData(User user, Level level, LevelUserData levelUserData){
        Map<Integer, LevelUserData> levelUserDataMap = user.getLevelData();
        levelUserDataMap.put(level.ID, levelUserData);
        user.setLevelData(levelUserDataMap);

        editUserLevelData(user);
    }

    private static void editUserLevelData(User user) {
        User loadedUser = findUserByName(user.getUsername());
        loadedUser.setLevelData(user.getLevelData());
        saveUser(loadedUser);
        System.out.println(user.getLevelData());
    }

    private static void saveUser(User user) {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                saveUsers(users);
                return;
            }
        }
        throw new RuntimeException("Spieler konnte nicht gefunden werden!");
    }

    public static LevelUserData loadLevelUserData(int levelId, User user) {
        User loadedUser = findUserByName(user.getUsername());

        Map<Integer, LevelUserData> levelData = loadedUser.getLevelData();

        for (Map.Entry<Integer, LevelUserData> entry : levelData.entrySet()) {
            if (entry.getKey() == levelId) {
                return entry.getValue();
            }
        }
        throw new NullPointerException("Diese LevelID konnte nicht gefunden werden!");
    }
    private static User findUserByName(String username) {
        List<User> users = loadUsers();

        for(User loadedUser : users){
            if(loadedUser.getUsername().equals(username)){
                return loadedUser;
            }
        }
        throw new RuntimeException("Spieler konnte nicht gefunden werden!");
    }

    public static void main(String[] args) {
        // Beispiel zum Speichern und Laden von Benutzern
        List<User> users = new ArrayList<>();
//        users.add(new User("user1", "password1"));
//        users.add(new User("user2", "password2"));
        saveUsers(users);

        // Beispiel zur Authentifizierung
//        User authenticatedUser = authenticate("user1", "password1");
//        if (authenticatedUser != null) {
//            System.out.println("Benutzer authentifiziert: " + authenticatedUser.getUsername());
//        } else {
//            System.out.println("Authentifizierung fehlgeschlagen.");
//        }
    }

}

