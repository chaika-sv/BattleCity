package gamestates;

public enum Gamestate {

    PLAYING, MENU, OPTIONS, GAME_OVER, PAUSE, START_LEVEL, LEVEL_COMPLETE, QUIT, EDITOR;

    public static Gamestate state = MENU;

}
