package com.tillDown.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tillDown.Main;
import com.tillDown.Views.AbilityView;
import com.tillDown.Views.GameView;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String characterName;
    private int maxHp;
    private int hp;
    private int xp;
    private int level = 1;
    private int numKills = 0;
    private float damager = 1f;
    private float damagerAbilityRemainingTime=0;
    private float speedyAbilityRemainingTime=0;
    private boolean isSpeedy = false;
    private boolean facingRight = true;
    private float idleTime = 0f;
    private float runTime = 0f;
    private float invincibleTime = 0f;
    private boolean isInvincible = false;
    private float x,y,circleX,circleY;
    @JsonIgnore
    private Sprite sprite;
    @JsonIgnore
    private Sprite circleSprite;
    @JsonIgnore
    private Animation<TextureRegion> idleAnimation;
    @JsonIgnore
    private Animation<TextureRegion> runAnimation;
    @JsonIgnore
    private Animation<TextureRegion> heartAnimation;
    @JsonIgnore
    private Weapon weapon;
    @JsonIgnore
    private Vector2 position = new Vector2();
    private float speed = 0f;
    @JsonIgnore
    private List<Sprite> hearts = new ArrayList<>();
    @JsonIgnore
    private List<Sprite> ammos = new ArrayList<>();
    private List<String> gainedAbilities =new ArrayList<>();
    @JsonIgnore
    private Texture emptyHeart;
    private float heartTime = 0f;
    public List<String> getGainedAbilities() {return gainedAbilities;}
    public Weapon getWeapon() {return weapon;}
    public void beforeSave(){
        x = position.x;
        y = position.y;
        circleX = circleSprite.getX();
        circleY = circleSprite.getY();
    }
    public void reInitialize(Weapon weapon) {
        idleAnimation = GameAssetManager.getGameAssetManager().getIdleAnimation(characterName);
        runAnimation = GameAssetManager.getGameAssetManager().getRunAnimation(characterName);
        heartAnimation = GameAssetManager.getGameAssetManager().getHeartAnimation();
        sprite = new Sprite(idleAnimation.getKeyFrame(idleTime));
        sprite.setPosition(x, y);
        circleSprite = new Sprite(GameAssetManager.getGameAssetManager().getCircleTexture());
        circleSprite.setPosition(circleX, circleY);
        circleSprite.setAlpha(0.25f);
        position = new Vector2(x,y);
        this.weapon = weapon;
        emptyHeart = GameAssetManager.getGameAssetManager().getEmptyHeartTexture();
        for (int i = 0; i < maxHp; i++) {
            hearts.add(new Sprite(heartAnimation.getKeyFrame(0)));
        }
        for (int i = 0; i < weapon.getMaxAmmo(); i++) {
            ammos.add(new Sprite(GameAssetManager.getGameAssetManager().getAmmoTexture()));
        }
    }
    public void increaseMaxHp() {
        maxHp++;
        hp++;
        hearts.add(new Sprite(heartAnimation.getKeyFrame(0)));
    }

    public int getMaxHp() {return maxHp;}

    public int getHp() {return hp;}

    public void setHp(int hp) {this.hp = hp;}
    public Player(){}
    public Player(String name, Weapon weapon) {
        this.weapon = weapon;
        circleSprite = new Sprite(GameAssetManager.getGameAssetManager().getCircleTexture());
        circleSprite.setAlpha(0.25f);
        this.characterName = name.toLowerCase();
        switch (characterName) {
            case "shana":
                hp = 4;
                speed = 200f;
                break;
            case "diamond":
                hp = 7;
                speed = 50f;
                break;
            case "scarlet":
                hp = 3;
                speed = 250f;
                break;
            case "lilith":
                hp = 5;
                speed = 150f;
                break;
            case "dasher":
                hp = 2;
                speed = 500f;
        }
        maxHp = hp;
        idleAnimation = GameAssetManager.getGameAssetManager().getIdleAnimation(name.toLowerCase());
        runAnimation = GameAssetManager.getGameAssetManager().getRunAnimation(name.toLowerCase());
        sprite = new Sprite(idleAnimation.getKeyFrame(idleTime));
        setPosition(0,0);
        heartAnimation = GameAssetManager.getGameAssetManager().getHeartAnimation();
        emptyHeart = GameAssetManager.getGameAssetManager().getEmptyHeartTexture();
        for (int i = 0; i < maxHp; i++) {
            hearts.add(new Sprite(heartAnimation.getKeyFrame(0)));
        }
        for (int i = 0; i < weapon.getMaxAmmo(); i++) {
            ammos.add(new Sprite(GameAssetManager.getGameAssetManager().getAmmoTexture()));
        }
    }

    public void update(float delta) {
        heartTime += delta;
        if (isInvincible) {
            sprite.setAlpha(invincibleTime);
            invincibleTime+=delta;
        }
        if (invincibleTime>=1f) {
            isInvincible = false;
            invincibleTime = 0f;
        }
        if (isSpeedy) {
            speedyAbilityRemainingTime+=delta;
            if (speedyAbilityRemainingTime>=10f) {
                speed/=2;
                isSpeedy = false;
                speedyAbilityRemainingTime=0;
            }
        }
        if(damager==1.25f){
            damagerAbilityRemainingTime+=delta;
            if (damagerAbilityRemainingTime>=10f) {
                damager=1f;
                damagerAbilityRemainingTime=0;
            }
        }
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
        circleSprite.setPosition(position.x-circleSprite.getWidth()/2+10, position.y-circleSprite.getHeight()/2+10);
        sprite.setPosition(position.x, position.y);
        updateHearts();
        updateAmmos();
    }

    public void updateAmmos() {
        for (int i = 0; i < ammos.size(); i++) {
            ammos.get(i).setPosition(Main.getGameView().getCamera().position.x+480-30*i, Main.getGameView().getCamera().position.y+190);
            if (i < weapon.getCurrentAmmo()) ammos.get(i).setColor(Color.WHITE);
            else ammos.get(i).setColor(Color.BLACK);
        }
    }

    public void updateHearts() {
        for (int i = 0; i < maxHp; i++) {
            hearts.get(i).setPosition(Main.getGameView().getCamera().position.x-470+30*i, Main.getGameView().getCamera().position.y+190);
            if (i<hp) hearts.get(i).setRegion(heartAnimation.getKeyFrame(heartTime).getTexture());
            else hearts.get(i).setRegion(emptyHeart);
        }
    }


    public int getLevel() {return level;}
    public void draw(SpriteBatch batch) {
        circleSprite.draw(batch);
        sprite.draw(batch);
        for (int i = 0; i < maxHp; i++) {
            hearts.get(i).draw(batch);
        }
        for (int i=0; i<ammos.size();i++){
            ammos.get(i).draw(batch);
        }
    }
    public float getDamager() {return damager;};
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
    public void addXp(int xp) {
        this.xp += xp;
        if (this.xp >= getNeededXpForLevel(level+1)) {
            level++;
            GameAssetManager.getGameAssetManager().playSound("levelUp");
            Main.getGameView().setPaused(true);
            Main.getMain().setScreen(new AbilityView(this));
        }
    }
    public void getDamage() {
        if (isInvincible) return;
        GameAssetManager.getGameAssetManager().playSound("damage");
        hp--;
        isInvincible = true;
        if (hp==0) {
            Main.getGameView().getController().endGame(false);
        }
    }
    public static int getNeededXpForLevel(int level){
        return 10*level*(level-1);
    }
    public float getLevelPercentage(){
        return ((xp-getNeededXpForLevel(level))*100f)/(getNeededXpForLevel(level+1)-getNeededXpForLevel(level));
    }
    public boolean isInvincible() {return isInvincible;}

    public void increaseNumKills(int i) {
        numKills+=i;
    }

    public void addAbility(String text) {
        gainedAbilities.add(text);
        switch (text){
            case "vitality":
                increaseMaxHp();
                break;
            case "procrease":
                weapon.setProjectile(weapon.getProjectile()+1);
                break;
            case "amocrease":
                weapon.setMaxAmmo(weapon.getMaxAmmo()+5);
                weapon.setCurrentAmmo(weapon.getMaxAmmo());
                for (int i = 0; i < 5; i++) {
                    ammos.add(new Sprite(GameAssetManager.getGameAssetManager().getAmmoTexture()));
                }
                break;
            case "damager":
                damager = 1.25f;
                break;
            case "speedy":
                speed*=2;
                isSpeedy = true;
                break;
        }
    }

    public int getNumKills() {return numKills;}
}
