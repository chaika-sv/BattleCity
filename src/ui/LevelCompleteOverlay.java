package ui;

import gamestates.Playing;
import main.Game;
import utils.LoadSaveImages;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static utils.HelpMethods.DrawNumber;
import static utils.LoadSaveImages.STAGE_IMG;
import static utils.LoadSaveImages.VICTORY_IMG;

public class LevelCompleteOverlay {

    private Playing playing;
    private BufferedImage img;

    public LevelCompleteOverlay(Playing playing) {
        this.playing = playing;
        loadImage();
    }

    private void loadImage() {
        BufferedImage logoImg = LoadSaveImages.GetSpriteAtlas(LoadSaveImages.VICTORY_IMG);
        img = logoImg.getSubimage(0, 0, 500, 500);
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 240));        // Transparent black background
        g.fillRect(0, 0, Game.FULL_GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img,
                Game.GAME_WIDTH / 2 - 250,
                Game.GAME_HEIGHT / 2 - 250,
                500, 500, null);

    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE ->
                    playing.returnToGameFromLevelCompleteOverlay();
        }
    }
}
