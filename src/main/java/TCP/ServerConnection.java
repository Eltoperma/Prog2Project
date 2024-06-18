package TCP;

import GameLogic.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerConnection {

    public static void main(String[] args) {

        Map<Integer, Direction> directions = new HashMap<>();
        Direction direction1 = new Direction(1, "UP");
        Direction direction2 = new Direction(2, "RIGHT");
        Direction direction3 = new Direction(3, "DOWN");
        Direction direction4 = new Direction(4, "LEFT");

        directions.put(direction1.getMove(), direction1);
        directions.put(direction2.getMove(), direction2);

        int port = 4242; // Port-Nummer

        try (ServerSocket server = new ServerSocket(port)){

            System.out.println("Server gestartet!");

            while (true) {

                try (Socket socket = server.accept()) { // try-with-resources, Auf Verbindung warten, Methode blockiert
                    //socket.setSoTimeout(5000);

                    BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Inputstream vom Client
                    PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true); // Outputstream zum Client mit autoflush

                    String line = socketIn.readLine();

                    int move = 0;
                    try {
                        move = Integer.parseInt(line);
                        System.out.println("Movement:" + move);
                        socketOut.println(directions.get(move));

                    } catch (NumberFormatException e) {
                        socketOut.println(e.getMessage());
                    }

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }

                System.out.println("Warte auf n�chste Anfrage!");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
