package editor;

import gamestates.Editing;
import levels.LevelBlockType;
import main.Game;

import java.awt.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utils.Constants.DirConstants.*;
import static utils.LoadSave.BLOCK_IMAGES;

public class Brush {


    private Editing editing;
    private LevelBlockType levelBlockType;
    private boolean left, right, up, down;
    private boolean moving;

    private float x, y;
    private int width, height;
    private float brushStepX;
    private float brushStepY;


    public Brush(Editing editing, float x, float y, int width, int height) {
        this.editing = editing;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.brushStepX = 64 * Game.SCALE;
        this.brushStepY = 64 * Game.SCALE;
    }

    public void move(int dir) {
        switch (dir) {
            case UP -> {
                if (y - brushStepX >= 0)
                    y -= brushStepY;
            }
            case DOWN -> {
                if (y + height + brushStepX <= GAME_HEIGHT)
                    y += brushStepY;
            }
            case LEFT -> {
                if (x - brushStepX >= 0)
                    x -= brushStepX;
            }
            case RIGHT -> {
                if (x + width + brushStepX <= GAME_WIDTH)
                    x += brushStepX;
            }
        }
    }


    public void update() {

        moving = false;

        float xSpeed = 0;
        float ySpeed = 0;

        if (left) {
            xSpeed -= brushStepX;
            moving = true;
        } else if (right) {
            xSpeed += brushStepX;
            moving = true;
        } else if (up) {
            ySpeed -= brushStepX;
            moving = true;
        } else if (down) {
            ySpeed += brushStepX;
            moving = true;
        }

        x += xSpeed;
        y += ySpeed;

    }

    public void draw(Graphics g) {
        if (levelBlockType == null) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect((int) x, (int) y, width, height);
        } else {
            g.drawImage(BLOCK_IMAGES.get(levelBlockType), (int)x, (int)y, width, height, null);
        }
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

    public void setLevelBlockType(LevelBlockType levelBlockType) {
        this.levelBlockType = levelBlockType;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBrushStepX(float brushStepX) {
        this.brushStepX = brushStepX;
    }

    public void setBrushStepY(float brushStepY) {
        this.brushStepY = brushStepY;
    }
}
