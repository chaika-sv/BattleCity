package editor;

import gamestates.Editing;
import levels.Level;
import levels.LevelBlock;
import levels.LevelBlockType;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;
import static utils.Constants.DirConstants.*;
import static utils.LoadSaveImages.BLOCK_IMAGES;

public class Brush {


    private Editing editing;
    private Level level;
    private LevelBlockType levelBlockType;
    private boolean left, right, up, down;
    private boolean moving;

    private float x, y;
    private int width, height;
    private float brushStepX;
    private float brushStepY;


    public Brush(Editing editing, Level level, float x, float y, int width, int height) {
        this.editing = editing;
        this.level = level;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.brushStepX = 64 * Game.SCALE;
        this.brushStepY = 64 * Game.SCALE;
    }

    public void addBlock() {
        Rectangle2D.Float brushRect = new Rectangle2D.Float(x, y, width, height);

        if (levelBlockType != null)
            if (levelBlockType == LevelBlockType.ERASE)
                erase();
            else {
                for(LevelBlock b : level.getLevelBlocks())
                    if (b.isActive())
                        if (brushRect.intersects(b.getHitbox()))
                            return;

                level.addLevelBlock(levelBlockType, (int) x, (int) y);
            }
    }

    private void erase() {
        Rectangle2D.Float brushRect = new Rectangle2D.Float(x, y, width, height);

        for(LevelBlock b : level.getLevelBlocks())
            if (b.isActive())
                if (brushRect.intersects(b.getHitbox()))
                    b.setActive(false);

    }

    public void move(int dir) {
        switch (dir) {
            case UP -> {
                if (y - brushStepY >= 0)
                    y -= brushStepY;
            }
            case DOWN -> {
                if (y + height + brushStepY <= GAME_HEIGHT)
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
        if (levelBlockType != null) {
            g.drawImage(BLOCK_IMAGES.get(levelBlockType), (int)x, (int)y, width, height, null);
        }

        g.setColor(Color.LIGHT_GRAY);
        g.drawRect((int) x, (int) y, width, height);
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
