
package model.tools;

import model.CityResources;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.CommercialTile;
import model.tiles.Tile;

/**
 *
 * @author Victorien Elvinger
 *
 */
public final class CommercialZoneDelimiterTool extends Tool {

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
     * equals return true if the selected tool is the commercial one, false otherwise
     * 
     */
	@Override
	public boolean equals (Object o) {
		return this == o || o instanceof CommercialZoneDelimiterTool;
		
	}

	/**
     * isAfordable returns true if the user can apply the Commercial Tool, false
     * otherwise.
     */
	@Override
	public boolean isAfordable (Tile aTarget, CityResources r) {
		if (aTarget instanceof ForestTile){
			return CommercialZoneDelimiterTool.CURRENCY_COST+20 <= r.getCurrency();
		}else{
		return CommercialZoneDelimiterTool.CURRENCY_COST <= r.getCurrency();
		}
	}

// Access
	/**
     * getCost return the price of building a commercial zone depending on the tile it would be built on
     * 
     */
	@Override
	public int getCost (Tile aTarget) {
		if (aTarget instanceof ForestTile){
			return CommercialZoneDelimiterTool.CURRENCY_COST+20;
		}else{
		return CommercialZoneDelimiterTool.CURRENCY_COST;
		}
	}

	@Override
	public int hashCode () {
		return getClass().hashCode();
	}

	/**
     * innerEffect apply the Commercial tool to the given tile and update the
     * given CityResources.
     */
	@Override
	protected Tile innerEffect (Tile aTarget, CityResources r) {
		assert canEffect(aTarget);
		assert isAfordable(aTarget, r);

		if (aTarget instanceof ForestTile){
			r.spend(CommercialZoneDelimiterTool.CURRENCY_COST+20);
		}else{
		r.spend(CommercialZoneDelimiterTool.CURRENCY_COST);
		}

		return new CommercialTile();
	}


// Debugging
	@Override
	public String toString () {
		return getClass().getSimpleName();
	}

}
