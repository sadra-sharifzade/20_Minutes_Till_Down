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
    private TextButton heroesInfoButton;
    private TextButton cheatCodesInfoButton;
    private TextButton keyBindingsInfoButton;
    private TextButton abilitiesInfoButton;
    private TextButton backButton;
    private Skin skin;
    public TalentMenuView() {
        this.controller = new TalentMenuController(this);
        this.stage = new Stage(new FitViewport(1000,1000));
        skin = GameAssetManager.getGameAssetManager().getSkin();
        heroesInfoButton = new TextButton(Main.language.heroesInfo, skin);
        cheatCodesInfoButton = new TextButton(Main.language.cheatCodes, skin);
        keyBindingsInfoButton = new TextButton(Main.language.keyBindings, skin);
        abilitiesInfoButton = new TextButton(Main.language.abilitiesInfo, skin);
        backButton = new TextButton(Main.language.back, skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                TalentMenuView.this.dispose();
            }
        });
        heroesInfoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showHeroesInfo();
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
        table.add(new Label(Main.language.hints, skin, "title")).colspan(2);
        table.add(backButton).width(100).pad(20).row();
        table.add(heroesInfoButton).pad(10).row();
        table.add(cheatCodesInfoButton).pad(10).row();
        table.add(keyBindingsInfoButton).pad(10).row();
        table.add(abilitiesInfoButton).pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
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
