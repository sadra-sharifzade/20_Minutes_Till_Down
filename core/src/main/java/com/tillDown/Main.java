package com.tillDown;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tillDown.Models.User;
import com.tillDown.Views.MainMenuView;

import java.util.ArrayList;

public class Main extends Game{
    private static Screen currentScreen;
    private static SpriteBatch batch;
    private static Main main;
    private static ArrayList<User> users;

    @Override
    public void create() {
        //TODO load users from json
        main = this;
        batch = new SpriteBatch();
        currentScreen = new MainMenuView();
    }
}
