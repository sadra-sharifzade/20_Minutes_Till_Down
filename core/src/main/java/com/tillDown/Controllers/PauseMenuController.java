package com.tillDown.Controllers;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Main;
import com.tillDown.Models.*;
import com.tillDown.Views.PauseMenuView;

import java.io.File;
import java.io.IOException;

public class PauseMenuController {
    private PauseMenuView view;

    public PauseMenuController(PauseMenuView view) {
        this.view = view;
    }

    public void showCheatCodesInfo() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("CHEAT CODES INFO", skin);
        dialog.pad(20);
        dialog.getContentTable().add(new Label("KEY", skin)).center();
        dialog.getContentTable().add(new Label("CHEAT", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+1 ", skin)).center();
        dialog.getContentTable().add(new Label("Decreases Remaining Time By 1 Minute", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+2 ", skin)).center();
        dialog.getContentTable().add(new Label("Increases Level of Player By 1", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+3 ", skin)).center();
        dialog.getContentTable().add(new Label("Getting a HP (only if empty)", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+4 ", skin)).center();
        dialog.getContentTable().add(new Label("Go To Boss Fight", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+5 ", skin)).center();
        dialog.getContentTable().add(new Label("Killing Enemies Around You", skin)).center().row();
        dialog.button("OK", false);
        dialog.show(view.getStage());
    }

    public void showGainedAbilities() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("Gained Abilities", skin);
        dialog.pad(20);
        StringBuilder abilities = new StringBuilder();
        int cnt = 1;
        for (String i : Main.getGameView().getController().getPlayer().getGainedAbilities()) {
            abilities.append(i).append(", ");
            if (cnt++ % 3 == 0) abilities.append("\n");
        }
        dialog.getContentTable().add(new Label(abilities.toString(), skin)).center();
        dialog.button("OK", false);
        dialog.show(view.getStage());

    }

    public void setBlackAndWhite(boolean checked) {
        Main.setIsBlackAndWhiteEnabled(checked);
    }

    public void saveCurrentGame() {
        int id = Main.getCurrentUser().getId();
        ObjectMapper mapper = Main.getMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File directory = new File("userData/"+ id );
        if (!directory.exists()) directory.mkdir();
        try {
            Main.save("userData/"+Main.getCurrentUser().getId()+"/settings.json");
            Player player = Main.getGameView().getController().getPlayer();
            player.beforeSave();
            Weapon weapon = player.getWeapon();
            weapon.beforeSave();
            for (Enemy e: EnemiesController.getEnemies()) e.beforeSave();
            EnemyListWrapper wrapper = new EnemyListWrapper(EnemiesController.getEnemies());
            mapper.writeValue(new File("userData/"+id+"/player.json"),player);
            mapper.writeValue(new File("userData/"+id+"/weapon.json"),weapon);
            mapper.writeValue(new File("userData/"+id+"/enemies.json"),wrapper);
            mapper.writeValue(new File("userData/"+id+"/playerBullets.json"),WeaponController.getPlayerBullets());
            mapper.writeValue(new File("userData/"+id+"/enemyBullets.json"),WeaponController.getEnemyBullets());
            mapper.writeValue(new File("userData/"+id+"/orbs.json"),OrbsController.getOrbs());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
