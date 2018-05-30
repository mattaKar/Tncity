package model.tools;

import model.CityResources;
import model.tiles.CityHall;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.Tile;

public class CityHallTool extends Tool {
	
	// Constant
	private final static int CURRENCY_COST = 0;
	
	
	// Status
	/**
     * isAfordable returns true if the user can apply the cityhall Tool, false
     * otherwise.
     */
		
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		return CityHallTool.CURRENCY_COST <= r.getCurrency();
	}

	// Access
	
	@Override
	public int getCost (Tile aTarget) {
		return CityHallTool.CURRENCY_COST;
	}
	
	
	public boolean canEffect (Tile aTarget) {
		return aTarget instanceof GrassTile || aTarget instanceof ForestTile ;
	}
	
	
	public boolean equals (Object o) {
		return this == o || o instanceof RoadTool;
		
	}
	
	/**
     * innerEffect apply the CityHall tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);


		return new CityHall();
	}	

}
