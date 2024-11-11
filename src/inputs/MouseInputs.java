package inputs;

import editor.EditorPanel;
import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    private EditorPanel editorPanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public MouseInputs(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state) {
            case EDITOR -> {
                editorPanel.getEditor().getEditing().mouseClicked(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (Gamestate.state) {
            case EDITOR -> {
                editorPanel.getEditor().getEditing().mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (Gamestate.state) {
            case EDITOR -> {
                editorPanel.getEditor().getEditing().mouseReleased(e);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
