package model;

import GameLogic.Upgrades;

import java.io.Serializable;

public class UpgradeModel implements Serializable {
    public Upgrades upUpgrade;
    public Upgrades downUpgrade;
    public Upgrades leftUpgrade;
    public Upgrades rightUpgrade;

}
