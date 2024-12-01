package main;

import editor.Editor;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

public class Game implements Runnable{

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;        // updates per second

    private Playing playing;
    private Menu menu;
    private Editor editor;

    public final static int TILES_DEFAULT_SIZE = 64;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 15;        // visible tiles
    public final static int TILES_IN_HEIGHT = 15;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    public final static int INFO_WIDTH = TILES_SIZE * 2;
    public final static int FULL_GAME_WIDTH = GAME_WIDTH + INFO_WIDTH;


    public Game() {

        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();           // to listen inputs

        startGameLoop();

    }

    private void initClasses() {
        playing = new Playing(this);
        menu = new Menu(this, playing);
        //editor = new Editor();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        // Game loop is here

        double timePerFrame = 1000000000.0 / FPS_SET;       // nanosecond / fps
        double timePerUpdate = 1000000000.0 / UPS_SET;       // nanosecond / ups
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;      // delta update
        double deltaF = 0;      // delta frame

        while (true) {
            long currentTime = System.nanoTime();

            // deltaU will be 0.0 or more WHEN the duration since last update is equal OR more than timePerUpdate
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                // it's time to update
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                // It's time for the next frame. Let's paint it
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // At the same time we are calculating actual FPS (how many frames we paint per second)
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }


    public void update() {

        switch (Gamestate.state) {
            case PLAYING, GAMEOVER, PAUSE -> {
                playing.update();
            }
            case MENU -> {
                menu.update();
            }
            case OPTIONS -> {
                //gameOptions.update();
            }
            case QUIT -> {
                System.exit(0);
            }
        }

    }

    public void draw(Graphics g) {

        switch (Gamestate.state) {
            case PLAYING, GAMEOVER, PAUSE -> {
                playing.draw(g);
            }
            case MENU -> {
                menu.draw(g);
            }
            case OPTIONS -> {
                //gameOptions.draw(g);
            }
        }

    }

    public void windowFocusLost() {
        // Todo: reset something
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }
}
