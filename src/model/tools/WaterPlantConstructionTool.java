package model.tools;

import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.WaterPlantTile;
import model.tiles.Tile;

public final class WaterPlantConstructionTool extends Tool {

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
     * equals returns true if the given object is the WaterPlantConstructionTool, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof WaterPlantConstructionTool;

    }

    /**
     * isAfordable returns true if the user can apply the WaterPlant Tool, false
     * otherwise.
     */
    @Override
    public boolean isAfordable(Tile aTarget, CityResources r) {
    	if ( aTarget instanceof ForestTile ){
        return WaterPlantConstructionTool.CURRENCY_COST + 20<= r.getCurrency();
    	} else {
    		return WaterPlantConstructionTool.CURRENCY_COST <= r.getCurrency();
    	}
    }

    // Access
    /**
     * getCost returns the price of building a waterplant, depending on the tile it would be built on
     * 
     */
    @Override
    public int getCost(Tile aTarget) {
    	if ( aTarget instanceof ForestTile ){
            return WaterPlantConstructionTool.CURRENCY_COST + 20;
        	}
    	else{
    		return WaterPlantConstructionTool.CURRENCY_COST;
    	}
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * innerEffect apply the WaterPlant tool to the given tile and update the
     * given CityResources.
     */
    @Override
    protected Tile innerEffect(Tile aTarget, CityResources r) {
        assert this.canEffect(aTarget);
        assert this.isAfordable(aTarget, r);
        if ( aTarget instanceof ForestTile ){
        	r.spend(WaterPlantConstructionTool.CURRENCY_COST+20);
        	}else{
        		r.spend(WaterPlantConstructionTool.CURRENCY_COST);
        	}
        return new WaterPlantTile();
    }

    // Debugging
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}