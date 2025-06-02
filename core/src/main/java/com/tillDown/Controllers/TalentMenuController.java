package com.tillDown.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Views.TalentMenuView;

import java.util.Map;

public class TalentMenuController {
    private TalentMenuView view;
    public TalentMenuController(TalentMenuView view) {
        this.view = view;
    }

    public void showHeroesInfo() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("HEROES INFO", skin);
        dialog.pad(20);
        dialog.getContentTable().add(new Label(" Name ", skin)).center();
        dialog.getContentTable().add(new Label(" HP ", skin)).center();
        dialog.getContentTable().add(new Label(" Speed ", skin)).center().row();
        dialog.getContentTable().add(new Label("SHANA", skin)).center();
        dialog.getContentTable().add(new Label("4", skin)).center();
        dialog.getContentTable().add(new Label("4", skin)).center().row();
        dialog.getContentTable().add(new Label("DIAMOND", skin)).center();
        dialog.getContentTable().add(new Label("7", skin)).center();
        dialog.getContentTable().add(new Label("1", skin)).center().row();
        dialog.getContentTable().add(new Label("SCARLET", skin)).center();
        dialog.getContentTable().add(new Label("3", skin)).center();
        dialog.getContentTable().add(new Label("5", skin)).center().row();
        dialog.button("OK", false);
        dialog.show(view.getStage());
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
        dialog.getContentTable().add(new Label("Getting a HP (only if dead)", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+4 ", skin)).center();
        dialog.getContentTable().add(new Label("Go To Boss Fight", skin)).center().row();
        dialog.getContentTable().add(new Label("Ctrl+5 ", skin)).center();
        dialog.getContentTable().add(new Label("Killing Enemies Around You", skin)).center().row();
        dialog.button("OK", false);
        dialog.show(view.getStage());
    }

    public void showKeyBindingsInfo() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("KEY BINDINGS", skin);
        dialog.pad(20);
        dialog.getContentTable().add(new Label("ACTION", skin)).center();
        dialog.getContentTable().add(new Label("KEY", skin)).center().row();
        for (String action : Main.getKeyBindings().keySet()) {
            dialog.getContentTable().add(new Label(action, skin)).center();
            dialog.getContentTable().add(new Label(Input.Keys.toString(Main.getKeyBindings().get(action)), skin)).center().row();
        }
        dialog.button("OK", false);
        dialog.show(view.getStage());

    }

    public void showAbilitiesInfo() {
        Skin skin = GameAssetManager.getGameAssetManager().getSkin();
        Dialog dialog = new Dialog("CHEAT CODES INFO", skin);
        dialog.pad(20);
        dialog.getContentTable().add(new Label("NAME", skin)).center();
        dialog.getContentTable().add(new Label("DESCRIPTION", skin)).center().row();
        dialog.getContentTable().add(new Label("VITALITY ", skin)).center();
        dialog.getContentTable().add(new Label("Increases Max HP By 1", skin)).center().row();
        dialog.getContentTable().add(new Label("DAMAGER ", skin)).center();
        dialog.getContentTable().add(new Label("Increases Damage of Any Weapon By 25% For 10 Seconds", skin)).center().row();
        dialog.getContentTable().add(new Label("PROCREASE ", skin)).center();
        dialog.getContentTable().add(new Label("Increases Projectile of Any Weapon By 1", skin)).center().row();
        dialog.getContentTable().add(new Label("AMOCREASE ", skin)).center();
        dialog.getContentTable().add(new Label("Increases Max Ammo of Any Weapon By 5", skin)).center().row();
        dialog.getContentTable().add(new Label("SPEEDY ", skin)).center();
        dialog.getContentTable().add(new Label("2x speed for 10 seconds", skin)).center().row();
        dialog.button("OK", false);
        dialog.show(view.getStage());
    }
}
