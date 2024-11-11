package gamestates;

import editor.Brush;
import editor.Editor;
import editor.EditorSpace;
import levels.LevelBlock;
import levels.LevelBlockType;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static main.Game.*;
import static utils.Constants.DirConstants.*;

public class Editing  extends State implements Statemethods {

    private Brush brush;
    private EditorSpace editorSpace;

    public Editing(Editor editor) {
        super(editor);

        brush = new Brush(this, 0, 0, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE));
        editorSpace = new EditorSpace(this);

    }

    @Override
    public void update() {
        //brush.update();
    }

    @Override
    public void draw(Graphics g) {
        editorSpace.draw(g);
        brush.draw(g);

    }

    private void drawGameField(Graphics g) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(LevelBlock b : editorSpace.getSampleLevelBlocks()) {
            if (isIn(e, b))
                applyToBrush(b);
        }
    }

    private void applyToBrush(LevelBlock b) {
        brush.setWidth(b.getWidth());
        brush.setHeight(b.getHeight());
        brush.setBrushStepX(b.getWidth());
        brush.setBrushStepY(b.getHeight());
        brush.setLevelBlockType(b.getDrawType());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> brush.move(LEFT);
            case KeyEvent.VK_D -> brush.move(RIGHT);
            case KeyEvent.VK_W -> brush.move(UP);
            case KeyEvent.VK_S -> brush.move(DOWN);
            //case KeyEvent.VK_SPACE -> brush.setAttacking(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> brush.setLeft(false);
            case KeyEvent.VK_D -> brush.setRight(false);
            case KeyEvent.VK_W -> brush.setUp(false);
            case KeyEvent.VK_S -> brush.setDown(false);
            //case KeyEvent.VK_SPACE -> player.setAttacking(false);
        }
    }

    private boolean isIn(MouseEvent e, LevelBlock b) {
        return b.getHitbox().getBounds().contains(e.getX(), e.getY());
    }
}
