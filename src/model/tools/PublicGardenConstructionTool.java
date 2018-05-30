package model.tools;

import model.CityResources;
import model.tiles.GrassTile;
import model.tiles.PublicGardenTile;
import model.tiles.Tile;

public class PublicGardenConstructionTool extends Tool {

	// Constant
    private final static int CURRENCY_COST = 200;
	


	@Override
	public boolean canEffect(Tile aTarget) {
		return aTarget instanceof GrassTile;
	}

	@Override
	public boolean isAfordable(Tile aTarget, CityResources r) {
		// TODO Auto-generated method stub
		return PublicGardenConstructionTool.CURRENCY_COST <= r.getCurrency();
	}

	@Override
	public int getCost(Tile aTarget) {
		// TODO Auto-generated method stub
		return PublicGardenConstructionTool.CURRENCY_COST;
	}

	@Override
	protected Tile innerEffect(Tile aTarget, CityResources r) {
		assert this.canEffect(aTarget);
        assert this.isAfordable(aTarget, r);
        
        r.spend(PublicGardenConstructionTool.CURRENCY_COST);
		return new PublicGardenTile();
	}

}
