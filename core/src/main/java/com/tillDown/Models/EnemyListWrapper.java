package com.tillDown.Models;

import java.util.ArrayList;
import java.util.List;

public class EnemyListWrapper {
    public ArrayList<Enemy> enemies;

    public EnemyListWrapper() {} // No-arg constructor required

    public EnemyListWrapper(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Enemy> getEnemies() {return enemies;}
}

