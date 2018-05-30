package model.tools;

import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.RoadTile;
import model.tiles.Tile;
import model.tiles.GrassTile;

public class RoadTool extends Tool {
	
	// Constant
	private final static int CURRENCY_COST = 10;
	
	// Status
	/**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
	@Override
	public boolean canEffect (Tile aTarget) {
		return aTarget instanceof GrassTile || aTarget instanceof ForestTile;
	}
	
	/**
     * equals returns true if the current tool is the road one, false otherwise.
     */
	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof RoadTool;
		
	}

	// Status
	/**
     * isAfordable returns true if the user can apply the Road Tool, false
     * otherwise.
     */
		
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		if (aTarget instanceof ForestTile){
			return RoadTool.CURRENCY_COST+20 <= r.getCurrency();
		}else{
		return RoadTool.CURRENCY_COST <= r.getCurrency();
		}
	}

	// Access
	
	/**
     * getCost returns the price of building the road, depending on the tile it would be be built on.
     */
	@Override
	public int getCost (Tile aTarget) {
		if (aTarget instanceof ForestTile){
			return RoadTool.CURRENCY_COST+20;
		}else{
		return RoadTool.CURRENCY_COST;
		}
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}
	
	
	/**
     * innerEffect apply the Road tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);
		assert isAfordable(aTarget, r);

		if (aTarget instanceof ForestTile){
			r.spend(RoadTool.CURRENCY_COST+20);
		}else{
		r.spend(RoadTool.CURRENCY_COST);
		}

		
		return new RoadTile();
	}	
	
	
}
