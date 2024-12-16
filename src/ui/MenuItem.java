package ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.LoadSaveImages.MENU_ITEM_IMAGES;

public class MenuItem {

    private MenuItemType type;
    private Rectangle2D.Float hitbox;
    private int x, y, width, height;
    private boolean active;

    public MenuItem(MenuItemType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = type.getWidth();
        this.height = type.getHeight();
        this.active = true;
    }

    public void draw(Graphics g) {
        g.drawImage(MENU_ITEM_IMAGES.get(type), x, y, width, height, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MenuItemType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
