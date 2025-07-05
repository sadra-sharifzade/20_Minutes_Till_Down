package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tillDown.Controllers.LoginMenuController;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final LoginMenuController controller;
    private Skin skin;
    private TextField usernameField;
    private TextField passwordField;
    private Label errorLabel;
    private TextButton loginButton;
    private TextButton forgetPasswordButton;
    private TextButton backToSignupButton;
    private Label questionLabel;
    private TextField securityQuestionField;
    private TextButton confirmButton;

    public LoginMenuView() {
        this.controller = new LoginMenuController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        loginButton = new TextButton(Main.language.login, skin);
        forgetPasswordButton = new TextButton(Main.language.forgotPassword, skin);
        backToSignupButton = new TextButton(Main.language.backToSignUp, skin);
        confirmButton = new TextButton(Main.language.confirm, skin);
        confirmButton.setVisible(false);
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        questionLabel = new Label(Main.language.whereWereYouBorn, skin);
        questionLabel.setVisible(false);
        securityQuestionField = new TextField("", skin);
        securityQuestionField.setVisible(false);

        loginButton.addListener(e -> {
            if (!loginButton.isPressed()) return false;
            controller.login(usernameField.getText(),passwordField.getText());
            return true;
        });
        forgetPasswordButton.addListener(e ->{
            if (!forgetPasswordButton.isPressed()) return false;
            questionLabel.setVisible(true);
            securityQuestionField.setVisible(true);
            confirmButton.setVisible(true);
            showError(Main.language.enterYourUsername);
            return true;
        });
        backToSignupButton.addListener(e ->{
            if (!backToSignupButton.isPressed()) return false;
            Main.getMain().setScreen(new SignupMenuView());
            LoginMenuView.this.dispose();
            return true;
        });
        confirmButton.addListener(e ->{
            if (!confirmButton.isPressed()) return false;
            controller.forgetPassword(usernameField.getText(),securityQuestionField.getText());
            return true;
        });
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1000, 1000));
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(new Label(Main.language.username, skin)).left().pad(10);
        table.add(usernameField).expandX().fillX().pad(10).row();

        table.add(new Label(Main.language.password, skin)).left().pad(10);
        table.add(passwordField).expandX().fillX().pad(10).row();

        table.add(loginButton).colspan(2).expandX().pad(10).row();
        table.add(forgetPasswordButton).colspan(2).expandX().pad(10).row();
        table.add(backToSignupButton).colspan(2).expandX().pad(10).row();
        table.add(errorLabel).colspan(2).pad(10).row();
        table.add(questionLabel).fillX().pad(10);
        table.add(securityQuestionField).expandX().fillX().pad(10).row();
        table.add(confirmButton).colspan(2).expandX().pad(10).row();
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
