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
import com.tillDown.Controllers.PauseMenuController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.User;

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

    public PauseMenuView() {
        this.controller = new PauseMenuController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        resumeButton = new TextButton("Resume", skin);
        showCheatCodesButton = new TextButton("Cheat Codes", skin);
        showGainedAbilitiesButton = new TextButton("Gained Abilities", skin);
        quitAndSaveButton = new TextButton("Quit & Save", skin);
        quitAndGiveUpButton = new TextButton("Quit & Give Up", skin);
        blackAndWhiteCheckbox = new CheckBox(" Black & White", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo resume game
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
                Main.getMain().setScreen(new MainMenuView());
                PauseMenuView.this.dispose();
            }
        });
        quitAndSaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //todo save game
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
        table.add(resumeButton).colspan(2).expandX().pad(10).row();
        table.add(showCheatCodesButton).colspan(2).expandX().pad(10).row();
        table.add(showGainedAbilitiesButton).colspan(2).expandX().pad(10).row();
        table.add(blackAndWhiteCheckbox).colspan(2).expandX().pad(10).row();
        table.add(quitAndSaveButton).colspan(2).expandX().pad(10).row();
        table.add(quitAndGiveUpButton).colspan(2).expandX().pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
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
