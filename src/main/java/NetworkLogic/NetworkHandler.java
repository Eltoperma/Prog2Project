package NetworkLogic;

import GameData.User;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NetworkHandler {
    Scanner scanner = new Scanner(System.in);
    User user;

    public void init() {
        try {
            if (isRegistered()) {
                user = login();
            } else {
                register();
                user = login();
            }
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

            String password = askPassword();

//            scanner.nextLine();
            User user = new User(username.trim(), password.trim());
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

            System.out.println("Dein Passwort:");
            String password = scanner.nextLine();

            User user = fetchUser(username, password);
            if(user == null) throw new RuntimeException("Dieser Nutzer existiert nicht!");
            return user;
        } catch (Exception e) {
            System.err.println("Etwas scheint nicht funktioniert zu haben: " + e.getMessage());
            login();
            throw new RuntimeException(e.getMessage());
        }
    }

    public User fetchUser(String username, String password) {
        try {
            User user = UserDataService.authenticate(username, password);
            return user;
        } catch (Exception e) {
            System.err.println("Fehler beim Laden des Benutzers!");
            throw new RuntimeException(e.getMessage());
        }
    }
}
