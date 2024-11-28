package ui;

import entities.TankType;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.DirConstants.RIGHT;
import static utils.Constants.TankColorConstants.PLAYER_YELLOW;
import static utils.Constants.TankTypeConstants.BASE;
import static utils.LoadSave.TANK_IMAGES;

public class MenuSelector {

    private int x, y, width, height;
    private BufferedImage img;
    private MenuItem selectedItem;

    public MenuSelector(MenuItem selectedItem) {
        this.x = selectedItem.getX() - 50;
        this.y = selectedItem.getY();
        this.width = (int) (TankType.T_BASE.getHitboxSize() * 0.7f);
        this.height = (int) (TankType.T_BASE.getHitboxSize() * 0.7f);
        this.selectedItem = selectedItem;
        img = TANK_IMAGES[PLAYER_YELLOW][BASE][RIGHT][0];
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public MenuItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(MenuItem selectedItem) {
        this.selectedItem = selectedItem;
        this.x = selectedItem.getX() - 50;
        this.y = selectedItem.getY();
    }
}
