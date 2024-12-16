package gamestates;

import editor.Editor;
import main.Game;

public class State {

    protected Game game;
    protected Editor editor;

    public State(Game game) {
        this.game = game;
    }

    public State(Editor editor) {
        this.editor = editor;
    }

    public Game getGame() {
        return game;
    }
}
