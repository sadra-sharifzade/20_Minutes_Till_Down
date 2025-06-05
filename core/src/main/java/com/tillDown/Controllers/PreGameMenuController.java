package com.tillDown.Controllers;

import com.tillDown.Main;
import com.tillDown.Views.PreGameMenuView;

public class PreGameMenuController {
    private PreGameMenuView view;
    public PreGameMenuController(PreGameMenuView view) {
        this.view = view;
    }

    public void changeHero(String selected) {
        Main.setHeroName(selected);
    }

    public void changeWeapon(String selected) {
        Main.setWeaponName(selected);
    }

    public void changeGameTime(Integer selected) {
        Main.setGameTime(selected);
    }
}
