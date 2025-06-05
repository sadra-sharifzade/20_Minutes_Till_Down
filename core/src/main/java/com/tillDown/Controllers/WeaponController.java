package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tillDown.Main;
import com.tillDown.Models.Bullet;
import com.tillDown.Models.GameAssetManager;
import com.tillDown.Models.Player;
import com.tillDown.Models.Weapon;

import java.util.ArrayList;

public class WeaponController {
    private final Weapon weapon;
    private final Player player;
    private static ArrayList<Bullet> playerBullets = new ArrayList<>();
    private static ArrayList<Bullet> removedPlayerBullets = new ArrayList<>();
    private static ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private static ArrayList<Bullet> removedEnemyBullets = new ArrayList<>();
    private float autoAimShootTime = 0f;
    public WeaponController(Weapon weapon,Player player) {
        this.weapon = weapon;
        this.player = player;
        playerBullets.clear();
        removedPlayerBullets.clear();
        enemyBullets.clear();
        removedEnemyBullets.clear();
    }
    public static void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
    }

    public static void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
    }

    public static void removePlayerBullet(Bullet bullet) {removedPlayerBullets.add(bullet);}

    public static void removeEnemyBullet(Bullet bullet) {removedEnemyBullets.add(bullet);}

    public void update(float delta) {
        autoAimShootTime += delta;
        if (Main.isAutoAimEnabled() && autoAimShootTime >= 0.3f){
            autoAimShootTime = 0f;
            Vector2 nearestEnemy = EnemiesController.findNearestEnemy(player.getX(),player.getY());
            if (!nearestEnemy.equals(new Vector2())) {
                Gdx.input.setCursorPosition((int)(2*nearestEnemy.x-(2*player.getX()-Gdx.graphics.getWidth()/2)),(int)((2*player.getY()+Gdx.graphics.getHeight()/2)-2*nearestEnemy.y));
                handleWeaponShoot(nearestEnemy.x,nearestEnemy.y,player.getX(),player.getY());
            }
        }
        updateAndDrawBullets(delta);

        if(Gdx.input.isKeyJustPressed(Main.getKeyBindings().get("Reload")) || (Main.isAutoReloadEnabled() && !weapon.isReload() && weapon.getCurrentAmmo()==0)){
            weapon.setRemainingReloadTime(weapon.getReloadTime());
            weapon.setReload(true);
        }
        weapon.setPosition(player.getX(), player.getY()-5);
        weapon.draw(delta);
        playerBullets.removeAll(removedPlayerBullets);
        removedPlayerBullets.clear();
        enemyBullets.removeAll(removedEnemyBullets);
        removedEnemyBullets.clear();
    }
    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }
    public void handleWeaponShoot(float x, float y,float originX, float originY) {
        if(!weapon.isReload()){
            if (weapon.decreaseAmmo()){
                GameAssetManager.getGameAssetManager().playSound("shoot");
                for(int i = 0; i< weapon.getProjectile();i++)
                    playerBullets.add(new Bullet(originX, originY,x+i*40,y+i*40,(int)(weapon.getDamage()*player.getDamager()),250,true));
            }

        }
    }

    public void updateAndDrawBullets(float delta) {
        SpriteBatch batch = Main.getBatch();
        for (Bullet b : playerBullets) {
            b.update(delta);
            b.draw(batch);
            player.increaseNumKills(EnemiesController.checkEnemyCollision(b));
        }
        for (Bullet b : enemyBullets) {
            Main.getGameView().getController().getPlayerController().checkPlayerCollision(b);
            b.update(delta);
            b.draw(batch);
        }
    }


}
