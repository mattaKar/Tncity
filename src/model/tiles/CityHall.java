package model.tiles;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;

/**
 * A central building that gathers all the taxes information, only one can be built 
 * and all the other buildings must be connected either 
 * directly or remotely with road in order for them to be built.
 */
public class CityHall extends BuildableTile{
	 
	// Constants
	
	 /**
     * Default value of {@link CommercialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 0;

    /**

     * Default value of {@link CommercialTile#getEvolutionWaterConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 0;
	
    
    //constructor
    
    /**
     *  Creates an instance of cityhall that allows the network to function and buildings to be built
     *  aside roads
     */
	public CityHall(){
		super(CityHall.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, DEFAULT_EVOLUTION_WATER_CONSUMPTION);
		vectReseaux[TypeRes.Route.numRes()]=EtatRes.PROD;
	}
	
	/**
     *  always returns false, the cityhall is the center of the city and can't/ musn't be destroyed
     */
	
	public boolean isDestroyed(){
		return false;
	}
	
	
	/**
     *  Aside from enabling the network to work, the tile does not update
     */
	public void update(CityResources res) {
	}
	


}
