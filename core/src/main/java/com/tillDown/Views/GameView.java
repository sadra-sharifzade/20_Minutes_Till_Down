package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private boolean paused;
    public int getElapsedTime() {
        return (int) (Main.getGameTime()*60-timeRemaining);
    }

    public float getRemainingTime() {return timeRemaining;}

    public GameController getController() {return controller;}
    public GameView(){}
    public GameView(Player player, Weapon weapon,boolean isNew) {
        GameAssetManager.changeCursor();
        skin = GameAssetManager.getGameAssetManager().getSkin();
        background = GameAssetManager.getGameAssetManager().getBackground();
        MAP_WIDTH = background.getWidth();
        MAP_HEIGHT = background.getHeight();
        camera = new OrthographicCamera();
        this.controller = new GameController(this, player,weapon,isNew);
        if(isNew) timeRemaining = Main.getGameTime() * 60;
        else timeRemaining = Main.getRemainingTime();
        camera.zoom = 0.5f;
        gameViewport = new ScreenViewport(camera);
        stage = new Stage(gameViewport);
        Gdx.input.setInputProcessor(this);
        camera.position.set(MAP_WIDTH / 2f, MAP_HEIGHT / 2f, 0);
        if(isNew) controller.getPlayer().setPosition(MAP_WIDTH / 2f, MAP_HEIGHT / 2f);
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
        xpBar.setAnimateDuration(0.2f);
        xpBar.setHeight(30);
        xpBar.setWidth(hudViewport.getScreenWidth());
        Table table = new Table();
        table.setFillParent(true);
        table.top().left().pad(5);
        levelLabel.setAlignment(Align.center);
        Stack stack = new Stack(xpBar,levelLabel);
        table.add(stack).width(hudViewport.getScreenWidth() - 50).colspan(2).center().pad(10).row();
        table.add().height(75f).row();
        table.add(numKillsLabel).left().pad(10);
        table.add(timerLabel).right().pad(10);
        table.add(HPLabel).left().pad(10);
        table.add(numShotsLabel).left().pad(10);
        table.add();
        hudStage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    public OrthographicCamera getCamera() {return camera;}

    public void setPaused(boolean paused) {this.paused = paused;}

    @Override
    public void render(float delta) {
        if (paused) return;
        ScreenUtils.clear(Color.DARK_GRAY);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameViewport.apply();
        camera.position.set(controller.getPlayer().getX(), controller.getPlayer().getY(), 0);
        if (camera.position.x <= gameViewport.getScreenWidth()/4) camera.position.x =  gameViewport.getScreenWidth() /4;
        else if (camera.position.x >= background.getWidth()- gameViewport.getScreenWidth() /4) camera.position.x =  background.getWidth()- gameViewport.getScreenWidth() /4;
        if (camera.position.y <=  gameViewport.getScreenHeight() /4) camera.position.y =  gameViewport.getScreenHeight() /4;
        else if (camera.position.y >= background.getHeight()- gameViewport.getScreenHeight() /4) camera.position.y =  background.getHeight()- gameViewport.getScreenHeight() /4;
        camera.update();
        timeRemaining -= delta;
        xpBar.setValue(controller.getPlayer().getLevelPercentage());
        levelLabel.setText(Main.language.level + controller.getPlayer().getLevel());
        numKillsLabel.setText(Main.language.numberOfKilledEnemies + controller.getPlayer().getNumKills());
        timerLabel.setText(formatTime((int) timeRemaining));
        SpriteBatch batch = Main.getBatch();
        batch.setProjectionMatrix(camera.combined);
        if (Main.isBlackAndWhiteEnabled()) {
            batch.setShader(Main.getGrayscaleShader());
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
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
        if (timeRemaining <= 0) {
            controller.endGame(true);
        }

    }

    public String formatTime(int totalSeconds) {
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
        if (keycode == Input.Keys.ESCAPE) {
            paused = true;
            Main.getMain().setScreen(new PauseMenuView(this)); // Pass current GameView to resume later
            return true;
        } else if (keycode == Input.Keys.NUM_1 && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            timeRemaining -= 60;
        } else if (keycode == Input.Keys.NUM_2 && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            controller.getPlayer().addXp(controller.getPlayer().getLevel() * 20);
        } else if (keycode == Input.Keys.NUM_3 && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            if (controller.getPlayer().getMaxHp() != controller.getPlayer().getHp()) {
                controller.getPlayer().setHp(controller.getPlayer().getHp()+1);
            }
        } else if (keycode == Input.Keys.NUM_4 && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            Main.setIsBossFight(true);
        } else if (keycode == Input.Keys.NUM_5 && Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            controller.killNearEnemies();
        } else if (keycode == Main.getKeyBindings().get("Auto-Aim")) {
            Main.setIsAutoAimEnabled(!Main.isAutoAimEnabled());
        }
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
        controller.getWeaponController().handleWeaponShoot(camera.position.x - camera.viewportWidth / 2 + screenX,
                                                           camera.position.y + camera.viewportHeight / 2 - screenY,
                                                           controller.getPlayer().getX(), controller.getPlayer().getY());
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
