package AssetManager;

import GameLogic.Upgrades;

import javax.swing.*;
import java.awt.*;

public  class MapUpgrade {
    public static Image getImage(Upgrades upgrade){
        ImageIcon img;
        switch (upgrade){
            case ONE -> {
                img = new ImageIcon("src/assets/upgrades/move1_upgrade.png");
            }
            case TWO -> {
                img = new ImageIcon("src/assets/upgrades/move2_upgrade.png");
            }
            case THREE -> {
                img = new ImageIcon("src/assets/upgrades/move3_upgrade.png");
            }
            case PLACEHOLDER -> {
                img = new ImageIcon("src/assets/upgrades/placeholder_upgrade.png");
            }
            case NONE -> {
                img = new ImageIcon("src/assets/upgrades/remove_upgrade.png");
            }
            default -> throw new RuntimeException("Error getting Map upgrade");
        }
        return img.getImage();
    }
}
