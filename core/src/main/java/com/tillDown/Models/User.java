package com.tillDown.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static int idCounter = 0;
    public static int savedGamesCounter = 0;
    private int id;
    private int savedGameId = 0;
    private String username;
    private String password;
    private String answerToSecurityQuestion;
    private String avatarName;
    private int numKills;
    private int score;
    private int mostTimeAlive;//in seconds
    private List<Integer> gainedAbilities =new ArrayList<Integer>(); //0:vitality,1:damager,2:procrease,3:amocrease,4:speedy
    public User(String username, String password, int avatarId) {
        this.username = username;
        this.password = password;
        this.avatarName = String.valueOf(avatarId)+".png";
        this.numKills = 0;
        this.score = 0;
        this.mostTimeAlive = 0;
        id = ++idCounter;
    }

    public List<Integer> getGainedAbilities() {return gainedAbilities;}

    public int getSavedGameId() {return savedGameId;}

    public void setSavedGameId(int savedGameId) {this.savedGameId = savedGameId;}

    public String getAnswerToSecurityQuestion() {return answerToSecurityQuestion;}

    public void setAnswerToSecurityQuestion(String answerToSecurityQuestion) {
        this.answerToSecurityQuestion = answerToSecurityQuestion;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public String getAvatarName() {return avatarName;}

    public void setAvatarName(String avatarName) {this.avatarName = avatarName;}

    public void setPassword(String password) {this.password = password;}

    public int getNumKills() {return numKills;}

    public void setNumKills(int numKills) {this.numKills = numKills;}

    public int getScore() {return score;}

    public void setScore(int score) {this.score = score;}

    public int getMostTimeAlive() {return mostTimeAlive;}

    public void setMostTimeAlive(int mostTimeAlive) {this.mostTimeAlive = mostTimeAlive;}
}
