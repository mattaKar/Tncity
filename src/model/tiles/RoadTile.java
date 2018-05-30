package model.tiles;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;

/**
 * BuildableTile that allows building to be built
 */
public class RoadTile extends BuildableTile {
	
    /**
     * Default value of {@link RoadTile#getEvolutionEnergyConsumption()}
     */
  
	public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 0;
	
	/**
     * Default value of {@link RoadTile#getEvolutionWaterConsumption()}
     */
	public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 0;
	
    // Implementation
	/**
     * create a road tile that does not consume any energy or water overtime and instantiate the road network on this tile
     * 
     */
	public RoadTile() {    
	

    	super(RoadTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, DEFAULT_EVOLUTION_WATER_CONSUMPTION);
    	this.state=ConstructionState.BUILT;
        vectReseaux[TypeRes.Route.numRes()]=EtatRes.RESINACTIF;
        vectReseaux[TypeRes.Elec.numRes()]=EtatRes.RESINACTIF;
        vectReseaux[TypeRes.Eau.numRes()]=EtatRes.RESINACTIF;
   	


    }
	//changes
	/**
     * destroy the road it is applied upon 
     * 
     */
	@Override
	public boolean isDestroyed() {
		return this.state == ConstructionState.DESTROYED;
	}
	
	/**
     * update does not do anything since roads do not change with time
     * 
     */
	@Override
	public void update(CityResources res) {
		
	}
	

}
