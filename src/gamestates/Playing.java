package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods{

    Player player;
    LevelManager levelManager;
    ObjectManager objectManager;

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        loadNextLevel();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        player = new Player(200, 200, (int)(52 * Game.SCALE), (int)(63 * Game.SCALE), this);
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
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        levelManager.draw(g);
        objectManager.draw(g);
        player.draw(g);
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
}
