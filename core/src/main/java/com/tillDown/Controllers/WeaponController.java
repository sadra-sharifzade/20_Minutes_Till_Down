package com.tillDown.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tillDown.Main;
import com.tillDown.Models.Bullet;
import com.tillDown.Models.Player;
import com.tillDown.Models.Weapon;

import java.util.ArrayList;

public class WeaponController {
    private final Weapon weapon;
    private final Player player;
    private static ArrayList<Bullet> playerBullets = new ArrayList<>();
    private static ArrayList<Bullet> enemyBullets = new ArrayList<>();

    public WeaponController(Weapon weapon,Player player) {
        this.weapon = weapon;
        this.player = player;
    }
    public static void addPlayerBullet(Bullet bullet) {
        playerBullets.add(bullet);
    }

    public static void addEnemyBullet(Bullet bullet) {
        enemyBullets.add(bullet);
    }

    public void update(float delta) {
        updateAndDrawBullets(delta);
        if(Gdx.input.isKeyJustPressed(Main.getKeyBindings().get("Reload"))){
            weapon.setCurrentAmmo(weapon.getMaxAmmo());
            weapon.setRemainingReloadTime(weapon.getReloadTime());
        }
        if(weapon.getRemainingReloadTime()!=0)weapon.decreaseRemainingReloadTime(delta);
        weapon.setPosition(player.getX(), player.getY()-5);
        weapon.draw();
    }
    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }
    public void handleWeaponShoot(float x, float y,float originX, float originY) {
        if(weapon.getRemainingReloadTime()==0f){
            System.out.println(originX+" "+originY+" "+x+" "+y);
            playerBullets.add(new Bullet(originX, originY,x,y,weapon.getDamage(),250));
            weapon.decreaseAmmo();
        }
    }

    public void updateAndDrawBullets(float delta) {
        SpriteBatch batch = Main.getBatch();
        for (Bullet b : playerBullets) {
            b.update(delta);
            b.draw(batch);
        }
        for (Bullet b : enemyBullets) {
            b.update(delta);
            b.draw(batch);
        }
    }

}
