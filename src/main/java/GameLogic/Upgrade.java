package GameLogic;

import java.io.Serializable;

public class Upgrade implements Serializable {
    public Upgrades upUpgrade;
    public Upgrades downUpgrade;
    public Upgrades leftUpgrade;
    public Upgrades rightUpgrade;

    public Upgrade(){
        upUpgrade = Upgrades.NONE;
        downUpgrade = Upgrades.NONE;
        leftUpgrade = Upgrades.NONE;
        rightUpgrade = Upgrades.NONE;
    }

}
