package com.tillDown.Controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tillDown.Main;
import com.tillDown.Models.Elder;
import com.tillDown.Models.Enemy;
import com.tillDown.Models.Eyebat;
import com.tillDown.Models.TentacleMonster;

import java.util.ArrayList;

public class EnemiesController {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float elapsedTime = 0;
    private float tentacleSpawnTime = 0;
    private float eyeBatSpawnTime = 0;
    private boolean isElderSpawned = false;
    private OrthographicCamera camera;
    private final int time = Main.getGameTime() * 60;
    public EnemiesController(OrthographicCamera camera) {
        this.camera = camera;
    }
    public void update(float delta) {
        for (Enemy e : enemies) {
            e.update(camera.position.x,camera.position.y,delta);
            e.draw(Main.getBatch());
        }
        elapsedTime += delta;
        tentacleSpawnTime += delta;
        eyeBatSpawnTime += delta;
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
        if (!isElderSpawned && elapsedTime >= time / 10) {
            //todo generate margin
            enemies.add(new Elder(getOffScreenSpawnPosition()));
            isElderSpawned = true;
        }

    }

    private Vector2 getOffScreenSpawnPosition() {
        float camX = camera.position.x;
        float camY = camera.position.y;
        float viewWidth = camera.viewportWidth * camera.zoom;
        float viewHeight = camera.viewportHeight * camera.zoom;

        float left = camX - viewWidth / 2f;
        float right = camX + viewWidth / 2f;
        float bottom = camY - viewHeight / 2f;
        float top = camY + viewHeight / 2f;

        float x = 0, y = 0;

        int edge = MathUtils.random(3); // 0: top, 1: bottom, 2: left, 3: right

        switch (edge) {
            case 0: // top
                x = MathUtils.random(left, right);
                y = top + 50;
                break;
            case 1: // bottom
                x = MathUtils.random(left, right);
                y = bottom - 50;
                break;
            case 2: // left
                x = left - 50;
                y = MathUtils.random(bottom, top);
                break;
            case 3: // right
                x = right + 50;
                y = MathUtils.random(bottom, top);
                break;
        }

        return new Vector2(x, y);
    }
}
