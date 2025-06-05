package com.tillDown.Models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Elder extends Enemy{
    private float dashTime = 0f;
    public Elder(Vector2 position) {
        super(position);
        name = "elder";
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("elder");
        sprite = new Sprite(animation.getKeyFrame(0));
        hp = 400;
        speed = 20;
    }
    public Elder() {}
    @Override
    public void reInitialize() {
        super.reInitialize();
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("elder");
        sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setPosition(x, y);
    }

    @Override
    public void update(float playerX,float playerY, float delta) {
        super.update(playerX, playerY, delta);
        dashTime += delta;
        if(dashTime > 5f && dashTime < 5.5f) {
            speed = 500;
        } else if(dashTime > 5.5f ) {
            dashTime = 0;
            speed = 20;
        }
    }

}
