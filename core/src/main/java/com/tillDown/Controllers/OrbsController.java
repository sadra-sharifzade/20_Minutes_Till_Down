package com.tillDown.Controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public void update(){
        for (Orb orb : orbs) {
            orb.draw(Main.getBatch());
        }
        orbs.removeAll(removedOrbs);
        removedOrbs.clear();
    }
    public static void addOrb(Orb orb){
        orbs.add(orb);
    }
    public static void checkPlayerCollision(Player player){
        for (Orb orb : orbs) {
            if (orb.getBounds().overlaps(player.getBounds())){
                removedOrbs.add(orb);
                player.addXp(3);
            }
        }
    }

}
