package gamestates;

import entities.EnemyManager;
import entities.Player;
import entities.TankType;
import levels.Level;
import levels.LevelBlock;
import levels.LevelBlockType;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverPauseOverlay;
import ui.InfoPanel;
import ui.LevelCompleteOverlay;
import ui.StartLevelOverlay;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Audio.*;
import static utils.Constants.LevelConstants.*;
import static utils.Constants.PowerUpConstants.FREEZE_TIME_MS;
import static utils.Constants.PowerUpConstants.WALL_TIME_MS;
import static utils.LoadSaveImages.ENEMY_SETTINGS;
import static utils.LoadSaveImages.LoadTankImages;

public class Playing extends State implements Statemethods{

    private Player player;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private EnemyManager enemyManager;
    private GameOverPauseOverlay gameOverPauseOverlay;
    private StartLevelOverlay startLevelOverlay;
    private LevelCompleteOverlay levelCompleteOverlay;
    private InfoPanel infoPanel;

    private boolean gameOver = false;
    private boolean pause = false;
    private boolean startLevel = false;
    private boolean levelComplete = false;
    private boolean freeze = false;
    private boolean metalWall = false;

    private long freezeStartTimeMS;
    private long metalWallTimeMS;


    public Playing(Game game) {
        super(game);
        loadImages();
        initClasses();
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
        startLevelOverlay = new StartLevelOverlay(this);
        levelCompleteOverlay = new LevelCompleteOverlay(this);
        infoPanel = new InfoPanel(this);
    }


    public void startGameWithStartLevelOverlay() {
        // We are starting the game ...
        startGame();
        // ... but for now pause everything and show the Start Level overlay
        Gamestate.state = Gamestate.START_LEVEL;
        setStartLevel(true);
    }

    private void startGame() {
        resetAll();
        levelManager.loadFirstLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));

    }


    public void returnToGameFromStartLevelOverlay() {
        Gamestate.state = Gamestate.PLAYING;
        setStartLevel(false);
        game.getAudioPlayer().playEffect(LEVEL_INTRO);
    }




    public void restartLevel() {
        resetAll();
        startCurrentLevelAgain();
        setGameOver(false);
        setPause(false);
        Gamestate.state = Gamestate.PLAYING;
        game.getAudioPlayer().playEffect(LEVEL_INTRO);
    }

    private void startCurrentLevelAgain() {
        levelManager.reloadCurrentLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));
    }


    public void levelComplete() {
        if (levelManager.isLastLevel()) {
            // todo: no more levels
            goToMainMenu();
        } else {
            game.getAudioPlayer().playEffect(VICTORY);
            Gamestate.state = Gamestate.LEVEL_COMPLETE;
            setLevelComplete(true);
        }
    }

    public void returnToGameFromLevelCompleteOverlay() {
        setLevelComplete(false);

        resetAll();
        levelManager.loadNextLevel();

        getObjectManager().createShield(player);
        enemyManager.applyEnemySettings(ENEMY_SETTINGS.get(levelManager.getCurrentLevelIndex()));

        // ... but for now pause everything and show the Start Level overlay
        Gamestate.state = Gamestate.START_LEVEL;
        setStartLevel(true);
    }


    public void pauseGame() {
        setPause(true);
        Gamestate.state = Gamestate.PAUSE;
        gameOverPauseOverlay.setDefaultMenuItemSelected();

        game.getAudioPlayer().playEffect(PAUSE);
    }

    public void resumeToGameAfterPause() {
        setPause(false);
        Gamestate.state = Gamestate.PLAYING;
    }



    public void goToMainMenu() {
        resetAll();
        setPause(false);
        setGameOver(false);
        Gamestate.state = Gamestate.MENU;
    }

    public void gameOver() {
        setGameOver(true);
        Gamestate.state = Gamestate.GAME_OVER;
        gameOverPauseOverlay.setDefaultMenuItemSelected();

        game.getAudioPlayer().playEffect(GAME_OVER);
    }


    private void resetAll() {
        player.resetAll();
        objectManager.resetAll();
        enemyManager.resetAll();
    }


    public void freezeEnemies() {
        freeze = true;
        freezeStartTimeMS = System.currentTimeMillis();
    }

    public void throwGrenade() {
        enemyManager.blowUpAllEnemies();
    }


    private void clearLevelBlocks(Level level, int x, int y, int width, int height) {
        Rectangle2D.Float rect = new Rectangle2D.Float(x, y, width, height);

        for(LevelBlock b : level.getLevelBlocks())
            if (b.isActive())
                if (rect.intersects(b.getHitbox()))
                    b.setActive(false);
    }

    public void buildWallAroundBase(LevelBlockType blockType) {

        if (blockType == LevelBlockType.METAL_SMALL)
            metalWall = true;
        else if (blockType == LevelBlockType.BRICK_SMALL)
            metalWall = false;

        metalWallTimeMS = System.currentTimeMillis();

        Level currentLevel = levelManager.getCurrentLevel();

        for(Point p : BASE_WALL_POINTS) {
            // Clear brick blocks in place of metal block
            clearLevelBlocks(currentLevel, p.x, p.y, blockType.getWidth(), blockType.getHeight());

            // Add metal block
            currentLevel.addLevelBlock(blockType, p.x, p.y);
        }
    }



    @Override
    public void update() {

        if (gameOver || pause) {
            gameOverPauseOverlay.update();
        } else if (startLevel) {
            startLevelOverlay.update();
        } else if (levelComplete) {
            levelCompleteOverlay.update();
        } else {

            long currentTime = System.currentTimeMillis();

            if (freeze && currentTime - freezeStartTimeMS > FREEZE_TIME_MS)
                freeze = false;

            if (metalWall && currentTime - metalWallTimeMS > WALL_TIME_MS)
                buildWallAroundBase(LevelBlockType.BRICK_SMALL);

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
        } else if (startLevel) {
            startLevelOverlay.draw(g);
        } else if (levelComplete) {
            levelCompleteOverlay.draw(g);
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
        } if (startLevel) {
            startLevelOverlay.keyPressed(e);
        } if (levelComplete) {
            levelCompleteOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_W -> player.setUp(true);
                case KeyEvent.VK_S -> player.setDown(true);
                case KeyEvent.VK_SPACE -> player.setAttacking(true);
                case KeyEvent.VK_ESCAPE -> pauseGame();
                case KeyEvent.VK_M -> game.getAudioPlayer().toggleEffectMute();
                case KeyEvent.VK_N -> game.getAudioPlayer().toggleEffectMuteMove();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver || pause) {
            //gameOverOverlay.keyRelease(e);
        } if (startLevel) {
            //startLevelOverlay.keyRelease(e);
        } if (levelComplete) {
            //startLevelOverlay.keyRelease(e);
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

    public GameOverPauseOverlay getGameOverPauseOverlay() {
        return gameOverPauseOverlay;
    }

    public boolean isStartLevel() {
        return startLevel;
    }

    public StartLevelOverlay getStartLevelOverlay() {
        return startLevelOverlay;
    }

    public void setStartLevel(boolean startLevel) {
        this.startLevel = startLevel;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public LevelCompleteOverlay getLevelCompleteOverlay() {
        return levelCompleteOverlay;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void setLevelComplete(boolean levelComplete) {
        this.levelComplete = levelComplete;
    }
}
