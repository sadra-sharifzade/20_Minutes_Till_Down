package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tillDown.Controllers.ProfileMenuController;
import com.tillDown.Controllers.TalentMenuController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;

public class TalentMenuView implements Screen {
    private TalentMenuController controller;
    private Stage stage;
    private TextButton herosInfoButton;
    private TextButton cheatCodesInfoButton;
    private TextButton keyBindingsInfoButton;
    private TextButton abilitiesInfoButton;
    private TextButton backButton;
    private Skin skin;
    public TalentMenuView() {
        this.controller = new TalentMenuController(this);
        this.stage = new Stage(new FitViewport(1000,1000));
        skin = GameAssetManager.getGameAssetManager().getSkin();
        herosInfoButton = new TextButton("Heros Info", skin);
        cheatCodesInfoButton = new TextButton("Cheat Codes", skin);
        keyBindingsInfoButton = new TextButton("Key Bindings", skin);
        abilitiesInfoButton = new TextButton("Abilities Info", skin);
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                TalentMenuView.this.dispose();
            }
        });
        herosInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showHerosInfo();
            }
        });

        cheatCodesInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showCheatCodesInfo();
            }
        });
        keyBindingsInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showKeyBindingsInfo();
            }
        });
        abilitiesInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showAbilitiesInfo();
            }
        });
    }

    public Stage getStage() {return stage;}

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(new Label("Hints", skin, "title")).colspan(2);
        table.add(backButton).width(100).pad(20).row();
        table.add(herosInfoButton).pad(10).row();
        table.add(cheatCodesInfoButton).pad(10).row();
        table.add(keyBindingsInfoButton).pad(10).row();
        table.add(abilitiesInfoButton).pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
