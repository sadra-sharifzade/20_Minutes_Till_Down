package com.tillDown.Models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tillDown.Main;

public class Weapon {
    private String name;
    private Sprite sprite;
    private int damage;
    private final int maxAmmo;
    private int currentAmmo;
    private int projectile;
    private final float reloadTime;
    private float remainingReloadTime;

    public Weapon(String name){
        name=name.toLowerCase();
        this.name = name;
        switch (name){
            case "revolver":
                sprite = new Sprite(GameAssetManager.getGameAssetManager().getRevolverTexture());
                damage = 20;
                maxAmmo = 6;
                projectile = 1;
                reloadTime = 1f;
                break;
            case "shotgun":
                sprite = new Sprite(GameAssetManager.getGameAssetManager().getShotgunTexture());
                damage = 10;
                projectile = 4;
                reloadTime = 1f;
                maxAmmo = 2;
                break;
            case "smgs dual":
                sprite = new Sprite(GameAssetManager.getGameAssetManager().getSmgTexture());
                damage = 8;
                projectile = 1;
                reloadTime = 2f;
                maxAmmo = 24;
                break;
            default:
                reloadTime = 1f;
                maxAmmo = 24;
        }
        remainingReloadTime = 0f;
        currentAmmo = maxAmmo;
    }
    public void draw(){
        sprite.draw(Main.getBatch());
    }
    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {return sprite;}

    public int getDamage() {return damage;}

    public void setDamage(int damage) {this.damage = damage;}

    public int getMaxAmmo() {return maxAmmo;}

    public int getCurrentAmmo() {return currentAmmo;}

    public void setCurrentAmmo(int currentAmmo) {this.currentAmmo = currentAmmo;}

    public int getProjectile() {return projectile;}

    public void setProjectile(int projectile) {this.projectile = projectile;}

    public float getReloadTime() {return reloadTime;}

    public float getRemainingReloadTime() {return remainingReloadTime;}

    public void setRemainingReloadTime(float remainingReloadTime) {this.remainingReloadTime = remainingReloadTime;}
    public void decreaseAmmo(){
        currentAmmo--;
        if(currentAmmo == 0){
            remainingReloadTime = reloadTime;
        }
    }

    public void decreaseRemainingReloadTime(float delta) {
        remainingReloadTime -= delta;
        if (remainingReloadTime <= 0) {
            remainingReloadTime = 0f;
            currentAmmo = maxAmmo;
        }
    }
}
