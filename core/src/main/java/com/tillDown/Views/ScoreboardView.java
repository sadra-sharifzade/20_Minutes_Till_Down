package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDown.Controllers.ScoreboardController;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
public class ScoreboardView implements Screen {
    private Stage stage;
    private ScoreboardController controller;
    private Skin skin;
    private Table table;
    private SelectBox<String> sortSelectBox;
    private TextButton backButton;

    public ScoreboardView() {
        controller = new ScoreboardController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        sortSelectBox = new SelectBox<>(skin);
        sortSelectBox.setItems("Score", "Kills", "Most Time Alive", "Username");
        sortSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.updateScoreboard(sortSelectBox.getSelected());
            }
        });
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                ScoreboardView.this.dispose();
            }
        });
    }

    public Table getTable() {return table;}

    public Skin getSkin() {return skin;}

    public SelectBox<String> getSortSelectBox() {return sortSelectBox;}

    public TextButton getBackButton() {return backButton;}

    @Override
    public void show() {
        table.defaults().pad(10);
        table.add(backButton).left().width(100).pad(20);
        Label title = new Label("Scoreboard", skin,"title");
        table.add(title).center().colspan(3);
        table.row();
        table.add(sortSelectBox).colspan(4).pad(10).row();
        controller.updateScoreboard("Score"); // default
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
