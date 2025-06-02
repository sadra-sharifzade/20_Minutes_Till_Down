package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tillDown.Main;
import com.tillDown.Views.GameView;

public class Player {
    private String characterName;
    private int hp;
    private int xp;
    private int level;
    private String weaponName;
    private int ammo;
    private float reloadTime;
    private boolean hasVitalityAbility;
    private float damagerAbilityRemainingTime=0;
    private boolean hasProcreaseAbility;
    private boolean hasAmocreaseAbility;
    private float speedyAbilityRemainingTime=0;
    private boolean facingRight = true;
    private float idleTime = 0f;
    private float runTime = 0f;

    private Sprite sprite;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;

    private Vector2 position = new Vector2();
    private float speed = 200f;

    public Player(String name) {
        this.characterName = name.toLowerCase();
        idleAnimation = GameAssetManager.getGameAssetManager().getIdleAnimation(name.toLowerCase());
        runAnimation = GameAssetManager.getGameAssetManager().getRunAnimation(name.toLowerCase());
        sprite = new Sprite(idleAnimation.getKeyFrame(idleTime));
        setPosition(0,0);
    }

    public void update(float delta) {
        float dx = 0, dy = 0;
        boolean moving = false;
        if (Gdx.input.isKeyPressed(Main.getKeyBindings().get("Move Up"))) { dy += speed * delta; moving = true; }
        if (Gdx.input.isKeyPressed(Main.getKeyBindings().get("Move Down"))) { dy -= speed * delta; moving = true; }
        if (Gdx.input.isKeyPressed(Main.getKeyBindings().get("Move Left"))) { dx -= speed * delta; moving = true;
            if (facingRight) {
                facingRight = false;
                sprite.flip(true, false);
            }}
        if (Gdx.input.isKeyPressed(Main.getKeyBindings().get("Move Right"))) { dx += speed * delta; moving = true;
            if (!facingRight) {
                facingRight = true;
                sprite.flip(true, false);
            }}
        if (moving) {
            runTime += delta;
            idleTime = 0;
            if (sprite.isFlipX() == facingRight) {
                sprite.flip(true, false);
            }
            sprite.setTexture(runAnimation.getKeyFrame(runTime).getTexture());
        } else {
            idleTime += delta;
            runTime = 0;
            if (sprite.isFlipX() == facingRight) {
                sprite.flip(true, false);
            }
            sprite.setTexture(idleAnimation.getKeyFrame(idleTime).getTexture());
        }
        position.add(dx, dy);
        clampPosition();
        sprite.setPosition(position.x, position.y);
    }

    public int getXp() {return xp;}

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public float getX() {
        return position.x + sprite.getWidth() / 2;
    }

    public float getY() {
        return position.y + sprite.getHeight() / 2;
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public void dispose() {
    }
    public void setPosition(float x, float y) {
        position.set(x, y);
        sprite.setPosition(position.x, position.y);
    }
    public void clampPosition() {
        float spriteWidth = sprite.getWidth();
        float spriteHeight = sprite.getHeight();
        float x = MathUtils.clamp(position.x, 0, GameView.MAP_WIDTH - spriteWidth);
        float y = MathUtils.clamp(position.y, 0, GameView.MAP_HEIGHT - spriteHeight);
        position.set(x, y);
    }
}
