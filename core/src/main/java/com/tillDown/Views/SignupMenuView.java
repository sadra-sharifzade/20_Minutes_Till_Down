package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;

public class SignupMenuView implements Screen {
    private Stage stage;
    private final SignupMenuContoller controller;

    private TextField usernameField;
    private TextField passwordField;
    private Label errorLabel;
    private Label questionLabel;
    private TextField securityQuestionField;
    private TextButton signupButton;
    private TextButton guestButton;
    private TextButton questionButton;
    private TextButton loginButton;
    private Skin skin;

    public SignupMenuView() {
        this.controller = new SignupMenuContoller(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        securityQuestionField = new TextField("", skin);
        securityQuestionField.setVisible(false);
        signupButton = new TextButton("Sign Up", skin);
        guestButton = new TextButton("Play as Guest", skin);
        loginButton = new TextButton("Login", skin);
        questionButton = new TextButton("Confirm", skin);
        questionButton.setVisible(false);

        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        questionLabel = new Label("", skin);
        questionLabel.setColor(Color.WHITE);
        questionLabel.setVisible(false);


        signupButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignup(usernameField.getText(), passwordField.getText());
            }
        });

        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.loginAsGuest();
            }
        });
        questionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getCurrentUser().setAnswerToSecurityQuestion(securityQuestionField.getText());
                Main.getMain().setScreen(new LoginMenuView());
                SignupMenuView.this.dispose();
            }
        });
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new LoginMenuView());
                SignupMenuView.this.dispose();
            }
        });
    }

    public void showError(String message) {
        errorLabel.setText(message);
//        Dialog errorDialog = new Dialog("Error", skin) {
//            @Override
//            protected void result(Object object) {
//                System.out.println("Dialog closed with: " + object);
//            }
//        };
//
//        errorDialog.text("Something went wrong!");
//        TextButton okButton = new TextButton("OK", skin);
//        okButton.getLabel().setFontScale(0.5f);  // Scale text inside the button
//        okButton.getLabel().setAlignment(Align.center);
//        errorDialog.button(okButton, true);
//        errorDialog.pad(100); // Add padding inside dialog
//        errorDialog.pack(); // Recalculate size
//        errorDialog.setPosition(
//            (Gdx.graphics.getWidth() - errorDialog.getWidth()) / 2,
//            (Gdx.graphics.getHeight() - errorDialog.getHeight()) / 2
//        );
//        errorDialog.show(stage);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1000, 1000));
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(new Label("Username:", skin)).left().pad(10);
        table.add(usernameField).expandX().fillX().pad(10).row();

        table.add(new Label("Password:", skin)).left().pad(10);
        table.add(passwordField).expandX().fillX().pad(10).row();

        table.add(signupButton).colspan(2).expandX().pad(10).row();
        table.add(guestButton).colspan(2).expandX().pad(10).row();
        table.add(loginButton).colspan(2).expandX().pad(10).row();
        table.add(errorLabel).colspan(2).pad(10).row();
        table.add(questionLabel).left().pad(10);
        table.add(securityQuestionField).fillX().pad(10).row();
        table.add(questionButton).colspan(2).pad(10).row();
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

    public Label getErrorLabel() {return errorLabel;}
    public void visibleSecurityQuestion(String question) {
        questionLabel.setText(question);
        securityQuestionField.setVisible(true);
        questionLabel.setVisible(true);
        questionButton.setVisible(true);
    }
    public void disableButtons() {
        signupButton.clearListeners();
        signupButton.setTouchable(Touchable.disabled);
        guestButton.clearListeners();
        guestButton.setTouchable(Touchable.disabled);

    }
}
