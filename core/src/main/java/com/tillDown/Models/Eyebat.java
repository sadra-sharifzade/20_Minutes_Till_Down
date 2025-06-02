package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tillDown.Controllers.WeaponController;

public class Eyebat extends Enemy{
    private float bulletTimer = 0.0f;
    public Eyebat(Vector2 position) {
        super(position);
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("eyebat");
        sprite = new Sprite(animation.getKeyFrame(0));
        hp = 50;
        speed = 50;
    }
    @Override
    public void update(float playerX,float playerY, float delta){
        super.update(playerX,playerY, delta);
        bulletTimer += delta;
        if(bulletTimer > 3f){
            bulletTimer = 0f;
            WeaponController.addEnemyBullet(new Bullet(position.x, position.y,playerX,playerY, 1,80));
        }

    }

}
