package levels;

public enum LevelBlockType {

    BRICK(1, 64, 64),
    BRICK_BIG(10, 64, 64),
    BRICK_HALF(11, 64, 32),
    BRICK_SMALL(12, 32, 32),
    BRICK_HALF_SMALL(13, 32, 16),
    BRICK_LITTLE1(14, 16, 16),
    BRICK_LITTLE2(14, 16, 16),
    METAL(2, 64, 64),
    METAL_BIG(20, 64, 64),
    METAL_HALF(21, 64, 32),
    METAL_SMALL(22, 32, 32),
    GRASS(3, 64, 64),
    GRASS_BIG(30, 64, 64),
    GRASS_SMALL(31, 32, 32),
    RIVER(4, 64, 64),
    RIVER1_BIG(40, 64, 64),
    RIVER1_SMALL(41, 32, 32),
    RIVER2_BIG(42, 64, 64),
    RIVER2_SMALL(43, 32, 32),
    RIVER3_BIG(44, 64, 64),
    RIVER3_SMALL(45, 32, 32),
    ICE(5, 64, 64),
    ICE_BIG(50, 64, 64),
    ICE_SMALL(51, 32, 32);

    private final int id;
    private final int width;
    private final int height;

    LevelBlockType(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
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
}
