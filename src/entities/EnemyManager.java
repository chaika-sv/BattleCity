package entities;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.util.ArrayList;

public class EnemyManager {

    private Playing playing;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;

        enemies.add(new Enemy(TankType.T_BIG_GUN,  100, 100, (int)(52 * Game.SCALE), (int)(63 * Game.SCALE), playing ));
    }

    public void update() {

    }

    public void draw(Graphics g) {
        for (Enemy e : enemies)
            if (e.isActive())
                e.draw(g);
    }
}
