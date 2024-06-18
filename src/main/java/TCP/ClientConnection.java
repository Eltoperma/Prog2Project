package TCP;

import model.GameModel:
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

public class ClientConnection {

    public static void main(String[] args) {

        int Move;

        String hostName = "localhost";
        int port = 4242;

        try (Socket socket = new Socket(hostName, port)){

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Server
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // Outputstream zum Server

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Movement:");
                Move = scanner.nextInt();
            }

            socketOut.println(Move);

            String text = socketIn.readLine(); // Zeile vom Server empfangen

            System.out.println(text); // Zeile auf die Konsole schreiben


        } catch (UnknownHostException ue) {
            System.out.println("Kein DNS-Eintrag fuer " + hostName);
        } catch (NoRouteToHostException e) {
            System.err.println("Nicht erreichbar " + hostName);
        } catch (IOException e) {
            System.out.println("IO-Error");
        }
    }
}
