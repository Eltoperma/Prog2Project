package DrawLogic;

import javax.swing.*;
import java.awt.Toolkit;

public class ScalingChecker {
    public void isScalingOn(JFrame frame){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double screenWidth = toolkit.getScreenSize().getWidth();
        int screenResolution = toolkit.getScreenResolution();
        double dpi = screenWidth / (screenWidth / screenResolution);

        if (dpi > 96) {
            JDialog dialog = new JDialog(frame, "Achtung",true);
            dialog.setSize(200, 100);
            dialog.setLocationRelativeTo(frame); // Center the dialog
            dialog.add(new JLabel("Es ist möglich das du Windows scaling an hast, bitte schalte es aus damit unser Spiel vernünftig gerendert werden kann :)"));
            dialog.setVisible(true);
        }
    }
}