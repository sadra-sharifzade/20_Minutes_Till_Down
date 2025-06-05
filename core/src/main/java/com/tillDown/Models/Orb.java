package com.tillDown.Models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Orb {
    private float x,y;
    private Sprite sprite;
    public Orb(float x, float y) {
        this.x = x;
        this.y = y;
        sprite = new Sprite(GameAssetManager.getGameAssetManager().getOrbTexture());
        sprite.setPosition(x, y);
        sprite.setSize(5,5);
    }
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
    public Rectangle getBounds() {return sprite.getBoundingRectangle();}
}
