package ui;

public enum MenuItemType {

    MI_RESTART(288, 32),
    MI_MAIN_MENU(96, 32),
    MI_EXIT_GAME(192, 32),
    MI_RESUME(128, 32),
    MI_1_PLAYER(192, 32),
    MI_2_PLAYERS(192, 32),
    MI_EDITOR(128, 32);

    private final int width;
    private final int height;

    MenuItemType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
