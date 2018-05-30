package model.tiles;

import model.CityResources;

/**
 * State-less tile that represents Shore tiles.
 */
public final class ShoreTile extends Tile {

    // Constant
    /**
     * Default instance.
     */
    private final static ShoreTile INSTANCE = new ShoreTile();

    // Factory
    /**
     * @return Default Shore tile.
     */
    public static ShoreTile getDefault() {
        // Provide always the same instance since Shore is not changing.
        return ShoreTile.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link ShoreTile#getDefault()} instead.
     */
    private ShoreTile() {
    }

    // Access
    @Override
    public int hashCode() {
        return 0;
    }

    // Status
    /**
     * equals returns true if the selected tile is a shore one, false otherwise
     * 
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof ShoreTile;
    }

    // Change
    /**
     * update does not do anything since shore tiles do not change upon time
     * 
     */
    @Override
    public void update(CityResources res) {
        // Do nothings.
    }

}
