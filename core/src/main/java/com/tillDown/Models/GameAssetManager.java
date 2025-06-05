package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl3.audio.Wav;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tillDown.Main;

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
    private Animation<TextureRegion> damageAnimation;
    private Animation<TextureRegion> heartAnimation;
    private Texture shotgunTexture;
    private Texture smgTexture;
    private Texture revolverTexture;
    private Texture bulletTexture;
    private Texture orbTexture;
    private Texture circleTexture;
    private Texture ammoTexture;
    private Texture emptyHeartTexture;
    private Sound shootSound;
    private Sound damageSound;
    private Sound levelUpSound;
    private Sound loseSound;
    private Sound winSound;

    public Texture getCircleTexture() {return circleTexture;}
    public static void changeCursor(){
        Pixmap pixmap = new Pixmap(Gdx.files.internal("assets/images/cursor.png"));
        int xHotspot = pixmap.getWidth() / 2;
        int yHotspot = pixmap.getHeight() / 2;
        Cursor cursor = Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }
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
        orbTexture = new Texture("assets/images/orb.png");
        circleTexture = new Texture("assets/images/circle.png");
        ammoTexture = new Texture("assets/images/ammo.png");
        emptyHeartTexture = new Texture("assets/images/emptyHeart.png");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sfx/shoot.wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("sfx/lose.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("sfx/win.wav"));
        damageSound = Gdx.audio.newSound(Gdx.files.internal("sfx/damage.wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("sfx/levelUp.wav"));
        loadEnemies();
        loadDamageAnimation();
        loadHeartAnimation();
    }
    public void playSound(String sound){
        if (!Main.isSFXEnabled()) return;
        switch (sound){
            case "shoot": shootSound.play(0.5f); break;
            case "lose": Main.getCurrentMusic().pause();loseSound.play();Main.getCurrentMusic().play(); break;
            case "win": Main.getCurrentMusic().pause();winSound.play();Main.getCurrentMusic().play(); break;
            case "damage": damageSound.play(); break;
            case "levelUp": levelUpSound.play(); break;
        }
    }
    public Texture getWeaponTexture(String weaponName){
        switch (weaponName){
            case "revolver": return revolverTexture;
            case "smgs dual": return smgTexture;
            case "shotgun": return shotgunTexture;
        }
        return null;
    }
    public Texture getEmptyHeartTexture() {return emptyHeartTexture;}

    public Texture getOrbTexture() {return orbTexture;}

    public void loadDamageAnimation() {
        FileHandle animDir = Gdx.files.internal("assets/images/damage");
        FileHandle[] frameFiles = animDir.list((dir, name) -> name.endsWith(".png"));
        Arrays.sort(frameFiles, Comparator.comparing(FileHandle::name));

        TextureRegion[] frames = new TextureRegion[frameFiles.length];
        for (int i = 0; i < frameFiles.length; i++) {
            frames[i] = new TextureRegion(new Texture(frameFiles[i]));
        }

        damageAnimation = new Animation(0.1f, frames);
        damageAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
    public void loadHeartAnimation() {
        FileHandle animDir = Gdx.files.internal("assets/images/heart");
        FileHandle[] frameFiles = animDir.list((dir, name) -> name.endsWith(".png"));
        Arrays.sort(frameFiles, Comparator.comparing(FileHandle::name));

        TextureRegion[] frames = new TextureRegion[frameFiles.length];
        for (int i = 0; i < frameFiles.length; i++) {
            frames[i] = new TextureRegion(new Texture(frameFiles[i]));
        }

        heartAnimation = new Animation(0.15f, frames);
        heartAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getDamageAnimation() {return damageAnimation;}

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

    public Animation<TextureRegion> getHeartAnimation() {return heartAnimation;}

    public Texture getAmmoTexture() {return ammoTexture;}

    public void loadAnimation(String heroName, String action) {
        FileHandle animDir = Gdx.files.internal("assets/images/heroes/" + heroName + "/" + action);
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
