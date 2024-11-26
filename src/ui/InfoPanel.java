package ui;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.TILES_SIZE;
import static utils.LoadSave.*;

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
        drawTanksInRows(g, enemyTankInfoImg,
                Game.GAME_WIDTH + 10,
                10,
                playing.getEnemyManager().getEnemiesToKillCount(), 3, 32, 0);

        // Label "IP"
        g.drawImage(firstPlayerInfoImg, Game.GAME_WIDTH + 10, (int) (Game.GAME_HEIGHT / 2f), 64, 32, null);

        // Player's health ( = number of tanks)
        drawTanksInRows(g, playerTankInfoImg,
                Game.GAME_WIDTH + 10,
                (int) (Game.GAME_HEIGHT / 2f) + 32,
                playing.getPlayer().getCurrentHealth(), 3, 32, 3);

        // Level flag
        g.drawImage(flagInfoImg, Game.GAME_WIDTH + 10, Game.GAME_HEIGHT - 80, 64, 64, null);

        // Level number
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.BOLD, 64));
        g.drawString(String.valueOf(playing.getLevelManager().getCurrentLevelIndex() + 1), Game.GAME_WIDTH + 10 + 70, Game.GAME_HEIGHT - 16);

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
