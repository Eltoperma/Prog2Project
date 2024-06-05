package NetworkLogic;


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

public class UserDataService {
    private static final String FILE_PATH = "userData.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Speichert eine Liste von User-Objekten in einer JSON-Datei
    // Fügt einen neuen Benutzer zur bestehenden Liste hinzu und speichert sie in der JSON-Datei
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
    public static User authenticate(String username, String password) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // Beispiel zum Speichern und Laden von Benutzern
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
        saveUsers(users);

        // Beispiel zur Authentifizierung
        User authenticatedUser = authenticate("user1", "password1");
        if (authenticatedUser != null) {
            System.out.println("Benutzer authentifiziert: " + authenticatedUser.getUsername());
        } else {
            System.out.println("Authentifizierung fehlgeschlagen.");
        }
    }
}

