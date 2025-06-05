package com.tillDown.Controllers;

import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tillDown.Main;
import com.tillDown.Models.User;
import com.tillDown.Views.MainMenuView;
import com.tillDown.Views.SignupMenuView;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SignupMenuContoller {
    private final SignupMenuView view;
    public SignupMenuContoller(SignupMenuView view) {
        this.view = view;
    }

    public void handleSignup(String username, String password) {
        if (Main.getUserByUsername(username)!=null) view.showError("Username is already taken!");
        else if (username.equals("guest")) view.showError("guest is a reserved username!");
        else if (password.length()<8) view.showError("Password must be at least 8 characters!");
        else if (!password.matches(".*[@#$%&*()_]+.*")) view.showError("Password should contain at least one of this characters: @#$%&*()_!");
        else if (!password.matches(".*\\d+.*")) view.showError("Password should contain at least one digit");
        else if (!password.matches(".*[A-Z]+.*")) view.showError("Password should contain at least one uppercase letter");
        else {
            User user = new User(username, password, (new Random().nextInt(10))+1);
            Main.addUser(user);
            Main.setCurrentUser(user);
            view.getErrorLabel().setColor(Color.GREEN);
            view.disableButtons();
            view.showError("Signup successful! Please answer the security question.");
            view.visibleSecurityQuestion("where were you born?");

        }
    }
    public void loginAsGuest() {
        User user = new User("Guest", "guest", (new Random().nextInt(10))+1);
        Main.setCurrentUser(user);
        Main.getMain().setScreen(new MainMenuView());
        view.dispose();
    }
}
