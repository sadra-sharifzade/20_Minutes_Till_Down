package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private Skin skin;
    private Texture background;
    private Map<String,Animation<TextureRegion>> idleAnimations = new HashMap<>();
    private Map<String,Animation<TextureRegion>> runAnimations = new HashMap<>();
    private Map<String,Animation<TextureRegion>> enemiesAnimations = new HashMap<>();
    private Texture shotgunTexture;
    private Texture smgTexture;
    private Texture revolverTexture;
    private Texture bulletTexture;
    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
    public void load(){
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        background = new Texture("background.png");
        loadHeroes();
        shotgunTexture = new Texture("assets/images/weapons/shotgun.png");
        revolverTexture = new Texture("assets/images/weapons/revolver.png");
        smgTexture = new Texture("assets/images/weapons/smg.png");
        bulletTexture = new Texture("assets/images/bullet.png");
        loadEnemies();
    }
    public void loadHeroes() {
        String[] heroNames = {"shana","diamond","dasher","scarlet","lilith"};
        for (String heroName : heroNames) {
            loadAnimation(heroName, "idle");
            loadAnimation(heroName, "run");
        }
    }

    public Animation<TextureRegion> getEnemyAnimations(String name) {return enemiesAnimations.get(name);}

    public void loadEnemies() {
        String[] enemyNames = {"tree","tentacle","eyebat","elder"};
        for (String enemyName : enemyNames){
            FileHandle animDir = Gdx.files.internal("assets/images/enemies/" + enemyName);
            FileHandle[] frameFiles = animDir.list((dir, name) -> name.endsWith(".png"));
            Arrays.sort(frameFiles, Comparator.comparing(FileHandle::name));

            TextureRegion[] frames = new TextureRegion[frameFiles.length];
            for (int i = 0; i < frameFiles.length; i++) {
                frames[i] = new TextureRegion(new Texture(frameFiles[i]));
            }

            Animation<TextureRegion> animation = new Animation<>(0.1f, frames);
            animation.setPlayMode(Animation.PlayMode.LOOP);

            enemiesAnimations.put(enemyName,animation);
        }
    }
    private void loadAnimation(String heroName, String action) {
        FileHandle animDir = Gdx.files.internal("assets/images/heroes/" + heroName + "/" + action);
        if (!animDir.exists()) return;
        FileHandle[] frameFiles = animDir.list((dir, name) -> name.endsWith(".png"));
        Arrays.sort(frameFiles, Comparator.comparing(FileHandle::name));

        TextureRegion[] frames = new TextureRegion[frameFiles.length];
        for (int i = 0; i < frameFiles.length; i++) {
            frames[i] = new TextureRegion(new Texture(frameFiles[i]));
        }

        Animation<TextureRegion> animation = new Animation<>(0.1f, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        if (action.equals("idle")) idleAnimations.put(heroName,animation);
        else runAnimations.put(heroName,animation);
    }

    public Texture getBulletTexture() {return bulletTexture;}

    public Texture getShotgunTexture() {return shotgunTexture;}

    public Texture getSmgTexture() {return smgTexture;}

    public Texture getRevolverTexture() {return revolverTexture;}

    public Animation<TextureRegion> getIdleAnimation(String heroName) {
        return idleAnimations.get(heroName);
    }
    public Animation<TextureRegion> getRunAnimation(String heroName) {
        return runAnimations.get(heroName);
    }

    public void dispose() {
        background.dispose();
        skin.dispose();
        for (Animation<TextureRegion> anim : idleAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
        for (Animation<TextureRegion> anim : runAnimations.values()) {
            for (TextureRegion region : anim.getKeyFrames()) {
                region.getTexture().dispose();
            }
        }
        idleAnimations.clear();
        runAnimations.clear();
    }
    public Texture getBackground(){
        return background;
    }
    public Skin getSkin() {
        return skin;
    }
}
