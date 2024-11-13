package editor;

import gamestates.Editing;
import gamestates.Gamestate;

import java.awt.*;

public class Editor implements Runnable{

    private EditorWindow editorWindow;
    private EditorPanel editorPanel;
    private Thread editorThread;

    private Editing editing;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;        // updates per second

    public Editor() {

        editorPanel = new EditorPanel(this);
        editing = new Editing(this, editorPanel);

        editorWindow = new EditorWindow(editorPanel);
        editorPanel.setFocusable(true);
        editorPanel.requestFocus();           // to listen inputs


        Gamestate.state = Gamestate.EDITOR;

        startEditorLoop();

    }

    private void startEditorLoop() {
        editorThread = new Thread(this);
        editorThread.start();
    }

    @Override
    public void run() {

        // Game loop is here

        double timePerFrame = 1000000000.0 / FPS_SET;       // nanosecond / fps
        double timePerUpdate = 1000000000.0 / UPS_SET;       // nanosecond / ups
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;      // delta update
        double deltaF = 0;      // delta frame

        while (true) {
            long currentTime = System.nanoTime();

            // deltaU will be 1.0 or more WHEN the duration since last update is equal OR more than timePerUpdate
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                // it's time to update
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                // It's time for the next frame. Let's paint it
                editorPanel.repaint();
                frames++;
                deltaF--;
            }

            // At the same time we are calculating actual FPS (how many frames we paint per second)
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }


    public void update() {
        editing.update();
    }

    public void draw(Graphics g) {
        editing.draw(g);
    }

    public void windowFocusLost() {
        // Todo: reset something
    }

    public Editing getEditing() {
        return editing;
    }
}
