package model.tools;


import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.PolicestationTile;
import model.tiles.Tile;

public final class PolicestationConstructionTool extends Tool {

    // Constant
    private final static int CURRENCY_COST = 500;
    
    
    // Status
    /**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
    @Override
    public boolean canEffect(Tile aTarget) {
    	return aTarget instanceof GrassTile || aTarget instanceof ForestTile ;
    }
    
	/**
     * equals return true if the selected tool is the police station one, false otherwise
     * 
     */
    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof PolicestationConstructionTool;

    }

    /**
     * isAfordable returns true if the user can apply the Hospital Tool, false
     * otherwise.
     */
    @Override
    public boolean isAfordable(Tile aTarget, CityResources r) {
    	if ( aTarget instanceof ForestTile ){
            return PolicestationConstructionTool.CURRENCY_COST + 20<= r.getCurrency();
        	} else {
        		return PolicestationConstructionTool.CURRENCY_COST <= r.getCurrency();
        	}
    }

    // Access
	/**
     * getCost return the price of building a policestation depending on the tile it would be built on.
     * 
     */
    @Override
    public int getCost(Tile aTarget) {
    	if ( aTarget instanceof ForestTile ){
            return PolicestationConstructionTool.CURRENCY_COST + 20;
        	}
    	else{
    		return PolicestationConstructionTool.CURRENCY_COST;
    	}
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * innerEffect apply the Hospital tool to the given tile and update the
     * given CityResources.
     */
    @Override
    protected Tile innerEffect(Tile aTarget, CityResources r) {
        assert this.canEffect(aTarget);
        assert this.isAfordable(aTarget, r);

        if ( aTarget instanceof ForestTile ){
        	r.spend(PolicestationConstructionTool.CURRENCY_COST+20);
        	}else{
        		r.spend(PolicestationConstructionTool.CURRENCY_COST);
        	}

        return new PolicestationTile();
    }

    // Debugging
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
