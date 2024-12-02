package gamestates;

import main.Game;
import ui.MenuItem;
import ui.MenuItemType;
import ui.MenuSelector;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.DirConstants.DOWN;
import static utils.Constants.DirConstants.UP;

public class Menu extends State implements Statemethods{

    private Playing playing;

    private BufferedImage logo;
    private BufferedImage copyright;
    private ArrayList<MenuItem> menuItems;
    private MenuSelector menuSelector;
    private int curSelectorIndex;
    private int minSelectorIndex;

    public Menu(Game game, Playing playing) {
        super(game);
        loadImages();

        this.playing = playing;

        curSelectorIndex = 0;
        initMenuItems();
    }

    private void initMenuItems() {
        menuItems = new ArrayList<>();

        menuItems.add(new ui.MenuItem(MenuItemType.MI_1_PLAYER, Game.FULL_GAME_WIDTH / 2 - 80, Game.GAME_HEIGHT / 2));
        menuItems.add(new ui.MenuItem(MenuItemType.MI_2_PLAYERS, Game.FULL_GAME_WIDTH / 2 - 80, Game.GAME_HEIGHT / 2 + 50));
        menuItems.add(new MenuItem(MenuItemType.MI_EDITOR, Game.FULL_GAME_WIDTH / 2 - 80, Game.GAME_HEIGHT / 2 + 100));
        menuItems.add(new MenuItem(MenuItemType.MI_EXIT_GAME, Game.FULL_GAME_WIDTH / 2 - 80, Game.GAME_HEIGHT / 2 + 150));

        // The menu selector is pointer (tank) in the left of the menu item
        menuSelector = new MenuSelector(menuItems.get(curSelectorIndex));
    }


    private void loadImages() {
        BufferedImage logoImg = LoadSave.GetSpriteAtlas(LoadSave.LOGO_IMG);
        logo = logoImg.getSubimage(0, 0, 382, 140);

        BufferedImage copyrightImg = LoadSave.GetSpriteAtlas(LoadSave.COPYRIGHT_IMG);
        copyright = copyrightImg.getSubimage(0, 0, 403, 26);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.FULL_GAME_WIDTH, Game.GAME_HEIGHT);

        // Logo
        g.drawImage(logo, Game.FULL_GAME_WIDTH / 2 - 191, 200, 382, 140, null);

        // Copyright
        g.drawImage(copyright, Game.FULL_GAME_WIDTH / 2 - 200, Game.GAME_HEIGHT - 100, 403, 26, null);

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
            case MI_1_PLAYER -> playing.startGameWithStartLevelOverlay();
            case MI_2_PLAYERS -> {}
            case MI_EDITOR -> Gamestate.state = Gamestate.EDITOR;
            case MI_EXIT_GAME -> System.exit(0);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> changeMenu(UP);
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> changeMenu(DOWN);
            case KeyEvent.VK_SPACE -> selectItem();
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
    public void keyReleased(KeyEvent e) {

    }
}
