package ui;

import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

import static utils.HelpMethods.DrawNumber;
import static utils.LoadSaveImages.STAGE_IMG;

public class StartLevelOverlay {

    private Playing playing;

    public StartLevelOverlay(Playing playing) {
        this.playing = playing;
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(35, 35, 35, 240));        // Transparent black background
        g.fillRect(0, 0, Game.FULL_GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(STAGE_IMG,
                Game.FULL_GAME_WIDTH / 2 - 96,
                Game.GAME_HEIGHT / 2,
                160, 32, null);

        DrawNumber(g, playing.getLevelManager().getCurrentLevelIndex() + 1,
                Game.FULL_GAME_WIDTH / 2 - 96 + 170,
                Game.GAME_HEIGHT / 2);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE ->
                    playing.returnToGameFromStartLevelOverlay();
        }
    }
}
