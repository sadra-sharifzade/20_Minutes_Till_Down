package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Main;
import com.tillDown.Models.User;
import com.tillDown.Views.GameView;
import com.tillDown.Views.MainMenuView;

import java.io.File;
import java.io.IOException;

public class MainMenuController {
    private MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
    }

    public void resumeLastGame(Skin skin, Stage stage) {
        User user = Main.getCurrentUser();
        if (user.getSavedGameId()==0) {
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            Main.setGameView(mapper.readValue(new File("savedGame.json"), GameView.class));
            Main.getMain().setScreen(Main.getGameView());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
