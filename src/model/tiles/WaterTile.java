
package model.tiles;

import model.CityResources;

/**
 * State-less tile that represents Water tiles.
 */
public final class WaterTile extends Tile {

    // Constant
    /**
     * Default instance.
     */
    private final static WaterTile INSTANCE = new WaterTile();

    // Factory
    /**
     * @return Default Water tile.
     */
    public static WaterTile getDefault() {
        // Provide always the same instance since Water is not changing.
        return WaterTile.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link WaterTile#getDefault()} instead.
     */
    private WaterTile() {
    }

    // Access
    @Override
    public int hashCode() {
        return 0;
    }

    // Status
	/**
     * equals return true if the selected object is the water one, false otherwise
     * 
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof WaterTile;
    }

    // Change
	/**
     * update doesn't do anything, since the watertile do not change with time
     * 
     */
    @Override
    public void update(CityResources res) {
        // Do nothings.
    }

}
