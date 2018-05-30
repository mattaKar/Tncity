
package model.tiles;

import model.CityResources;


/**
 * A building meant to improve the security situation of the city working on a specified budget
 */
public class PolicestationTile extends BuildableTile {

    // Constant
	
    /**
     * Default value of POlice Station capacity
     */
    public final static int DEFAULT_POLICESTATION_CAPACITY = 100;
    
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
    
    // Implementation

    /**
     * {@link #getPolicestationCapacity()}
     */
    
    protected final int PolicestationCapacity;
    
    /**
     * {@link #getmaxNeededEnergy()}
     */
    private final int maxNeededEnergy;
    
    /**
     * {@link #getmaxNeededWater()}
     */
    private final int maxNeededWater;
    
    /**
     * Evolution state
     */
    protected boolean isDestroyed;
    
    /**
     * police station count
     */
    public static int nbrPol=0;


    // Creation
    /**
     * @param PolicestationCapacity
     *            - {@link #getPolicestationCapacity()}
     * creates an instance of PolicestationTile with set settings.
     */

    public PolicestationTile(int capacity) {
        super(PolicestationTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, PolicestationTile.DEFAULT_EVOLUTION_WATER_CONSUMPTION  );
        this.state=ConstructionState.BUILT;
        this.maxNeededEnergy = PolicestationTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededWater = PolicestationTile.DEFAULT_MAX_NEEDED_WATER;
        PolicestationTile.nbrPol=nbrPol+1;

        this.PolicestationCapacity = PolicestationTile.DEFAULT_POLICESTATION_CAPACITY ;

    }

    /**
     * Create with default settings.
     */
    public PolicestationTile() {
        this(PolicestationTile.DEFAULT_POLICESTATION_CAPACITY);
    }

    // Access
   

    /**
     * @return Maximum capacity.
     */
    public int getPolicestationCapacity() {
        return this.PolicestationCapacity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededWater;
        result = result * 17 + this.PolicestationCapacity;
        return result;
    }

    // Status
    
    /**
     * @return Maximum needed energy
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }
    
    /**
     * @return Maximum needed water
     */
    public final int getMaxNeededWater() {
        return this.maxNeededWater;
    }

    /**
     * returns true if the selected object is an instance of police station tile
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof PolicestationTile && this.equals((PolicestationTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(PolicestationTile o) {
        return this == o || o.PolicestationCapacity == this.PolicestationCapacity && o.isDestroyed == this.isDestroyed;
    }

    /**
     * returns true if the selected police station tile is destroyed, false otherwise
     */
    @Override
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    // Change
    /**
     * removes the selected police station from the gameboard and updates the city resources given the change
     */
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
        	
        	res.decreasePolicestationCapacity(this.PolicestationCapacity);
        	super.disassemble(res);
        	PolicestationTile.nbrPol=nbrPol-1;
        }
    }

    /**
     * evolve bring an under_construction building to a constructed one,
     *  it changes the resources consequently.(never used because police stations are always built).
     */
    @Override
    public void evolve(CityResources res) {
        super.evolve(res);

        if (this.state == ConstructionState.BUILT) {

            res.increasePolicestationCapacity(this.PolicestationCapacity);
            this.update(res);
        }
    }
    /**
     * updates calculates the new Policestation capacity every turn depending on the budget spent on security
     */
    @Override
    public void update(CityResources res) {
    	boolean isResActif=res.isAdjacentActiveGlobal(position);
        if(isResActif){
        	int q=res.impot.tauxSecu;
        	res.increasePolicestationCapacity(Math.min(this.PolicestationCapacity*q/50,this.PolicestationCapacity*2));
        }
    }

}
