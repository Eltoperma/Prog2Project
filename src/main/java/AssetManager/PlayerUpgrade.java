package AssetManager;

import GameLogic.Direction;
import GameLogic.Upgrades;

import javax.swing.*;
import java.awt.*;

public class PlayerUpgrade {
       public Image getImage(Upgrades upgrade,Direction direction ){
        ImageIcon img;
        switch (upgrade){
            case ONE -> {
                switch (direction){
                        case UP -> {
                            img = new ImageIcon("src/assets/player/upgrades/move1_north.png");
                        }
                        case DOWN -> {
                            img = new ImageIcon("src/assets/player/upgrades/move1_south.png");
                        }
                        case LEFT -> {
                            img = new ImageIcon("src/assets/player/upgrades/move1_west.png");
                        }
                        case RIGHT -> {
                            img = new ImageIcon("src/assets/player/upgrades/move1_east.png");
                        }
                        default -> {
                            throw new RuntimeException("Direction input for Player upgrade didnt match");
                        }
                }
            }
            case TWO -> {
                switch (direction){
                    case UP -> {
                        img = new ImageIcon("src/assets/player/upgrades/move2_north.png");
                    }
                    case DOWN -> {
                        img = new ImageIcon("src/assets/player/upgrades/move2_south.png");
                    }
                    case LEFT -> {
                        img = new ImageIcon("src/assets/player/upgrades/move2_west.png");
                    }
                    case RIGHT -> {
                        img = new ImageIcon("src/assets/player/upgrades/move2_east.png");
                    }
                    default -> {
                        throw new RuntimeException("Direction input for Player upgrade didnt match");
                    }
                }
            }
            case THREE -> {
                switch (direction){
                    case UP -> {
                        img = new ImageIcon("src/assets/player/upgrades/move3_north.png");
                    }
                    case DOWN -> {
                        img = new ImageIcon("src/assets/player/upgrades/move3_south.png");
                    }
                    case LEFT -> {
                        img = new ImageIcon("src/assets/player/upgrades/move3_west.png");
                    }
                    case RIGHT -> {
                        img = new ImageIcon("src/assets/player/upgrades/move3_east.png");
                    }
                    default -> {
                        throw new RuntimeException("Direction input for Player upgrade didnt match");
                    }
                }
            }
            case NONE -> {
                switch (direction){
                    case UP -> {
                        img = new ImageIcon("src/assets/player/upgrades/empty_north.png");
                    }
                    case DOWN -> {
                        img = new ImageIcon("src/assets/player/upgrades/empty_south.png");
                    }
                    case LEFT -> {
                        img = new ImageIcon("src/assets/player/upgrades/empty_west.png");
                    }
                    case RIGHT -> {
                        img = new ImageIcon("src/assets/player/upgrades/empty_east.png");
                    }
                    default -> {
                        throw new RuntimeException("Direction input for Player upgrade didnt match");
                    }
                }
            }
            case PLACEHOLDER -> {
                switch (direction){
                    case UP -> {
                        img = new ImageIcon("src/assets/player/upgrades/placeholder_north.png");
                    }
                    case DOWN -> {
                        img = new ImageIcon("src/assets/player/upgrades/placeholder_south.png");
                    }
                    case LEFT -> {
                        img = new ImageIcon("src/assets/player/upgrades/placeholder_west.png");
                    }
                    case RIGHT -> {
                        img = new ImageIcon("src/assets/player/upgrades/placeholder_east.png");
                    }
                    default -> {
                        throw new RuntimeException("Direction input for Player upgrade didnt match");
                    }
                }
            }
            default -> throw new RuntimeException("Error when deciding player upgrade type");
        }
        return img.getImage();
    }
}
