package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {


    private Vector2 position;
    private Vector2 direction;
    private float speed;
    private Sprite sprite;
    private int damage;

    public Bullet(float startX, float startY, float targetX, float targetY, int damage,int speed) {
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getBulletTexture());
        sprite.setSize(20 , 20);
        this.position = new Vector2(startX, startY);
        this.direction = new Vector2(targetX - startX, targetY - startY).nor();
        this.speed = speed;
        this.damage = damage;
        this.sprite.setPosition(startX, startY);
    }

    public void update(float delta) {
        position.mulAdd(direction, speed * delta);
        sprite.setPosition(position.x, position.y);
    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
