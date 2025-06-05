package com.tillDown.Controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tillDown.Main;
import com.tillDown.Models.*;
import com.tillDown.Views.GameView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class EnemiesController {
    private static ArrayList<Enemy> enemies = new ArrayList<>();
    private static ArrayList<Enemy> removedEnemies = new ArrayList<>();
    private float tentacleSpawnTime = 0;
    private float eyeBatSpawnTime = 0;
    private boolean isElderSpawned = false;
    private OrthographicCamera camera;
    private final int time = Main.getGameTime() * 60;

    public static ArrayList<Enemy> getEnemies() {return enemies;}

    public static void setEnemies(ArrayList<Enemy> enemies) {EnemiesController.enemies = enemies;}

    public EnemiesController(OrthographicCamera camera,boolean isNew) {
        this.camera = camera;
        if(!isNew) return;
        enemies.clear();
        removedEnemies.clear();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 50; i++) {
            enemies.add(new Tree(new Vector2(random.nextInt(GameView.MAP_WIDTH),random.nextInt(GameView.MAP_HEIGHT))));
        }
    }
    public static void checkEnemyCollision(Player player) {
        if (player.isInvincible()) return;
        for (Enemy e : enemies) {
            if (e.getBounds().overlaps(player.getBounds())) {
                player.getDamage();
                if(!e.getName().equals("tree"))e.moveBack();
            }
        }
    }
    public static int checkEnemyCollision(Bullet bullet) {
        int cnt = 0;
        for (Enemy e : enemies) {
            if (e.getBounds().overlaps(bullet.getBounds())) {
                if(!e.getName().equals("tree")){
                    if(!e.getName().equals("elder"))e.moveBack();
                    if(e.decreaseHp(bullet.getDamage()))cnt++;
                }
                WeaponController.removePlayerBullet(bullet);
            }
        }
        return cnt;
    }
    public static Vector2 findNearestEnemy(float x, float y) {
        float min = 1000;
        Vector2 nearest = new Vector2();
        for (Enemy e : enemies) {
            if (e.getName().equals("tree")) continue;
            if (e.getPosition().dst(x, y) < min){
                min = e.getPosition().dst(x, y);
                nearest = e.getPosition().cpy();
                nearest.x += e.getSprite().getWidth()/2;
                nearest.y += e.getSprite().getHeight()/2;
            }
        }
        return nearest;
    }

    public static void removeEnemy(Enemy enemy) {removedEnemies.add(enemy);}

    public void update(float delta) {
        for (Enemy e : enemies) {
            e.update(camera.position.x,camera.position.y,delta);
            e.draw(Main.getBatch());
        }
        enemies.removeAll(removedEnemies);
        removedEnemies.clear();
        tentacleSpawnTime += delta;
        eyeBatSpawnTime += delta;
        float elapsedTime = Main.getGameView().getElapsedTime();
        if (tentacleSpawnTime >= 3) {
            tentacleSpawnTime = 0;
            for (int i = 0; i < elapsedTime / 30; i++) {
                enemies.add(new TentacleMonster(getOffScreenSpawnPosition()));
            }
        }
        if (elapsedTime >= (time/4)) {
            if (eyeBatSpawnTime >= 10) {
                eyeBatSpawnTime = 0;
                for (int i = 0; i < (4 * elapsedTime - time + 30) / 30; i++) {
                    enemies.add(new Eyebat(getOffScreenSpawnPosition()));
                }
            }
        }
        if ((!isElderSpawned && elapsedTime >= time / 2)||Main.isBossFight()) {
            //todo generate margin
            Main.setIsBossFight(false);
            enemies.add(new Elder(getOffScreenSpawnPosition()));
            isElderSpawned = true;
        }

    }

    public Vector2 getOffScreenSpawnPosition() {
        float camX = camera.position.x;
        float camY = camera.position.y;
        float viewWidth = camera.viewportWidth * camera.zoom;
        float viewHeight = camera.viewportHeight * camera.zoom;
        float left = camX - viewWidth / 2f;
        float right = camX + viewWidth / 2f;
        float bottom = camY - viewHeight / 2f;
        float top = camY + viewHeight / 2f;
        float x = 0, y = 0;
        int edge = MathUtils.random(3);
        switch (edge) {
            case 0:
                x = MathUtils.random(left, right);
                y = top + 50;
                break;
            case 1:
                x = MathUtils.random(left, right);
                y = bottom - 50;
                break;
            case 2:
                x = left - 50;
                y = MathUtils.random(bottom, top);
                break;
            case 3:
                x = right + 50;
                y = MathUtils.random(bottom, top);
                break;
        }
        return new Vector2(x, y);
    }

    public void killEnemies(float x, float y) {
        for (Enemy e : enemies) {
            if (Math.abs(x-e.getX())<50 || Math.abs(y-e.getY())<50) {
                removedEnemies.add(e);
            }
        }
    }
}
