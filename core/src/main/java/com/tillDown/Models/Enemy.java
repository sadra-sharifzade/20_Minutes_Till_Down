package com.tillDown.Models;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
    protected Vector2 position;
    protected String name;
    protected Sprite sprite;
    protected int hp;
    protected int speed;
    protected float runTime = 0f;
    protected Animation<TextureRegion> animation;
    public Enemy(Vector2 position){
        this.position = position;
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
    public void update(float playerX,float playerY, float delta) {
        Vector2 direction = new Vector2(playerX, playerY).sub(this.position);
        if (direction.len2() > 0.001f) {
            direction.nor();
            Vector2 velocity = new Vector2(direction).scl(speed * delta);
            position.add(velocity);
        }
        runTime += delta;
        TextureRegion frame = animation.getKeyFrame(runTime);
        sprite.setRegion(frame);
        sprite.setPosition(position.x, position.y);

        float angle = direction.angleDeg();
        sprite.setRotation(angle);
    }



    public Animation<TextureRegion> getAnimation() {return animation;}

    public Vector2 getPosition() {return position;}
}
