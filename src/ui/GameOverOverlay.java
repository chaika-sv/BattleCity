package ui;

import entities.TankType;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utils.LoadSave.GAME_OVER_IMG;

public class GameOverOverlay {

    private Playing playing;
    private ArrayList<MenuItem> menuItems;
    private MenuSelector menuSelector;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        initMenuItems();
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(MenuItemType.MI_RESTART, Game.GAME_WIDTH / 2 - MenuItemType.MI_RESTART.getWidth() / 2, Game.GAME_HEIGHT / 2));
        menuItems.add(new MenuItem(MenuItemType.MI_MAIN_MENU, Game.GAME_WIDTH / 2 - MenuItemType.MI_MAIN_MENU.getWidth() / 2, Game.GAME_HEIGHT / 2 + 50));
        menuItems.add(new MenuItem(MenuItemType.MI_EXIT_GAME, Game.GAME_WIDTH / 2 - MenuItemType.MI_EXIT_GAME.getWidth() / 2, Game.GAME_HEIGHT / 2 + 100));

        menuSelector = new MenuSelector(
                Game.GAME_WIDTH / 2 - MenuItemType.MI_RESTART.getWidth() / 2 - 50,
                Game.GAME_HEIGHT / 2,
                (int) (TankType.T_BASE.getHitboxSize() * 0.7f),
                (int) (TankType.T_BASE.getHitboxSize() * 0.7f)
        );

    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));        // Transparent black
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(GAME_OVER_IMG, Game.GAME_WIDTH / 2 - 64, Game.GAME_HEIGHT / 2 - 100, 128, 64, null);

        for (MenuItem menuItem : menuItems)
            menuItem.draw(g);

        menuSelector.draw(g);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            playing.startCurrentLevelAgain();
            playing.setGameOver(false);

            //Gamestate.state = Gamestate.MENU;
        }
    }
}
