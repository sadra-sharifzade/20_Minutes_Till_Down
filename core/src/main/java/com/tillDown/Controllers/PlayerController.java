package com.tillDown.Controllers;

import com.tillDown.Main;
import com.tillDown.Models.Player;

public class PlayerController {
    private final Player player;
    public PlayerController(Player player) {
        this.player = player;
    }
    public void update(float delta) {
        player.update(delta);
        player.draw(Main.getBatch());
    }

    public Player getPlayer() {return player;}
}
