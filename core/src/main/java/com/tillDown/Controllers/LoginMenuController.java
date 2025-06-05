package com.tillDown.Controllers;

import com.tillDown.Main;
import com.tillDown.Models.User;
import com.tillDown.Views.LoginMenuView;
import com.tillDown.Views.MainMenuView;

import java.io.IOException;

public class LoginMenuController {
    private LoginMenuView view;
    public LoginMenuController(LoginMenuView view) {
        this.view = view;
    }
    public void login(String username, String password) {
        User user = Main.getUserByUsername(username);
        if (user == null) view.showError("User not found!");
        else if (!user.getPassword().equals(password)) view.showError("Wrong password!");
        else {
            try {
                Main.load("userData/"+user.getId()+"/settings.json");
            } catch (IOException ignored) {
                try {
                    Main.load("defaultSettings.json");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            Main.setCurrentUser(user);
            Main.getMain().setScreen(new MainMenuView());
            view.dispose();
        }
    }
    public void forgetPassword(String username,String securityQuestion) {
        User user = Main.getUserByUsername(username);
        if (user == null) view.showError("User not found!");
        else if (!user.getAnswerToSecurityQuestion().equals(securityQuestion)) view.showError("Wrong Answer!");
        else {
            view.showError("Your password is: " + user.getPassword());
        }
    }
}
