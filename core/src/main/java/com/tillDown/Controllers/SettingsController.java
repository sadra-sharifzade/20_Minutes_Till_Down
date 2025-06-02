package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.tillDown.Main;
import com.tillDown.Views.SettingsView;

import java.awt.*;

public class SettingsController {
    SettingsView view;
    public SettingsController(SettingsView view) {
        this.view = view;
    }

    public void setIsSFXEnabled(boolean checked) {
        Main.setIsSFXEnabled(checked);
    }

    public void setIsAutoReloadEnabled(boolean checked) {
        Main.setIsAutoReloadEnabled(checked);
    }
    public void setBlackAndWhite(boolean checked) {
        Main.setIsBlackAndWhiteEnabled(checked);
    }

    public void changeMusic(String selectedFile) {
        if (Main.getCurrentMusic() != null) Main.getCurrentMusic().dispose();
        Main.setCurrentMusic(Gdx.audio.newMusic(Gdx.files.internal("musics/" + selectedFile)));
        Main.getCurrentMusic().setLooping(true);
        Main.getCurrentMusic().play();
    }

    public void changeVolume(float volume, Label volumeLabel) {
        if (Main.getCurrentMusic() != null) {
            Main.getCurrentMusic().setVolume(volume);
        }
        int percent = (int)(volume * 100);
        volumeLabel.setText(percent + "%");
    }
    public boolean recordKey(int keycode){
        if (view.getWaitingForKeyAction() != null) {
            String action = view.getWaitingForKeyAction();
            if (Main.getKeyBindings().containsValue(keycode)) return false;
            Main.getKeyBindings().put(action, keycode);
            for (Actor actor : view.getTable().getChildren()) {
                if (actor instanceof TextButton) {
                    TextButton btn = (TextButton) actor;
                    if (btn.getText().toString().equals("Press a key...")) {
                        btn.setText(Input.Keys.toString(keycode));
                        break;
                    }
                }
            }

            view.setWaitingForKeyAction(null);
            return true;
        }
        return false;
    }
}
