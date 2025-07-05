package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tillDown.Controllers.ProfileMenuController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;

import java.util.*;
import java.util.List;

public class ProfileMenuView implements Screen {
    private ProfileMenuController controller;
    private Stage stage;
    private TextButton changeUsernameButton;
    private TextButton changePasswordButton;
    private TextButton deleteAccountButton;
    private TextButton changeAvatarButton;
    private TextButton backButton;
    private Dialog dragAndDropDialog;
    private Skin skin;
    public ProfileMenuView() {

        this.controller = new ProfileMenuController(this);
        this.stage = new Stage(new FitViewport(1000,1000));
        skin = GameAssetManager.getGameAssetManager().getSkin();
        changeUsernameButton = new TextButton(Main.language.changeUsername, skin);
        changePasswordButton = new TextButton(Main.language.changePassword, skin);
        deleteAccountButton = new TextButton(Main.language.deleteAccount, skin);
        changeAvatarButton = new TextButton(Main.language.changeAvatar, skin);
        backButton = new TextButton(Main.language.back, skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                ProfileMenuView.this.dispose();
            }
        });
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showChangeUsernameDialog();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showChangePasswordDialog();
            }
        });
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showDeleteAccountDialog();
            }
        });
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.showChangeAvatarDialog();
            }
        });
    }

    public Dialog getDragAndDropDialog() {return dragAndDropDialog;}

    public Stage getStage() {return stage;}

    public void setDragAndDropDialog(Dialog dragAndDropDialog) {
        this.dragAndDropDialog = dragAndDropDialog;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(new Label(Main.language.profile, skin, "title")).colspan(2);
        table.add(backButton).width(100).pad(20).row();
        table.add(changeUsernameButton).pad(10).row();
        table.add(changePasswordButton).pad(10).row();
        table.add(deleteAccountButton).pad(10).row();
        table.add(changeAvatarButton).pad(10).row();
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

    public ProfileMenuController getController() {return controller;}
}
