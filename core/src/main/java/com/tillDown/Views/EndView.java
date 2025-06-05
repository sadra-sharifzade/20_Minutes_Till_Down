package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tillDown.Controllers.MainMenuController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.User;

public class EndView implements Screen {
    private Stage stage;
    private Label titleLabel;
    private Label scoreLabel;
    private Label numKillsLabel;
    private Label timeAliveLabel;
    private TextButton backToMainMenuButton;
    private Skin skin;

    public EndView(boolean won,int score, int numKills,int timeAlive) {
        skin = GameAssetManager.getGameAssetManager().getSkin();
        if (won) titleLabel = new Label(Main.getCurrentUser().getUsername() + Main.language.won, skin);
        else titleLabel = new Label(Main.getCurrentUser().getUsername() + Main.language.lost, skin);
        scoreLabel = new Label(Main.language.score + score, skin);
        numKillsLabel = new Label( Main.language.kills+ numKills, skin);
        timeAliveLabel = new Label(Main.language.timeAlive + timeAlive + " secs", skin);
        backToMainMenuButton = new TextButton(Main.language.backToMainMenu, skin);
        backToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                EndView.this.dispose();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(titleLabel).colspan(2).expandX().pad(10).row();
        table.add(scoreLabel).colspan(2).expandX().pad(10).row();
        table.add(numKillsLabel).colspan(2).expandX().pad(10).row();
        table.add(timeAliveLabel).colspan(2).expandX().pad(10).row();
        table.add(backToMainMenuButton).colspan(2).expandX().pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
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
