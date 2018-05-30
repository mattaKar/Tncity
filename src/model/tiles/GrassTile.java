/**
 * TNCity
 * Copyright (c) 2017
 *  Jean-Philippe Eisenbarth,
 *  Victorien Elvinger
 *  Martine Gautier,
 *  Quentin Laporte-Chabasse
 *
 *  This file is part of TNCity.
 *
 *  TNCity is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TNCity is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with TNCity.  If not, see <http://www.gnu.org/licenses/>.
 */

package model.tiles;

import model.CityResources;

/**
 * State-less tile that represents grass tiles.
 */
public final class GrassTile extends Tile {

    // Constant
    /**
     * Default instance.
     */
    private final static GrassTile INSTANCE = new GrassTile();

    // Factory
    /**
     * @return Default grass tile.
     */
    public static GrassTile getDefault() {
        // Provide always the same instance since Grass is not changing.
        return GrassTile.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link GrassTile#getDefault()} instead.
     */
    private GrassTile() {
    }

    // Access
    @Override
    public int hashCode() {
        return 0;
    }

    // Status
    /**
     * equals return true if the selected object is an instance of grassTile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof GrassTile;
    }

    // Change
    /**
     * updates does nothing because grass tiles do not change with time.
     */
    @Override
    public void update(CityResources res) {
        // Do nothings.
    }

}
