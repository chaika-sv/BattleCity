package gamestates;

import editor.Brush;
import editor.Editor;
import editor.EditorPanel;
import editor.EditorSpace;
import levels.Level;
import levels.LevelBlock;
import levels.LevelBlockType;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;

import static main.Game.*;
import static utils.Constants.DirConstants.*;
import static utils.Constants.LevelConstants.*;

public class Editing  extends State implements Statemethods {

    private Brush brush;
    private EditorSpace editorSpace;
    private Level level;
    private EditorPanel editorPanel;

    public Editing(Editor editor, EditorPanel editorPanel) {
        super(editor);

        this.editorPanel = editorPanel;

        level = new Level(0);
        brush = new Brush(this, level, 0, 0, (int)(TILES_DEFAULT_SIZE * Game.SCALE), (int)(TILES_DEFAULT_SIZE * Game.SCALE));
        editorSpace = new EditorSpace(this);

    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        editorSpace.draw(g);
        level.draw(g, DRAW_ALL_LEVEL);
        brush.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(LevelBlock b : editorSpace.getSampleLevelBlocks()) {
            if (isIn(e, b))
                if (b.getType() == LevelBlockType.SAVE)
                    saveLevelToFile();
                else if (b.getType() == LevelBlockType.OPEN)
                    loadLevelFromFile();
                else
                    applyToBrush(b);
        }
    }

    private void loadLevelFromFile() {

        File file;
        String fileName = "";


        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(LEVEL_DIR));
        if (fileChooser.showOpenDialog(editorPanel) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            fileName = file.getName();
        }

        if (!fileName.equals("")) {
            try {
                FileInputStream fis = new FileInputStream(LEVEL_DIR + "/" + fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Level loadedLevel = (Level) ois.readObject();
                level.copyLevel(loadedLevel);
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveLevelToFile() {

        File file;
        String fileName = "";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(LEVEL_DIR));
        if (fileChooser.showSaveDialog(editorPanel) == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            fileName = file.getName();
        }

        if (!fileName.equals("")) {
            try {
                FileOutputStream fos = new FileOutputStream(LEVEL_DIR + "/" + fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(level);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void applyToBrush(LevelBlock b) {
        brush.setWidth(b.getWidth());
        brush.setHeight(b.getHeight());
        brush.setBrushStepX(b.getWidth());
        brush.setBrushStepY(b.getHeight());
        brush.setLevelBlockType(b.getDrawType());

        stickToNet();
    }

    private void stickToNet() {
        brush.setX((int)(brush.getX() / brush.getWidth()) * brush.getWidth());
        brush.setY((int)(brush.getY() / brush.getHeight()) * brush.getHeight() );
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
            case KeyEvent.VK_SPACE -> brush.addBlock();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> brush.setLeft(false);
            case KeyEvent.VK_D -> brush.setRight(false);
            case KeyEvent.VK_W -> brush.setUp(false);
            case KeyEvent.VK_S -> brush.setDown(false);
        }
    }

    private boolean isIn(MouseEvent e, LevelBlock b) {
        return b.getHitbox().getBounds().contains(e.getX(), e.getY());
    }
}
