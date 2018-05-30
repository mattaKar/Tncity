package model.tools;

import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.IndustrialTile;
import model.tiles.Tile;

/**
 *
 * @author Victorien Elvinger
 *
 */
public final class IndustrialZoneDelimiterTool extends Tool {

// Constant
	private final static int CURRENCY_COST = 10;

// Status
	// Status
	/**
     * canEffect returns true if the given Tile is buildable, false otherwise.
     */
	@Override
	public boolean canEffect (Tile aTarget) {
		return aTarget instanceof GrassTile || aTarget instanceof ForestTile ;
	}

	/**
     * equals return true if the selected tool is the industrial one, false otherwise
     * 
     */
	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof IndustrialZoneDelimiterTool;

	}

	/**
     * isAfordable returns true if the user can apply the Industrial Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		if (aTarget instanceof ForestTile){
			return IndustrialZoneDelimiterTool.CURRENCY_COST+20 <= r.getCurrency();
		}else{
		return IndustrialZoneDelimiterTool.CURRENCY_COST <= r.getCurrency();
		}
	}

// Access
	/**
     * getCost return the price of building an industrial zone depending on the tile it would be built on
     * 
     */
	@Override
	public int getCost (Tile aTarget) {
		if (aTarget instanceof ForestTile){
			return IndustrialZoneDelimiterTool.CURRENCY_COST+20;
		}else{
		return IndustrialZoneDelimiterTool.CURRENCY_COST;
		}
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}

	/**
     * innerEffect apply the Industrial tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);
		assert isAfordable(aTarget, r);

		if (aTarget instanceof ForestTile){
			r.spend(IndustrialZoneDelimiterTool.CURRENCY_COST+20);
		}else{
		r.spend(IndustrialZoneDelimiterTool.CURRENCY_COST);
		}

		return new IndustrialTile();
	}


// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}

}
