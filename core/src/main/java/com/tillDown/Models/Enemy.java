package com.tillDown.Models;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tillDown.Controllers.EnemiesController;
import com.tillDown.Controllers.OrbsController;
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Tree.class, name = "tree"),
    @JsonSubTypes.Type(value = TentacleMonster.class, name = "tentacle"),
    @JsonSubTypes.Type(value = Eyebat.class, name = "eyebat"),
    @JsonSubTypes.Type(value = Elder.class, name = "elder")
})
public abstract class Enemy {
    @JsonIgnore
    protected Vector2 position;
    protected String name;
    @JsonIgnore
    protected Sprite sprite;
    protected float x,y;
    protected int hp;
    protected int speed;
    protected float runTime = 0f;
    @JsonIgnore
    protected Animation<TextureRegion> animation;
    protected boolean isDead = false;
    protected float deadTime = 0f;
    protected boolean moveBack = false;
    protected float moveBackTime = 0f;
    public Enemy(){}
    public Enemy(Vector2 position){
        this.position = position;
    }
    public void beforeSave(){
        x = position.x;
        y = position.y;
    }
    public void reInitialize(){
        position = new Vector2(x,y);
    }
    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public String getName() {return name;}

    public Sprite getSprite() {return sprite;}
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
    public void update(float playerX, float playerY, float delta) {
        Vector2 direction = new Vector2(playerX, playerY).sub(this.position);
        int a = moveBack ? -4 : 1;
        if (direction.len2() > 0.001f) {
            direction.nor();
            Vector2 velocity = new Vector2(direction).scl(a*speed * delta);
            position.add(velocity);
        }
        TextureRegion frame;
        if (isDead){
            deadTime += delta;
            frame = animation.getKeyFrame(deadTime);
        }else if (moveBack){
            moveBackTime += delta;
            runTime += delta;
            frame = animation.getKeyFrame(runTime);
            if (moveBackTime>0.1f){
                moveBack = false;
                moveBackTime = 0f;
            }
        } else{
            runTime += delta;
            frame = animation.getKeyFrame(runTime);
        }
        if (deadTime > 0.2f){
            deadTime = 0;
            OrbsController.addOrb(new Orb(position.x, position.y));
            EnemiesController.removeEnemy(this);
        }
        sprite.setRegion(frame);
        sprite.setPosition(position.x, position.y);

        float angle = direction.angleDeg();
        sprite.setRotation(angle);
    }
    public float getX() {return position.x;}
    public float getY() {return position.y;}



    public Animation<TextureRegion> getAnimation() {return animation;}

    public Vector2 getPosition() {return position;}

    public void moveBack() {
        moveBack = true;
    }

    public boolean decreaseHp(int damage) {
        hp-=damage;
        if (hp <= 0) {
            animation = GameAssetManager.getGameAssetManager().getDamageAnimation();
            isDead =true;
            return true;
        }
        return false;
    }
}
