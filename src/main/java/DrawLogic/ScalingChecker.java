package DrawLogic;

import javax.swing.*;
import java.awt.*;

public class ScalingChecker {
    public void isScalingOn(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double screenWidth = toolkit.getScreenSize().getWidth();
        int screenResolution = toolkit.getScreenResolution();
        double dpi = screenWidth / (screenWidth / screenResolution);

        if (dpi > 96) {
            JDialog dialog = new JDialog(frame, "Achtung", true);
            dialog.setLayout(new GridBagLayout());

            JLabel label = new JLabel("Schalt Windows Scaling aus, um eine bessere Spielerfahrung zu erfahren");
            JButton button = new JButton("Ok");

            GridBagConstraints gbcLabel = new GridBagConstraints();
            gbcLabel.gridx = 0;
            gbcLabel.gridy = 0;
            gbcLabel.anchor = GridBagConstraints.CENTER;
            gbcLabel.insets = new Insets(10, 10, 10, 10); // Adding padding
            gbcLabel.fill = GridBagConstraints.HORIZONTAL; // Allow label to wrap text

            dialog.add(label, gbcLabel);

            GridBagConstraints gbcButton = new GridBagConstraints();
            gbcButton.gridx = 0;
            gbcButton.gridy = 1;
            gbcButton.anchor = GridBagConstraints.CENTER;

            dialog.add(button, gbcButton);

            dialog.setSize(440, 150);
            dialog.setLocationRelativeTo(frame);

            button.addActionListener(e -> {
                dialog.dispose();
            });

            dialog.setVisible(true);
        }
    }
}