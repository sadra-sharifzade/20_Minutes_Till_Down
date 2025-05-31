package com.tillDown;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.tillDown.Controllers.SignupMenuContoller;
import com.tillDown.Models.Player;
import com.tillDown.Models.User;
import com.tillDown.Views.MainMenuView;
import com.tillDown.Views.ProfileMenuView;
import com.tillDown.Views.SignupMenuView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main extends Game{
    private static SpriteBatch batch;
    private static Main main;
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private static Music currentMusic;
    private static boolean isSFXEnabled = true;
    private static boolean isAutoReloadEnabled = false;
    private static boolean isBlackAndWhiteEnabled = false;
    private static ShaderProgram grayscaleShader;
    private static ProfileMenuView profileMenuView;//for drag and drop
    private static Map<String, Integer> keyBindings;
    private static String heroName;
    private static String weaponName;
    private static int gameTime;

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
    public static void setIsBlackAndWhiteEnabled(boolean isBlackAndWhiteEnabled) {}

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

    public static void addUser(User user) {
        users.add(user);
    }
    @Override
    public void create() {
        //TODO load users from json
        main = this;
        batch = new SpriteBatch();
        setScreen(new SignupMenuView());
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/Pretty Dungeon.wav"));
        currentMusic.setLooping(true);
        currentMusic.play();
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );
        System.out.println(grayscaleShader.isCompiled());
        keyBindings = new HashMap<>();
        keyBindings.put("Move Left", Input.Keys.A);
        keyBindings.put("Move Right", Input.Keys.D);
        keyBindings.put("Move Up", Input.Keys.W);
        keyBindings.put("Move Down", Input.Keys.S);
    }
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
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
