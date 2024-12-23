package ui;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.HelpMethods.DrawNumber;
import static utils.LoadSaveImages.*;

public class InfoPanel {

    private Playing playing;

    public InfoPanel(Playing playing) {
        this.playing = playing;

        loadImages();
    }

    private void loadImages() {
        LoadInfoPanelImages();
    }

    public void update() {

    }

    public void draw(Graphics g) {
        // Background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(Game.GAME_WIDTH, 0, Game.GAME_WIDTH + Game.INFO_WIDTH, Game.GAME_HEIGHT);

        // Enemies to kill
        drawTanksInRows(g, ENEMY_TANK_CNT_IMG,
                Game.GAME_WIDTH + 10,
                10,
                playing.getEnemyManager().getEnemiesToKillCount(), 3, 32, 0);

        // Label "IP"
        g.drawImage(FIRST_PLAYER_INFO_IMG, Game.GAME_WIDTH + 10, (int) (Game.GAME_HEIGHT / 2f), 64, 32, null);

        // Player's health ( = number of tanks)
        drawTanksInRows(g, PLAYER_TANK_CNT_IMG,
                Game.GAME_WIDTH + 10,
                (int) (Game.GAME_HEIGHT / 2f) + 32,
                playing.getPlayer().getCurrentHealth(), 3, 32, 3);

        // Points
        DrawNumber(g, playing.getPlayer().getPoints(), Game.GAME_WIDTH + 10, (int) (Game.GAME_HEIGHT / 2f + 150));

        // Level flag
        g.drawImage(STAGE_NUM_IMG, Game.GAME_WIDTH + 10, Game.GAME_HEIGHT - 100, 64, 64, null);

        // Level number
        DrawNumber(g, playing.getLevelManager().getCurrentLevelIndex() + 1, Game.GAME_WIDTH + 10 + 70, Game.GAME_HEIGHT - 66);


        g.setColor(Color.BLACK);
        g.drawString("Press M for mute,", Game.GAME_WIDTH + 10, Game.GAME_HEIGHT - 15);
        g.drawString("N - mute movement", Game.GAME_WIDTH + 10, Game.GAME_HEIGHT - 5);
    }


    /**
     * The  method draw images in rows
     * @param g Graphics
     * @param img Image to draw
     * @param xOffset top left X to start
     * @param yOffset top left Y to start
     * @param totalCnt total counts of copies to draw
     * @param maxRowCnt max number of images in one row
     * @param tankSize image size
     * @param distanceBetweenRows distance between two rows
     */
    private void drawTanksInRows(Graphics g, BufferedImage img, int xOffset, int yOffset, int totalCnt, int maxRowCnt, int tankSize, int distanceBetweenRows) {

        int j = 0;
        for (int i = 1; i <= totalCnt; i++) {
            g.drawImage(img,
                    xOffset + j * tankSize,
                    yOffset, tankSize, tankSize, null);

            j++;

            if (i % maxRowCnt == 0) {
                yOffset += tankSize + distanceBetweenRows;
                j = 0;
            }
        }

    }


}
