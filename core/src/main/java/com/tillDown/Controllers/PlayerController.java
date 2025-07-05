package com.tillDown.Controllers;

import com.tillDown.Main;
import com.tillDown.Models.Bullet;
import com.tillDown.Models.Player;

public class PlayerController {
    private Player player;
    public PlayerController(Player player) {
        this.player = player;
    }

    public void checkPlayerCollision(Bullet b) {
        if (EnemiesController.rectangleOverlap(Main.getGameView().getController().getPlayer().getBounds(),b.getBounds())) {
            Main.getGameView().getController().getPlayer().getDamage();
            b.setPlayerCollided(true);
        }
    }

    public void update(float delta) {
        EnemiesController.checkEnemyCollision(player);
        OrbsController.checkPlayerCollision(player);
        player.update(delta);
        player.draw(Main.getBatch());
    }

    public Player getPlayer() {return player;}

}
