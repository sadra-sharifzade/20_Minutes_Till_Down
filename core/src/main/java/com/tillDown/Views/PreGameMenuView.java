package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tillDown.Controllers.PreGameMenuController;
import com.tillDown.Controllers.SettingsController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.Player;
import com.tillDown.Models.Weapon;

public class PreGameMenuView implements Screen {
    private Stage stage;
    private final PreGameMenuController controller;
    private SelectBox<String> heroDropDown;
    private SelectBox<String> weaponDropDown;
    private SelectBox<Integer> timeDropDown;
    private TextButton backButton;
    private TextButton startGameButton;
    private Skin skin;
    public PreGameMenuView() {
        this.controller = new PreGameMenuController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        heroDropDown = new SelectBox<>(skin);
        heroDropDown.setItems("Shana","Diamond","Scarlet","Lilith","Dasher");
        weaponDropDown = new SelectBox<>(skin);
        weaponDropDown.setItems("Revolver","Shotgun","SMGs Dual");
        timeDropDown = new SelectBox<>(skin);
        timeDropDown.setItems(2,5,10,20);
        backButton = new TextButton("Back", skin);
        startGameButton = new TextButton("Start Game", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                PreGameMenuView.this.dispose();
            }
        });
        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.setGameTime(timeDropDown.getSelected());
                Weapon weapon = new Weapon(weaponDropDown.getSelected());
                GameView view = new GameView(new Player(heroDropDown.getSelected(), weapon), weapon,true);
                Main.getMain().setScreen(view);
                Main.setGameView(view);
                PreGameMenuView.this.dispose();
            }
        });
        heroDropDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeHero(heroDropDown.getSelected());
            }
        });
        weaponDropDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeWeapon(weaponDropDown.getSelected());
            }
        });
        timeDropDown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeGameTime(timeDropDown.getSelected());
            }
        });





    }



    @Override
    public void show() {
        stage = new Stage(new FitViewport(2000, 1000));
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        table.add(backButton).width(100).pad(20);
        table.add(new Label("Pre-Game Menu", skin,"title")).colspan(2).row();
        table.add(new Label("Choose Hero:", skin)).pad(10);
        table.add(heroDropDown).colspan(2).fillX().pad(10).row();
        table.add(new Label("Choose Weapon: ",skin)).pad(10);
        table.add(weaponDropDown).colspan(2).fillX().pad(10).row();
        table.add(new Label("Choose Game Time:", skin)).pad(10);
        table.add(timeDropDown).colspan(2).fillX().pad(10).row();
        table.add(startGameButton).colspan(3).fillX().pad(10).row();
        stage.addActor(table);

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
