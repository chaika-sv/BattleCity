package ui;

public enum MenuItemType {

    MI_RESTART(288, 32),
    MI_MAIN_MENU(96, 32),
    MI_EXIT_GAME(192, 32);

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
