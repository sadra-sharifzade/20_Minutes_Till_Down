package com.tillDown.Controllers;

import com.tillDown.Models.Player;
import com.tillDown.Models.Weapon;
import com.tillDown.Views.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WeaponController weaponController;
    private EnemiesController enemiesController;
    private BulletsController bulletsController;
    public GameController(GameView view, Player player, Weapon weapon) {
        this.view = view;
        playerController = new PlayerController(player);
        weaponController = new WeaponController(weapon,player);
        enemiesController = new EnemiesController(view.getCamera());
    }
    public void update(float delta) {
        playerController.update(delta);
        weaponController.update(delta);
        enemiesController.update(delta);
//        bulletsController.update();
    }
    public Player getPlayer() {
        return playerController.getPlayer();
    }

    public WeaponController getWeaponController() {return weaponController;}
}
