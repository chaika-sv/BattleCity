package editor;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import main.Game;

import javax.swing.*;
import java.awt.*;

import static main.Game.*;

public class EditorPanel extends JPanel {

    private MouseInputs mouseInputs;
    private Editor editor;

    public EditorPanel(Editor editor) {
        this.editor = editor;

        setPanelSize();

        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH + 2 * TILES_SIZE, GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        System.out.println("Size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        editor.draw(g);
    }

    public Editor getEditor() {
        return editor;
    }
}
