package model.tiles;

import model.CityResources;

/**
 * State-less tile that represents Forest tiles.
 */
public final class ForestTile extends Tile {

    // Constant
    /**
     * Default instance.
     */
    private final static ForestTile INSTANCE = new ForestTile();

    // Factory
    /**
     * @return Default Forest tile.
     */
    public static ForestTile getDefault() {
        // Provide always the same instance since Forest is not changing.
        return ForestTile.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link ForestTile#getDefault()} instead.
     */
    private ForestTile() {
    }

    // Access
    @Override
    public int hashCode() {
        return 0;
    }

    // Status
    /**
     * equals return true if the selected object is an instance of forest tile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof ForestTile;
    }

    // Change
    /**
     * updates does nothing because forest tiles do not change with time.
     */
    @Override
    public void update(CityResources res) {
        // Do nothings.
    }

}
