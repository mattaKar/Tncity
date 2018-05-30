
package model.tiles;

import model.CityResources;


/**
 * A building meant to improve the health situation of the city working on a specified budget
 */
public class HospitalTile extends BuildableTile {

    // Constant
	
    /**
     * Default value of care capacity for an hospital
     */
    public final static int DEFAULT_CARE_CAPACITY = 200;
    
    
    /**
     * Default value of the maximum needed energy
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    
    /**
     * Default value of maximum needed water
     */
    public final static int DEFAULT_MAX_NEEDED_WATER = 30;
    
    /**
     * Default value of the evolution of the energy consumption
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;
    
    /**
     * Default value of the evolution of the water consumption
     */
    public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 5;
    
    /**
     * Default value of happiness an hospital will bring to the city
     */
	private static final int DEFAULT_HAPPINESS = 40;
    
    // Implementation

    /**
     * {@link #getCareCapacity()}
     */
    
    protected final int careCapacity;
    
    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;
    
    /**
     * {@link #getMaxNeededWater()}
     */
    private final int maxNeededWater;
    
    /**
     * value of happiness given by the building
     */
    private final int happinessBring;
    
    /**
     * Evolution state
     */
    protected boolean isDestroyed;
    
    /**
     * count of hospital in the city
     */
    public static int nbrHosp=0;


    // Creation
    /**
     * @param productionCapacity
     *            - {@link #getProductionCapacity()}
     * instantiate an hospital with a given capacity as settings
     */

    public HospitalTile(int capacity) {
        super(HospitalTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, HospitalTile.DEFAULT_EVOLUTION_WATER_CONSUMPTION  );
        this.state=ConstructionState.BUILT;
        this.maxNeededEnergy = HospitalTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededWater = HospitalTile.DEFAULT_MAX_NEEDED_WATER;
        HospitalTile.nbrHosp=nbrHosp+1;
        this.state=ConstructionState.BUILT;

        this.careCapacity = HospitalTile.DEFAULT_CARE_CAPACITY ;
        this.happinessBring = HospitalTile.DEFAULT_HAPPINESS;

    }

    /**
     * Create with default settings.
     */
    public HospitalTile() {
        this(HospitalTile.DEFAULT_CARE_CAPACITY);
    }

    // Access
   

    /**
     * @return Maximum care capacity.
     */
    public int getCareCapacity() {
        return this.careCapacity;
    }
    /**
     * @return Maximum Needed Energy.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }

    /**
     * @return Maximum Needed Water.
     */
    public final int getMaxNeededWater() {
        return this.maxNeededWater;
    }
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededWater;
        result = result * 17 + this.careCapacity;
        return result;
    }

    // Status
    
    

    /**
     * returns true if the selected object is an instance of hospital tile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof HospitalTile && this.equals((HospitalTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(HospitalTile o) {
        return this == o || o.careCapacity == this.careCapacity && o.isDestroyed == this.isDestroyed;
    }
    
    /**
     * returns true if the selected tile is destroyed, false otherwise
     */
    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    // Change
    
    /**
     * removes the building from the gameboard and updates the resources consequently
     */
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
        	
        	res.decreaseCareCapacity(this.careCapacity);
        	super.disassemble(res);
        	HospitalTile.nbrHosp=nbrHosp-1;
        }
    }

    /**
     * evolve takes an under_construction hospital 
     * and builds it (not used because hospital are never under_construction)
     */
    @Override
    public void evolve(CityResources res) {
        super.evolve(res);

        if (this.state == ConstructionState.BUILT) {

            this.update(res);
        }
    }
    
    /**
     * updates calculates the new cell capacity every turn depending on the budget spent on security
     */
    @Override
    public void update(CityResources res) {

    	boolean isResActif=res.isAdjacentActiveGlobal(position);
        if(isResActif){
	    	int q=res.impot.tauxHosp;
	        res.increaseCareCapacity(Math.min(this.careCapacity*q/50,this.careCapacity*2));
	        res.increaseHappiness(this.happinessBring);
        }	        	
    }

}
