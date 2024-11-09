package editor;

import gamestates.Editing;
import main.Game;

import java.awt.*;

import static utils.Constants.DirConstants.*;

public class Brush {

    protected Editing editing;
    protected boolean left, right, up, down;
    protected boolean moving;

    protected float x, y;
    protected int width, height;
    protected float brushStep;


    public Brush(Editing editing, float x, float y, int width, int height) {
        this.editing = editing;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.brushStep = 64 * Game.SCALE;
    }

    public void move(int dir) {
        switch (dir) {
            case UP: y -= brushStep;
            case DOWN: y += brushStep;
            case LEFT: x -= brushStep;
            case RIGHT: x += brushStep;
        }
    }


    public void update() {

        moving = false;

        float xSpeed = 0;
        float ySpeed = 0;

        if (left) {
            xSpeed -= brushStep;
            moving = true;
        } else if (right) {
            xSpeed += brushStep;
            moving = true;
        } else if (up) {
            ySpeed -= brushStep;
            moving = true;
        } else if (down) {
            ySpeed += brushStep;
            moving = true;
        }

        x += xSpeed;
        y += ySpeed;

    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect((int)x, (int)y, width, height);
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
