package com.tillDown;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.GameSettingData;
import com.tillDown.Models.Player;
import com.tillDown.Models.User;
import com.tillDown.Views.GameView;
import com.tillDown.Views.MainMenuView;
import com.tillDown.Views.ProfileMenuView;
import com.tillDown.Views.SignupMenuView;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends Game{
    private static SpriteBatch batch;
    private static Main main;
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private static Music currentMusic;
    private static boolean isSFXEnabled = true;
    private static boolean isAutoReloadEnabled = true;
    private static boolean isBlackAndWhiteEnabled = false;
    private static ShaderProgram grayscaleShader;
    private static ProfileMenuView profileMenuView;//for drag and drop
    private static GameView gameView;
    private static Map<String, Integer> keyBindings;
    private static String heroName;
    private static String weaponName;
    private static int gameTime;
    private static boolean isAutoAimEnabled = false;
    private static boolean isBossFight = false;
    private static float remainingTime = 0;
    private static ObjectMapper mapper = new ObjectMapper();

    public static boolean isBossFight() {return isBossFight;}

    public static void setIsBossFight(boolean isBossFight) {Main.isBossFight = isBossFight;}

    public static boolean isAutoAimEnabled() {return isAutoAimEnabled;}

    public static void setIsAutoAimEnabled(boolean isAutoAimEnabled) {Main.isAutoAimEnabled = isAutoAimEnabled;}

    public static String getHeroName() {return heroName;}

    public static void setHeroName(String heroName) {Main.heroName = heroName;}

    public static String getWeaponName() {return weaponName;}

    public static void setWeaponName(String weaponName) {Main.weaponName = weaponName;}

    public static int getGameTime() {return gameTime;}

    public static void setGameTime(int gameTime) {Main.gameTime = gameTime;}

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void setProfileMenuView(ProfileMenuView profileMenuView) {
        Main.profileMenuView = profileMenuView;
    }

    public static boolean isAutoReloadEnabled() {return isAutoReloadEnabled;}

    public static void setIsAutoReloadEnabled(boolean isAutoReloadEnabled) {
        Main.isAutoReloadEnabled = isAutoReloadEnabled;
    }
    public static boolean isBlackAndWhiteEnabled() {return isBlackAndWhiteEnabled;}
    public static void setIsBlackAndWhiteEnabled(boolean isBlackAndWhiteEnabled) {Main.isBlackAndWhiteEnabled = isBlackAndWhiteEnabled;}

    public static boolean isSFXEnabled() {return isSFXEnabled;}

    public static void setIsSFXEnabled(boolean isSFXEnabled) {Main.isSFXEnabled = isSFXEnabled;}

    public static Music getCurrentMusic() {return currentMusic;}

    public static void setCurrentMusic(Music currentMusic) {Main.currentMusic = currentMusic;}

    public static ArrayList<User> getUsers() {return users;}

    public static User getCurrentUser() {return currentUser;}

    public static void setCurrentUser(User currentUser) {Main.currentUser = currentUser;}
    public static void removeUser(User user) {users.remove(user);}
    public static Map<String, Integer> getKeyBindings() {return keyBindings;}

    public static void setKeyBindings(Map<String, Integer> keyBindings) {
        Main.keyBindings = keyBindings;
    }

    public static ShaderProgram getGrayscaleShader() {return grayscaleShader;}

    public static void addUser(User user) {
        users.add(user);
    }

    public static void setGameView(GameView view) {gameView=view;}
    public static GameView getGameView() {return gameView;}

    public static ObjectMapper getMapper() {return mapper;}
    public static void save(String filepath) throws IOException {
        GameSettingData data = new GameSettingData();
        data.isSFXEnabled = isSFXEnabled;
        data.isAutoReloadEnabled = isAutoReloadEnabled;
        data.isBlackAndWhiteEnabled = isBlackAndWhiteEnabled;
        data.keyBindings = keyBindings;
        data.heroName = heroName;
        data.weaponName = weaponName;
        data.gameTime = gameTime;
        data.remainingTime = gameView==null? 0:gameView.getRemainingTime();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filepath), data);
    }
    public static void load(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        GameSettingData data = mapper.readValue(new File(filepath), GameSettingData.class);

        isSFXEnabled = data.isSFXEnabled;
        isAutoReloadEnabled = data.isAutoReloadEnabled;
        isBlackAndWhiteEnabled = data.isBlackAndWhiteEnabled;
        keyBindings = data.keyBindings;
        heroName = data.heroName;
        weaponName = data.weaponName;
        gameTime = data.gameTime;
        remainingTime = data.remainingTime;
    }

    public static float getRemainingTime() {return remainingTime;}

    @Override
    public void create() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            users = mapper.readValue(new File("users.json"),new TypeReference<ArrayList<User>>() {});
        } catch (IOException ignored) {

        }
        User.setIdCounter(users.size());
        main = this;
        batch = new SpriteBatch();
        GameAssetManager.getGameAssetManager().load();
        setScreen(new SignupMenuView());
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/Pretty Dungeon.wav"));
        currentMusic.setLooping(true);
        currentMusic.play();
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );
        keyBindings = new HashMap<>();
        keyBindings.put("Move Left", Input.Keys.A);
        keyBindings.put("Move Right", Input.Keys.D);
        keyBindings.put("Move Up", Input.Keys.W);
        keyBindings.put("Move Down", Input.Keys.S);
        keyBindings.put("Reload", Input.Keys.R);
        keyBindings.put("Auto-Aim", Input.Keys.SPACE);
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("users.json"),users);
        } catch (IOException ignored) {

        }
        batch.dispose();
        GameAssetManager.getGameAssetManager().dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public void handleDroppedFiles(String[] files) {
        profileMenuView.getController().handleDroppedFiles(files);
    }
}
