package com.tillDown.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tillDown.Controllers.SettingsController;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Main;
import com.tillDown.Models.GameAssetManager;

import java.io.File;
import java.io.IOException;


public class SettingsView implements Screen {
    private Stage stage;
    private final SettingsController controller;
    private SelectBox<String> musicDropdown;
    private Slider volumeSlider;
    private Label volumeLabel;
    private Skin skin;
    private CheckBox sfxCheckbox;
    private CheckBox autoReloadCheckbox;
    private CheckBox blackAndWhiteCheckbox;
    private TextButton backButton;
    private Table table;
    private String waitingForKeyAction;
    public SettingsView() {
        this.controller = new SettingsController(this);
        skin = GameAssetManager.getGameAssetManager().getSkin();
        musicDropdown = new SelectBox<>(skin);
        FileHandle[] musicFiles = Gdx.files.internal("assets/musics").list();
        Array<String> musicNames = new Array<>();
        for (FileHandle file : musicFiles) {
            musicNames.add(file.name());
        }
        musicDropdown.setItems(musicNames);
        musicDropdown.setSelectedIndex(2);
        volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(Main.getCurrentMusic().getVolume());
        volumeLabel = new Label("100%", skin);
        sfxCheckbox = new CheckBox(" SFX", skin);
        sfxCheckbox.setChecked(Main.isSFXEnabled());
        autoReloadCheckbox = new CheckBox(" Auto Reload", skin);
        autoReloadCheckbox.setChecked(Main.isAutoReloadEnabled());
        blackAndWhiteCheckbox = new CheckBox(" Black & White", skin);
        blackAndWhiteCheckbox.setChecked(Main.isBlackAndWhiteEnabled());
        backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView());
                SettingsView.this.dispose();
            }
        });
        sfxCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setIsSFXEnabled(sfxCheckbox.isChecked());
            }
        });
        autoReloadCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setIsAutoReloadEnabled(autoReloadCheckbox.isChecked());
            }
        });
        blackAndWhiteCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setBlackAndWhite(blackAndWhiteCheckbox.isChecked());
            }
        });

        musicDropdown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeMusic(musicDropdown.getSelected());
            }
        });
        Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("sfx/click.wav"));
        musicDropdown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Main.isSFXEnabled())clickSound.play();
            }
        });
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeVolume(volumeSlider.getValue(),volumeLabel);
            }
        });




    }

    public String getWaitingForKeyAction() {return waitingForKeyAction;}

    public void setWaitingForKeyAction(String waitingForKeyAction) {this.waitingForKeyAction = waitingForKeyAction;}

    public Table getTable() {return table;}

    @Override
    public void show() {
        stage = new Stage(new FitViewport(2000,1000));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.add(backButton).width(100).pad(20);
        table.add(new Label("Settings", skin,"title")).colspan(2).row();
        table.add(new Label("Choose Music:", skin)).pad(10);
        table.add(musicDropdown).colspan(2).fillX().pad(10).row();
        table.add(new Label("Volume", skin)).pad(10).left();
        table.add(volumeSlider).colspan(2).fillX().pad(10);
        table.add(volumeLabel).width(100).pad(10).left().row();
        table.add(sfxCheckbox).left().pad(10);
        table.add(autoReloadCheckbox).center().pad(10);
        table.add(blackAndWhiteCheckbox).right().pad(10).row();
        int cnt = 1;
        for (String action : Main.getKeyBindings().keySet()) {
            String keyName = Input.Keys.toString(Main.getKeyBindings().get(action));
            final TextButton keyButton = new TextButton(keyName, skin);

            keyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (waitingForKeyAction == null) {
                        keyButton.setText("Press a key...");
                        waitingForKeyAction = action;
                        Main.getKeyBindings().put(action,-1);
                    }

                }
            });


            table.add(new Label(action, skin)).pad(10);
            table.add(keyButton).pad(10);
            if (cnt++%2 == 0) table.row();
        }
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return controller.recordKey(keycode);
            }
        });
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
        File directory = new File("userData/"+ Main.getCurrentUser().getId() );
        if (!directory.exists()) directory.mkdir();
        try {
            Main.save("userData/"+Main.getCurrentUser().getId()+"/settings.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.dispose();
    }

}
