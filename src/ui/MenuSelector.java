package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.DirConstants.RIGHT;
import static utils.Constants.TankColorConstants.PLAYER_YELLOW;
import static utils.Constants.TankTypeConstants.BASE;
import static utils.LoadSave.TANK_IMAGES;

public class MenuSelector {

    private int x, y, width, height;
    private BufferedImage img;

    public MenuSelector(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        img = TANK_IMAGES[PLAYER_YELLOW][BASE][RIGHT][0];
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }



}
