package com.tillDown.Models;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Tree extends Enemy {
    public Tree(Vector2 pos) {
        super(pos);
        name = "tree";
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("tree");
        sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setPosition(pos.x, pos.y);
    }
    public Tree(){}
    @Override
    public void reInitialize() {
        super.reInitialize();
        animation = GameAssetManager.getGameAssetManager().getEnemyAnimations("tree");
        sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setPosition(x, y);
    }
    @Override
    public void update(float playerX, float playerY, float delta) {
    }
}
