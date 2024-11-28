package ui;

import entities.TankType;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utils.Constants.DirConstants.DOWN;
import static utils.Constants.DirConstants.UP;
import static utils.LoadSave.GAME_OVER_IMG;

public class GameOverOverlay {

    private Playing playing;
    private ArrayList<MenuItem> menuItems;
    private MenuSelector menuSelector;
    private int curSelectorIndex;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        curSelectorIndex = 0;
        initMenuItems();
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(MenuItemType.MI_RESTART, Game.GAME_WIDTH / 2 - MenuItemType.MI_RESTART.getWidth() / 2, Game.GAME_HEIGHT / 2));
        menuItems.add(new MenuItem(MenuItemType.MI_MAIN_MENU, Game.GAME_WIDTH / 2 - MenuItemType.MI_MAIN_MENU.getWidth() / 2, Game.GAME_HEIGHT / 2 + 50));
        menuItems.add(new MenuItem(MenuItemType.MI_EXIT_GAME, Game.GAME_WIDTH / 2 - MenuItemType.MI_EXIT_GAME.getWidth() / 2, Game.GAME_HEIGHT / 2 + 100));

        menuSelector = new MenuSelector(menuItems.get(curSelectorIndex));
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 240));        // Transparent black
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(GAME_OVER_IMG, Game.GAME_WIDTH / 2 - 64, Game.GAME_HEIGHT / 2 - 100, 128, 64, null);

        for (MenuItem menuItem : menuItems)
            menuItem.draw(g);

        menuSelector.draw(g);

    }

    private void changeMenu(int dir) {
        if (dir == DOWN)
            if (curSelectorIndex + 1 < menuItems.size())
                curSelectorIndex++;

       if (dir == UP)
            if (curSelectorIndex - 1 >= 0)
                curSelectorIndex--;

        menuSelector.setSelectedItem(menuItems.get(curSelectorIndex));
    }

    private void selectItem() {
        switch (menuItems.get(curSelectorIndex).getType()) {
            case MI_RESTART -> {
                playing.resetAll();
                playing.startCurrentLevelAgain();
                playing.setGameOver(false);
                Gamestate.state = Gamestate.PLAYING;
            }
            case MI_MAIN_MENU -> {
            }
            case MI_EXIT_GAME -> {
                System.exit(0);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> changeMenu(UP);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> changeMenu(DOWN);
            case KeyEvent.VK_SPACE -> selectItem();
        }
    }

}
