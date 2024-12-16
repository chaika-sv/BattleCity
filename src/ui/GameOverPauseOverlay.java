package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static utils.Constants.DirConstants.DOWN;
import static utils.Constants.DirConstants.UP;
import static utils.LoadSaveImages.GAME_OVER_IMG;
import static utils.LoadSaveImages.PAUSE_IMG;

/**
 * The overlay is using for both Pause and Game over states
 * There are two difference between these states:
 *  - title
 *  - Pause has the RESUME menu item while Game Over doesn't have it
 */
public class GameOverPauseOverlay {

    private Playing playing;
    private ArrayList<MenuItem> menuItems;
    private MenuSelector menuSelector;
    private int curSelectorIndex;
    private int minSelectorIndex;

    public GameOverPauseOverlay(Playing playing) {
        this.playing = playing;
        curSelectorIndex = 0;
        initMenuItemsAndSelector();
    }

    private void initMenuItemsAndSelector() {
        menuItems = new ArrayList<>();

        // Show RESUME for pause only (we set it inactive in update() for game over)
        menuItems.add(new MenuItem(MenuItemType.MI_RESUME, Game.GAME_WIDTH / 2 - MenuItemType.MI_RESUME.getWidth() / 2, Game.GAME_HEIGHT / 2 - 50));

        menuItems.add(new MenuItem(MenuItemType.MI_RESTART, Game.GAME_WIDTH / 2 - MenuItemType.MI_RESTART.getWidth() / 2, Game.GAME_HEIGHT / 2));
        menuItems.add(new MenuItem(MenuItemType.MI_MAIN_MENU, Game.GAME_WIDTH / 2 - MenuItemType.MI_MAIN_MENU.getWidth() / 2, Game.GAME_HEIGHT / 2 + 50));
        menuItems.add(new MenuItem(MenuItemType.MI_EXIT_GAME, Game.GAME_WIDTH / 2 - MenuItemType.MI_EXIT_GAME.getWidth() / 2, Game.GAME_HEIGHT / 2 + 100));

        // The menu selector is pointer (tank) in the left of the menu item
        menuSelector = new MenuSelector(menuItems.get(curSelectorIndex));
    }

    public void setDefaultMenuItemSelected() {
        // For game over menu start with 1 (RESTART). RESUME is inactive in this case
        if (Gamestate.state == Gamestate.GAMEOVER) {
            menuItems.get(0).setActive(false);
            minSelectorIndex = 1;
        }
        // For pause menu start with 0 (RESUME)
        else if (Gamestate.state == Gamestate.PAUSE) {
            menuItems.get(0).setActive(true);
            minSelectorIndex = 0;
        }

        // We need it in case when pause screen was opened first and curSelectorIndex = 0 (game over don't have it)
        //if (curSelectorIndex < minSelectorIndex) {
            curSelectorIndex = minSelectorIndex;
            menuSelector.setSelectedItem(menuItems.get(curSelectorIndex));
        //}
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 240));        // Transparent black background
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // Title (Game Over or Pause)
        if (Gamestate.state == Gamestate.GAMEOVER)
            g.drawImage(GAME_OVER_IMG, Game.GAME_WIDTH / 2 - 64, Game.GAME_HEIGHT / 2 - 100, 128, 64, null);
        else if (Gamestate.state == Gamestate.PAUSE)
            g.drawImage(PAUSE_IMG, Game.GAME_WIDTH / 2 - 80, Game.GAME_HEIGHT / 2 - 130, 160, 32, null);

        // Menu items (resume, restart, menu, exit, ...)
        for (MenuItem menuItem : menuItems)
            if (menuItem.isActive())
                menuItem.draw(g);

        // The menu selector is pointer (tank) in the left of the menu item
        menuSelector.draw(g);

    }

    /**
     * Move menu selector
     * @param dir direction up or down
     */
    private void changeMenu(int dir) {
        if (dir == DOWN)
            if (curSelectorIndex + 1 < menuItems.size())
                curSelectorIndex++;

        if (dir == UP)
            if (curSelectorIndex - 1 >= minSelectorIndex)
                curSelectorIndex--;

        menuSelector.setSelectedItem(menuItems.get(curSelectorIndex));
    }

    /**
     * When one of the menu items was selected
     */
    private void selectItem() {
        switch (menuItems.get(curSelectorIndex).getType()) {
            case MI_RESUME -> playing.resumeToGameAfterPause();
            case MI_RESTART -> playing.restartLevel();
            case MI_MAIN_MENU -> playing.goToMainMenu();
            case MI_EXIT_GAME -> System.exit(0);
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> changeMenu(UP);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> changeMenu(DOWN);
            case KeyEvent.VK_SPACE -> selectItem();
            case KeyEvent.VK_ESCAPE -> {
                if (Gamestate.state == Gamestate.PAUSE)
                    playing.resumeToGameAfterPause();
            }
        }
    }

}
