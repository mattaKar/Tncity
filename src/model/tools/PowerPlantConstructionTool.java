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

package model.tools;

import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.PowerPlantTile;
import model.tiles.Tile;

public final class PowerPlantConstructionTool extends Tool {

    // Constant
    private final static int CURRENCY_COST = 2000;

    // Status
    /**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
    @Override
    public boolean canEffect(Tile aTarget) {
    	return aTarget instanceof GrassTile || aTarget instanceof ForestTile ;
    }

	/**
     * equals return true if the selected tool is the powerplant one, false otherwise
     * 
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof PowerPlantConstructionTool;

    }

    /**
     * isAfordable returns true if the user can apply the PowerPlant Tool, false
     * otherwise.
     */
    @Override
    public boolean isAfordable(Tile aTarget, CityResources r) {
    	if ( aTarget instanceof ForestTile ){
        return PowerPlantConstructionTool.CURRENCY_COST + 20<= r.getCurrency();
    	} else {
    		return PowerPlantConstructionTool.CURRENCY_COST <= r.getCurrency();
    	}
    }

    // Access
	/**
     * getCost return the price of building the powerplant depending on the tile it would be built on
     * 
     */
    @Override
    public int getCost(Tile aTarget) {
    	if ( aTarget instanceof ForestTile ){
            return PowerPlantConstructionTool.CURRENCY_COST + 20;
        	}
    	else{
    		return PowerPlantConstructionTool.CURRENCY_COST;
    	}
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * innerEffect apply the PowerPlant tool to the given tile and update the
     * given CityResources.
     */
    @Override
    protected Tile innerEffect(Tile aTarget, CityResources r) {
        assert this.canEffect(aTarget);
        assert this.isAfordable(aTarget, r);
        if ( aTarget instanceof ForestTile ){
        	r.spend(PowerPlantConstructionTool.CURRENCY_COST+20);
        	}else{
        		r.spend(PowerPlantConstructionTool.CURRENCY_COST);
        	}
        return new PowerPlantTile();
    }

    // Debugging
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
