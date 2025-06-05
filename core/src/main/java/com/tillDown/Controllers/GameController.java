package com.tillDown.Controllers;

import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.Player;
import com.tillDown.Models.User;
import com.tillDown.Models.Weapon;
import com.tillDown.Views.EndView;
import com.tillDown.Views.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WeaponController weaponController;
    private EnemiesController enemiesController;
    private OrbsController orbsController;
    public GameController(GameView view, Player player, Weapon weapon) {
        this.view = view;
        playerController = new PlayerController(player);
        weaponController = new WeaponController(weapon,player);
        enemiesController = new EnemiesController(view.getCamera());
        orbsController = new OrbsController();
    }
    public void update(float delta) {
        weaponController.update(delta);
        enemiesController.update(delta);
        orbsController.update();
        playerController.update(delta);
    }
    public Player getPlayer() {
        return playerController.getPlayer();
    }

    public WeaponController getWeaponController() {return weaponController;}

    public void killNearEnemies() {
        enemiesController.killEnemies(getPlayer().getX(),getPlayer().getY());
    }
    public void endGame(boolean won) {
        if (won) GameAssetManager.getGameAssetManager().playSound("win");
        else GameAssetManager.getGameAssetManager().playSound("lose");
        User currentUser = Main.getCurrentUser();
        int numKills = getPlayer().getNumKills();
        currentUser.setNumKills(currentUser.getNumKills()+numKills);
        currentUser.setScore(currentUser.getScore()+numKills*view.getElapsedTime());
        if (view.getElapsedTime()>currentUser.getMostTimeAlive()) currentUser.setMostTimeAlive(view.getElapsedTime());
        Main.getGameView().dispose();
        Main.getMain().setScreen(new EndView(won,numKills*view.getElapsedTime(),numKills, view.getElapsedTime()));
    }

    public PlayerController getPlayerController() {return playerController;}
}
