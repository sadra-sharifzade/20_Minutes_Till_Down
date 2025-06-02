package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tillDown.Controllers.GameController;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.Player;
import com.tillDown.Models.Weapon;

public class GameView implements Screen, InputProcessor {
    private Stage hudStage;
    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Viewport gameViewport;
    private Viewport hudViewport;
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    private Texture background;
    private Label timerLabel;
    private ProgressBar xpBar;
    private float timeRemaining;
    private Skin skin;
    private Label numKillsLabel;
    private Label levelLabel;
    private Label HPLabel;
    private Label numShotsLabel;
    public GameView(String characterName, String weaponName) {
        camera = new OrthographicCamera();
        this.controller = new GameController(this,new Player(characterName),new Weapon(weaponName));
        timeRemaining=Main.getGameTime()*60;

    }

    @Override
    public void show() {
        skin = GameAssetManager.getGameAssetManager().getSkin();
        background = GameAssetManager.getGameAssetManager().getBackground();
        MAP_WIDTH = background.getWidth();
        MAP_HEIGHT = background.getHeight();
        camera.zoom = 0.5f;
        gameViewport = new ScreenViewport(camera);
        stage = new Stage(gameViewport);
        Gdx.input.setInputProcessor(this);
        camera.position.set(MAP_WIDTH/2f, MAP_HEIGHT/2f, 0);
        controller.getPlayer().setPosition(MAP_WIDTH/2f,MAP_HEIGHT/2f);
        camera.update();
        hudCamera = new OrthographicCamera();
        hudViewport = new ScreenViewport(hudCamera);
        hudStage = new Stage(hudViewport);
        timerLabel = new Label(formatTime((int) timeRemaining), skin);
        numKillsLabel = new Label("", skin);
        levelLabel = new Label("", skin);
        HPLabel = new Label("", skin);
        numShotsLabel = new Label("", skin);
        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        barStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        barStyle.knobBefore = skin.newDrawable("white", Color.GREEN);
        barStyle.background.setMinHeight(30);
        barStyle.knobBefore.setMinHeight(30);
        xpBar = new ProgressBar(0, 100, 1, false, barStyle);
        xpBar.setValue(50);
        xpBar.setAnimateDuration(0.2f);
        xpBar.setHeight(30);
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().pad(10);
        table.add(timerLabel).left().pad(10).row();
        table.add(xpBar).width(300).left().pad(10);
        table.add(levelLabel).left().pad(10);
        table.add(numKillsLabel).left().pad(10);
        table.add(HPLabel).left().pad(10);
        table.add(numShotsLabel).left().pad(10);
        hudStage.addActor(table);
    }

    public OrthographicCamera getCamera() {
        System.out.println(camera);return camera;}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameViewport.apply();
        camera.position.set( controller.getPlayer().getX(), controller.getPlayer().getY(), 0);
        camera.update();
        timeRemaining -= delta;
        timerLabel.setText(formatTime((int) timeRemaining));
        SpriteBatch batch = Main.getBatch();
        batch.setProjectionMatrix(camera.combined);
        if (Main.isBlackAndWhiteEnabled()) {
            batch.setShader(Main.getGrayscaleShader());
        } else {
            batch.setShader(null);
        }
        batch.begin();
        batch.draw(background, 0, 0);
        controller.update(delta);
        batch.end();

        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        hudStage.act(delta);
        hudStage.draw();

    }
    private String formatTime(int totalSeconds) {
        return String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);
    }
    @Override
    public void resize(int width, int height) {

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
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(camera.position.x-camera.viewportWidth/2+screenX, camera.position.y+camera.viewportHeight/2-screenY,camera.position.x,camera.position.y);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
