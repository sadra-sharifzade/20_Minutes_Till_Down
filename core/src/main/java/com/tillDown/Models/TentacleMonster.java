package com.tillDown.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy{
    public TentacleMonster(Vector2 position) {
        super(position);
        name = "tentacle";
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("tentacle");
        sprite = new Sprite(animation.getKeyFrame(0));
        hp=25;
        speed=100;
    }
    public TentacleMonster() {}
    @Override
    public void reInitialize() {
        super.reInitialize();
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("tentacle");
        sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setPosition(x, y);
    }
}
