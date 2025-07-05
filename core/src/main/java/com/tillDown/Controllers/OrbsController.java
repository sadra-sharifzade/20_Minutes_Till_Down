package com.tillDown.Controllers;

import com.badlogic.gdx.math.Rectangle;
import com.tillDown.Main;
import com.tillDown.Models.Orb;
import com.tillDown.Models.Player;

import java.util.ArrayList;

public class OrbsController {
    private static ArrayList<Orb> orbs = new ArrayList<>();
    private static ArrayList<Orb> removedOrbs = new ArrayList<>();

    public OrbsController(boolean isNew) {
        if (!isNew) return;
        orbs.clear();
        removedOrbs.clear();
    }

    public static ArrayList<Orb> getOrbs() {return orbs;}

    public static void setOrbs(ArrayList<Orb> orbs) {OrbsController.orbs = orbs;}

    public void update() {
        for (Orb orb : orbs) {
            orb.draw(Main.getBatch());
        }
        orbs.removeAll(removedOrbs);
        removedOrbs.clear();
    }

    public static void addOrb(Orb orb) {
        orbs.add(orb);
    }
    public static void checkPlayerCollision(Player player) {
        for (Orb orb : orbs) {
            if (Math.abs(orb.getBounds().getX() + orb.getBounds().getWidth() / 2 - player.getBounds().getX() -
                             player.getBounds().getWidth() / 2) < 40 && Math.abs(
                orb.getBounds().getY() + orb.getBounds().getHeight() / 2 - player.getBounds().getY() -
                    player.getBounds().getHeight() / 2) < 40) {
                removedOrbs.add(orb);
                player.addXp(3);
            }
        }
    }

}
