package objects;

import static utils.Constants.TempObjectsConstants.*;

public enum TemporaryObjectType {

    TO_SMALL_EXPLOSION(SMALL_EXPLOSION, 64, 64, true, 1, 3),
    TO_BIG_EXPLOSION(BIG_EXPLOSION, 128, 128, true, 1, 2),
    TO_SPAWN(SPAWN, 64, 64, true, 5, 4),
    TO_SHIELD(SHIELD, 64, 64, true, 20, 2);

    private final int id;
    private final int width;
    private final int height;
    private final boolean repeating;
    private final int repeatingTimes;
    private final int spritesNumber;

    TemporaryObjectType(int id, int width, int height, boolean repeating, int repeatingTimes, int spritesNumber) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.repeating = repeating;
        this.repeatingTimes = repeatingTimes;
        this.spritesNumber = spritesNumber;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public int getRepeatingTimes() {
        return repeatingTimes;
    }

    public int getSpritesNumber() {
        return spritesNumber;
    }
}
