package inputs;

import editor.EditorPanel;
import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    private EditorPanel editorPanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public KeyboardInputs(EditorPanel editorPanel) {
        this.editorPanel = editorPanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyPressed(e);
            }
            case GAME_OVER, PAUSE -> {
                gamePanel.getGame().getPlaying().getGameOverPauseOverlay().keyPressed(e);
            }
            case START_LEVEL -> {
                gamePanel.getGame().getPlaying().getStartLevelOverlay().keyPressed(e);
            }
            case LEVEL_COMPLETE -> {
                gamePanel.getGame().getPlaying().getLevelCompleteOverlay().keyPressed(e);
            }
            case MENU -> {
                gamePanel.getGame().getMenu().keyPressed(e);
            }
            case OPTIONS -> {
                //gamePanel.getGame().getGameOptions().keyPressed(e);
            }
            case EDITOR ->  {
                editorPanel.getEditor().getEditing().keyPressed(e);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case PLAYING -> {
                gamePanel.getGame().getPlaying().keyReleased(e);
            }
            case GAME_OVER, PAUSE -> {
                //gamePanel.getGame().getPlaying().getGameOverOverlay().keyReleased(e);
            }
            case MENU -> {
                //gamePanel.getGame().getMenu().keyReleased(e);
            }
            case EDITOR ->  {
                editorPanel.getEditor().getEditing().keyReleased(e);
            }

        }
    }
}
