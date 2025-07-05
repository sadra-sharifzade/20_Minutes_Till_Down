package com.tillDown.Models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tillDown.Controllers.WeaponController;

public class Bullet {
    private Vector2 position;
    private Vector2 direction;
    private float speed;
    @JsonIgnore
    private Sprite sprite;
    private int damage;
    private float playerCollidedTime = 0f;
    private boolean isPlayerCollided = false;
    private boolean isPlayerBullet = false;
    public Bullet(){}
    public Bullet(float startX, float startY, float targetX, float targetY, int damage,int speed,boolean isPlayer) {
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getBulletTexture(isPlayer));
        isPlayerBullet = isPlayer;
        sprite.setSize(20 , 20);
        this.position = new Vector2(startX, startY);
        this.direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.speed = speed;
        this.damage = damage;
        this.sprite.setPosition(startX, startY);
    }
    public void reInitialize() {
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getBulletTexture(isPlayerBullet));
        sprite.setSize(20 , 20);
        sprite.setPosition(position.x, position.y);
    }
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
    public void update(float delta) {
        if (isPlayerCollided) {
            playerCollidedTime += delta;
            sprite.setRegion(GameAssetManager.getGameAssetManager().getDamageAnimation().getKeyFrame(playerCollidedTime));
            if (playerCollidedTime >= 0.5f) {
                isPlayerCollided = false;
                WeaponController.removeEnemyBullet(this);
            }
        }else {
            position.mulAdd(direction, speed * delta);
            sprite.setPosition(position.x, position.y);
        }
    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getX() {return position.x;}
    public float getY() {return position.y;}

    public int getDamage() {return damage;}
    public void setPlayerCollided(boolean isPlayerCollided) {
        this.isPlayerCollided = isPlayerCollided;
    }
}
