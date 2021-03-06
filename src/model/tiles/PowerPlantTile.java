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

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;

/**
 * Produces energy to be consumed by the other buildings 
 */
public class PowerPlantTile extends Tile implements Destroyable {

    // Constant
    /**
     * Extra energy produces for each new update. In the limit of the capacity
     * {@link #getProductionCapacity()}.
     */
    public final static int EXTRA_ENERGY_PRODUCTION = 30;

    /**
     * Default value of {@link PowerPlantTile2#getProductionCapacity()}
     */
    public final static int DEFAULT_PRODUCTION_CAPACITY = 210;

    // Implementation
    /**
     * {@link #getProduction()}
     */
    protected int production;

    /**
     * {@link #getProductionCapacity()}
     */
    protected final int productionCapacity;

    /**
     * Evolution state
     */
    protected boolean isDestroyed;

    // Creation
    /**
     * @param productionCapacity
     *            - {@link #getProductionCapacity()}
     */
    public PowerPlantTile(int productionCapacity) {
        super();
        this.productionCapacity = productionCapacity;
        this.production = 0;
        this.isDestroyed = false;
        vectReseaux[TypeRes.Elec.numRes()]=EtatRes.PROD;
    }

    /**
     * Create with default settings.
     */
    public PowerPlantTile() {
        this(PowerPlantTile.DEFAULT_PRODUCTION_CAPACITY);
    }

    // Access
    /**
     * @return Current production.
     */
    public int getProduction() {
        return this.production;
    }

    /**
     * @return Maximum production.
     */
    public int getProductionCapacity() {
        return this.productionCapacity;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.production;
        result = result * 17 + this.productionCapacity;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        return result;
    }

    // Status
    /**
     * returns true if the selected tile is a power plant, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof PowerPlantTile && this.equals((PowerPlantTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(PowerPlantTile o) {
        return this == o || o.production == this.production && o.productionCapacity == this.productionCapacity && o.isDestroyed == this.isDestroyed;
    }

    /**
     * returns true if the selected building is destroyed, false otherwise.
     */
    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    // Change
    /**
     * remove the selected power plant from the map and changes resources upon the change.
     */
    @Override
    public void disassemble(CityResources res) {
        if (!this.isDestroyed) {
            res.decreaseEnergyProduction(this.productionCapacity);
            this.isDestroyed = true;
        }
    }

    /**
     * while the building is standing up, update increases 
     * it's production each turn before reaching the maximum production
     * 
     */
    @Override
    public void update(CityResources res) {
        if (!this.isDestroyed) {
            // Double production
            final int extraProduction = Math.min(PowerPlantTile.EXTRA_ENERGY_PRODUCTION, this.productionCapacity - this.production);

            this.production = this.production + extraProduction;
            res.increaseEnergyProduction(extraProduction);
        }
    }

}
