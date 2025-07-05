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

public class MainMenuView implements Screen {
    private Stage stage;
    private final MainMenuController controller;
    private TextButton resumeLastGameButton;
    private TextButton settingsButton;
    private TextButton profileButton;
    private TextButton preGameButton;
    private TextButton scoreboardButton;
    private TextButton talentButton;
    private TextButton signOutButton;
    private Skin skin;

    public MainMenuView() {
        this.controller = new MainMenuController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        resumeLastGameButton = new TextButton(Main.language.resumeLastGame, skin);
        settingsButton = new TextButton(Main.language.settings, skin);
        profileButton = new TextButton(Main.language.profile, skin);
        scoreboardButton = new TextButton(Main.language.scoreboard ,skin);
        preGameButton = new TextButton(Main.language.createANewGame, skin);
        talentButton = new TextButton(Main.language.talent, skin);
        signOutButton = new TextButton(Main.language.signOut, skin);

        resumeLastGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resumeLastGame(skin,stage);
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new SettingsView());
                MainMenuView.this.dispose();
            }
        });
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ProfileMenuView profileMenuView = new ProfileMenuView();
                Main.setProfileMenuView(profileMenuView);
                Main.getMain().setScreen(profileMenuView);
                MainMenuView.this.dispose();
            }
        });
        preGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new PreGameMenuView());
                MainMenuView.this.dispose();
            }
        });
        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new ScoreboardView());
                MainMenuView.this.dispose();
            }
        });
        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new TalentMenuView());
                MainMenuView.this.dispose();
            }
        });
        signOutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.setCurrentUser(null);
                Main.getMain().setScreen(new LoginMenuView());
                MainMenuView.this.dispose();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        User user = Main.getCurrentUser();

        Texture profileTexture = new Texture(Gdx.files.internal("assets/avatars/"+user.getAvatarName()));
        Image profileImage = new Image(profileTexture);
        profileImage.setSize(100, 100);

        Label usernameLabel = new Label(user.getUsername(), skin);
        Label scoreLabel = new Label(Main.language.score + user.getScore(), skin);

        Table profileTable = new Table();
        profileTable.top().left();
        profileTable.setFillParent(true);
        profileTable.pad(10);

        profileTable.add(profileImage).size(100, 100).padRight(10).padLeft(250);
        Table textTable = new Table();
        textTable.add(usernameLabel).left().row();
        textTable.add(scoreLabel).left();
        profileTable.add(textTable).left();

        stage.addActor(profileTable);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(resumeLastGameButton).colspan(2).expandX().pad(10).row();
        table.add(settingsButton).colspan(2).expandX().pad(10).row();
        table.add(profileButton).colspan(2).expandX().pad(10).row();
        table.add(scoreboardButton).colspan(2).expandX().pad(10).row();
        table.add(preGameButton).colspan(2).expandX().pad(10).row();
        table.add(talentButton).colspan(2).expandX().pad(10).row();
        table.add(signOutButton).colspan(2).expandX().pad(10).row();
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
