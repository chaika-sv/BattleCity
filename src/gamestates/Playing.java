package gamestates;

import entities.EnemyManager;
import entities.EnemySettings;
import entities.Player;
import entities.TankType;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.InfoPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static main.Game.TILES_DEFAULT_SIZE;
import static utils.Constants.LevelConstants.*;
import static utils.LoadSave.ENEMY_SETTINGS;
import static utils.LoadSave.LoadTankImages;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private InfoPanel infoPanel;

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        loadImages();
        initClasses();

        startGame();

    }

    private void loadImages() {
        LoadTankImages();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);
        enemyManager = new EnemyManager(this);
        player = new Player(TankType.T_BASE_PLAYER, PLAYER_SPAWN_X, PLAYER_SPAWN_Y, this);
        gameOverOverlay = new GameOverOverlay(this);
        infoPanel = new InfoPanel(this);
    }

    private void startGame() {
        resetAll();     // just in case
        levelManager.loadFirstLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));
    }

    public void startCurrentLevelAgain() {
        levelManager.reloadCurrentLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));
    }

    public void resetAll() {
        player.resetAll();
        objectManager.resetAll();
        enemyManager.resetAll();
    }

    @Override
    public void update() {

        if (gameOver) {
            gameOverOverlay.update();
        } else {
            // Still playing
            objectManager.update();
            if (player.isActive())
                player.update();
            enemyManager.update();
        }
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        infoPanel.draw(g);

        levelManager.draw(g);
        objectManager.draw(g);

        if (player.isActive())
            player.draw(g);

        enemyManager.draw(g);
        objectManager.drawAfterPlayer(g);
        levelManager.drawAfterPlayer(g);

        if (gameOver) {
            gameOverOverlay.draw(g);
        }
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
            gameOverOverlay.keyPressed(e);
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

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


}
