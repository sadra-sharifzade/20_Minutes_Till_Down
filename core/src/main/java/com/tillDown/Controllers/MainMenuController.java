package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Main;
import com.tillDown.Models.*;
import com.tillDown.Views.GameView;
import com.tillDown.Views.MainMenuView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
    }

    public void resumeLastGame(Skin skin, Stage stage) {
        User user = Main.getCurrentUser();
        ObjectMapper mapper = Main.getMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            int id = user.getId();
            Weapon weapon = mapper.readValue(new File("userData/"+id+"/weapon.json"), Weapon.class);
            weapon.reInitialize();
            Player player = mapper.readValue(new File("userData/"+id+"/player.json"), Player.class);
            player.reInitialize(weapon);
            EnemyListWrapper wrapper = mapper.readValue(new File("userData/"+id+"/enemies.json"), EnemyListWrapper.class);
            EnemiesController.setEnemies(wrapper.getEnemies());
            for (Enemy e : EnemiesController.getEnemies()) e.reInitialize();
            ArrayList<Orb> orbs =mapper.readValue(new File("userData/"+id+"/orbs.json"), new TypeReference<ArrayList<Orb>>() {});
            for (Orb o : orbs) o.reInitialize();
            ArrayList<Bullet> playerBullets = mapper.readValue(new File("userData/"+id+"/playerBullets.json"), new TypeReference<ArrayList<Bullet>>() {});
            for (Bullet b : playerBullets) b.reInitialize();
            ArrayList<Bullet> enemyBullets = mapper.readValue(new File("userData/"+id+"/enemyBullets.json"), new TypeReference<ArrayList<Bullet>>() {});
            for (Bullet b: enemyBullets) b.reInitialize();
            OrbsController.setOrbs(orbs);
            WeaponController.setEnemyBullets(enemyBullets);
            WeaponController.setPlayerBullets(playerBullets);
            if (Main.getGameView()!=null) {Main.setRemainingTime(Main.getGameView().getRemainingTime());}
            Main.setGameView(new GameView(player,weapon,false));
            Main.getMain().setScreen(Main.getGameView());
        } catch (IOException e) {
            Dialog errorDialog = new Dialog("Error", skin);
            errorDialog.text("You Don't Have A Saved Game");
            TextButton okButton = new TextButton("OK", skin);
            okButton.getLabel().setFontScale(0.65f);  // Scale text inside the button
            okButton.getLabel().setAlignment(Align.center);
            errorDialog.button(okButton, true);
            errorDialog.pad(50); // Add padding inside dialog
            errorDialog.pack(); // Recalculate size
            errorDialog.setPosition(
                (Gdx.graphics.getWidth() - errorDialog.getWidth()) / 2,
                (Gdx.graphics.getHeight() - errorDialog.getHeight()) / 2
            );
            errorDialog.show(stage);
        }
    }

}
