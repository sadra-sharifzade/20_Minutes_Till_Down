package com.tillDown.Controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tillDown.Main;
import com.tillDown.Models.User;
import com.tillDown.Views.ScoreboardView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardController {
    private ScoreboardView view;
    public ScoreboardController(ScoreboardView view) {
        this.view = view;
    }
    public void updateScoreboard(String sortType) {
        Table table = view.getTable();
        Skin skin = view.getSkin();
        SelectBox<String> sortSelectBox = view.getSortSelectBox();
        table.clearChildren();
        table.defaults().pad(10);
        table.add(view.getBackButton()).left().width(100).pad(20);
        Label title = new Label("Scoreboard", skin,"title");
        table.add(title).center().colspan(3);
        table.row();
        table.add(sortSelectBox).colspan(4).pad(10).row();

        // Sort and get top 10
        List<User> users = new ArrayList<>(Main.getUsers());
        users.sort(getComparator(sortType));
        List<User> topUsers = users.subList(0, Math.min(10, users.size()));

        // Header row
        table.add(new Label("Rank", skin)).pad(5);
        table.add(new Label("Username", skin)).pad(5);
        table.add(new Label("Kills", skin)).pad(5);
        table.add(new Label("Time Alive", skin)).pad(5);
        table.add(new Label("Score", skin)).pad(5).row();

        // Current user for comparison
        User currentUser = Main.getCurrentUser();

        for (int i = 0; i < topUsers.size(); i++) {
            User u = topUsers.get(i);
            Label rankLabel = new Label(String.valueOf(i + 1), skin);
            Label usernameLabel = new Label(u.getUsername(), skin);
            Label killsLabel = new Label(String.valueOf(u.getNumKills()), skin);
            Label timeAliveLabel = new Label(String.valueOf(u.getMostTimeAlive()), skin);
            Label scoreLabel = new Label(String.valueOf(u.getScore()), skin);

            // Highlight top 3
            if (i == 0) rankLabel.setColor(Color.GOLD);
            else if (i == 1) rankLabel.setColor(Color.GRAY);
            else if (i == 2) rankLabel.setColor(Color.BROWN);

            // Highlight current user
            if (u.equals(currentUser)) {
                usernameLabel.setColor(Color.RED);
                usernameLabel.setFontScale(1.2f); // bold effect
            }

            table.add(rankLabel).pad(5);
            table.add(usernameLabel).pad(5);
            table.add(killsLabel).pad(5);
            table.add(timeAliveLabel).pad(5);
            table.add(scoreLabel).pad(5).row();
        }
    }

    public Comparator<User> getComparator(String type) {
        switch (type) {
            case "Score" :return Comparator.comparingInt(User::getScore).reversed();
            case "Kills" :return Comparator.comparingInt(User::getNumKills).reversed();
            case "Most Time Alive" : return  Comparator.comparingLong(User::getMostTimeAlive).reversed();
            case "Username" :return Comparator.comparing(User::getUsername);
            default : return Comparator.comparingInt(User::getScore);
        }
    }

    }
