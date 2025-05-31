package com.tillDown.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Views.PauseMenuView;

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
        dialog.getContentTable().add(new Label("Getting a HP (only if dead)", skin)).center().row();
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
        int cnt = 0;
        for (Integer i: Main.getCurrentUser().getGainedAbilities()){
            switch (i){
                case 0: abilities.append("Vitality,"); break;
                case 1: abilities.append("Damager,"); break;
                case 2: abilities.append("Procrease,"); break;
                case 3: abilities.append("Amocrease,"); break;
                case 4: abilities.append("Speedy,"); break;
            }
            if (cnt++ == 3) abilities.append("\n");
        }
        dialog.getContentTable().add(new Label(abilities.toString(), skin)).center();
        dialog.button("OK", false);
        dialog.show(view.getStage());

    }

    public void setBlackAndWhite(boolean checked) {
        Main.setIsBlackAndWhiteEnabled(checked);
    }
}
