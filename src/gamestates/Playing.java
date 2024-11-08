package gamestates;

import entities.EnemyManager;
import entities.Player;
import entities.TankType;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.LoadSave.LoadTankImages;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private EnemyManager enemyManager;

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        loadImages();
        initClasses();

        loadNextLevel();
    }

    private void loadImages() {
        LoadTankImages();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        enemyManager = new EnemyManager(this);
        player = new Player(TankType.T_BASE, 200, 200, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE), this);
    }

    private void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
    }



    private void resetAll() {
        player.resetAll();
    }

    @Override
    public void update() {
        objectManager.update();
        player.update();
        enemyManager.update();
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        levelManager.draw(g);
        objectManager.draw(g);
        player.draw(g);
        enemyManager.draw(g);
        levelManager.drawAfterPlayer(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            //gameOverOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_W -> player.setUp(true);
                case KeyEvent.VK_S -> player.setDown(true);
                case KeyEvent.VK_SPACE -> player.setAttacking(true);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) {
            //gameOverOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_W -> player.setUp(false);
                case KeyEvent.VK_S -> player.setDown(false);
                case KeyEvent.VK_SPACE -> player.setAttacking(false);
            }
        }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
}
