import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameHandler.init();

        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }

}