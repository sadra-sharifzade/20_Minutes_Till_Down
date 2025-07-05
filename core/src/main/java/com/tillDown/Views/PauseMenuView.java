package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Controllers.PauseMenuController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.User;

import java.io.File;
import java.io.IOException;

public class PauseMenuView implements Screen {
    private Stage stage;
    private final PauseMenuController controller;
    private TextButton resumeButton;
    private TextButton showCheatCodesButton;
    private TextButton showGainedAbilitiesButton;
    private TextButton quitAndGiveUpButton;
    private TextButton quitAndSaveButton;
    private CheckBox blackAndWhiteCheckbox;
    private Skin skin;
    private GameView gameView;

    public PauseMenuView(GameView gameView) {
        this.gameView = gameView;
        this.controller = new PauseMenuController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        resumeButton = new TextButton(Main.language.resume, skin);
        showCheatCodesButton = new TextButton(Main.language.cheatCodes, skin);
        showGainedAbilitiesButton = new TextButton(Main.language.gainedAbilities, skin);
        quitAndSaveButton = new TextButton(Main.language.quitAndSave, skin);
        quitAndGiveUpButton = new TextButton(Main.language.quitAndGiveUp, skin);
        blackAndWhiteCheckbox = new CheckBox(Main.language.blackAndWhite, skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameView.setPaused(false);
                Main.getMain().setScreen(gameView);
                PauseMenuView.this.dispose();
            }
        });
        showCheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showCheatCodesInfo();
            }
        });
        showGainedAbilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showGainedAbilities();
            }
        });
        quitAndGiveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getGameView().getController().endGame(false);
                PauseMenuView.this.dispose();
            }
        });
        quitAndSaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.saveCurrentGame();
                Main.getMain().setScreen(new MainMenuView());
                PauseMenuView.this.dispose();
            }
        });
        blackAndWhiteCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setBlackAndWhite(blackAndWhiteCheckbox.isChecked());
            }
        });

    }

    public Stage getStage() {return stage;}

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1000, 1000));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        blackAndWhiteCheckbox.setChecked(Main.isBlackAndWhiteEnabled());
        table.add(resumeButton).colspan(2).expandX().pad(10).row();
        table.add(showCheatCodesButton).colspan(2).expandX().pad(10).row();
        table.add(showGainedAbilitiesButton).colspan(2).expandX().pad(10).row();
        table.add(blackAndWhiteCheckbox).colspan(2).expandX().pad(10).row();
        table.add(quitAndSaveButton).colspan(2).expandX().pad(10).row();
        table.add(quitAndGiveUpButton).colspan(2).expandX().pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }

}
