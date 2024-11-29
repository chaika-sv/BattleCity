package gamestates;

import entities.EnemyManager;
import entities.Player;
import entities.TankType;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverPauseOverlay;
import ui.InfoPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utils.Constants.LevelConstants.*;
import static utils.LoadSave.ENEMY_SETTINGS;
import static utils.LoadSave.LoadTankImages;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private EnemyManager enemyManager;
    private GameOverPauseOverlay gameOverPauseOverlay;
    private InfoPanel infoPanel;

    private boolean gameOver = false;
    private boolean pause = false;

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
        gameOverPauseOverlay = new GameOverPauseOverlay(this);
        infoPanel = new InfoPanel(this);
    }

    private void startGame() {
        resetAll();     // just in case
        levelManager.loadFirstLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));
    }

    private void startCurrentLevelAgain() {
        levelManager.reloadCurrentLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));
    }

    public void restartLevel() {
        resetAll();
        startCurrentLevelAgain();
        setGameOver(false);
        setPause(false);
        Gamestate.state = Gamestate.PLAYING;
    }

    public void pauseGame() {
        setPause(true);
        Gamestate.state = Gamestate.PAUSE;
    }

    public void resumeToGameAfterPause() {
        setPause(false);
        Gamestate.state = Gamestate.PLAYING;
    }


    private void resetAll() {
        player.resetAll();
        objectManager.resetAll();
        enemyManager.resetAll();
    }




    @Override
    public void update() {

        if (gameOver || pause) {
            gameOverPauseOverlay.update();
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

        if (gameOver || pause) {
            gameOverPauseOverlay.draw(g);
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
        if (gameOver || pause) {
            gameOverPauseOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_W -> player.setUp(true);
                case KeyEvent.VK_S -> player.setDown(true);
                case KeyEvent.VK_SPACE -> player.setAttacking(true);
                case KeyEvent.VK_ESCAPE -> pauseGame();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver || pause) {
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

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public GameOverPauseOverlay getGameOverOverlay() {
        return gameOverPauseOverlay;
    }
}
