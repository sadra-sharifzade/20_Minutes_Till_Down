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
import com.tillDown.Models.Player;
import com.tillDown.Models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AbilityView implements Screen {
    private Stage stage;
    private TextButton[] abilityButtons = new TextButton[3];
    private Label titleLabel;
    private Skin skin;

    public AbilityView(Player player) {
        skin = GameAssetManager.getGameAssetManager().getSkin();
        titleLabel = new Label(Main.language.youUpgraded + player.getLevel() + Main.language.chooseFrom, skin);
        ArrayList<String> abilities = new ArrayList<>();
        abilities.add("Speedy");
        abilities.add("Damager");
        abilities.add("Procrease");
        abilities.add("Amocrease");
        abilities.add("Vitality");
        for (int i = 0; i < 3; i++) {
            int rand = new Random().nextInt(abilities.size());
            abilityButtons[i] = new TextButton(abilities.get(rand), skin);
            abilities.remove(rand);
            abilityButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.getMain().setScreen(Main.getGameView());
                    Main.getGameView().setPaused(false);
                    player.addAbility(((TextButton) event.getListenerActor()).getLabel().getText().toString().toLowerCase());
                    AbilityView.this.dispose();
                }
            });
        }

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.add(titleLabel).colspan(2).expandX().pad(10).row();
        for (int i = 0; i < 3; i++) {
            table.add(abilityButtons[i]).colspan(2).expandX().pad(10).row();
        }
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
