
package model.tiles;

import model.CityResources;

/**
 * Enable to welcome new workers and consume energy units according to the
 * number of workers. Produces products according to the number of workers 
 * and the general health of the population.
 */
public class IndustrialTile extends BuildableTile {

    // Constants
    /**
     * Default value of {@link IndustrialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    
    /**
     * Default value of {@link IndustrialTile#getEvolutionWaterConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 5;

    /**
     * Default value of {@link IndustrialTile#maxJoiningInhabitants}
     */

    private final static int DEFAULT_MAX_JOINING_WORKERS = 20;


    /**
     * Default value of {@link IndustrialTile#maxLeavingInhabitants}
     */
    private final static int DEFAULT_MAX_LEAVING_WORKERS = 10;

    /**
     * Default value of {@link IndustrialTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    
    /**
     * Default value of {@link IndustrialTile#getMaxNeededWater()}
     */
    public final static int DEFAULT_MAX_NEEDED_WATER = 30;

    /**
     * Default value of {@link IndustrialTile#getWorkersCapacity()}
     */

    public final static int DEFAULT_WORKERS_CAPACITY = 40;
    
    /**
     * Default value of {@link IndustrialTile#getProductsCapacity()}
     */
    
    public final static int DEFAULT_PRODUCTS_CAPACITY = 200;

    // Implementation
    /**
     * {@link #getWorkersCapacity()}
     */
    private final int workersCapacity;
    
    /**
     * {@link #getProductsCapacity()}
     */
    private final int productsCapacity;



    /**
     * Maximum number of new workers for each update.
     */
    public final int maxJoiningWorkers;

    /**
     * Maximum number of leaving workers for each update.
     */
    private final int maxLeavingWorkers;

    /**
     * {@link #getMaxNeededEnergy()}
     */
    private final int maxNeededEnergy;


    /**
     * {@link #getMaxNeededWater()}
     */
    private final int maxNeededWater;
    
    // Creation
    /**
     * @param capacity
     *            - {@link #getInhabitantsCapacity()}
     *            
     * creates an instance of industrialTile depending on given settings
     */
    public IndustrialTile(int capacity) {
        super(IndustrialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, DEFAULT_EVOLUTION_WATER_CONSUMPTION);

        this.workersCapacity = capacity;
        this.maxNeededEnergy = IndustrialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededWater = CommercialTile.DEFAULT_MAX_NEEDED_WATER;
        this.maxJoiningWorkers = IndustrialTile.DEFAULT_MAX_JOINING_WORKERS;
        this.maxLeavingWorkers = IndustrialTile.DEFAULT_MAX_LEAVING_WORKERS;

        this.productsCapacity = IndustrialTile.DEFAULT_PRODUCTS_CAPACITY ;
    }

    /**
     * Create with default settings.
     */
    public IndustrialTile() {
        this(IndustrialTile.DEFAULT_WORKERS_CAPACITY);
    }

    // Access
    /**
     * @return Maximum number of workers.
     */
    public final int getworkersCapacity() {
        return this.workersCapacity;
    }
    /**
     * @return Maximum number of products.
     */
    public final int getproductsCapacity() {
        return this.productsCapacity;
    }
    
    


    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the factory is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }


    /**
     * @return Maximum number of water units to consume. This maximum is
     *         consumed if the factory is full.
     */
    public final int getMaxNeededWater() {
        return this.maxNeededWater;
    }
    
    /**
     * 
     * @return the actual production of the factory
     */
    public int getProduction(CityResources res){
    	double workers =(double) this.getWorkers(res);
        double busyPercentage = ((workers) / ((double)this.workersCapacity));
        int production = (int)(busyPercentage*(100-res.getIllnessRate())/1.5);
        production = Math.min(production, this.productsCapacity);
        return production;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.workersCapacity;
        result = result * 17 + this.maxJoiningWorkers;
        result = result * 17 + this.maxLeavingWorkers;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededWater;
        result = result * 17 + this.productsCapacity;
        return result;
    }

    // Status
    /**
     * equals returns true if the selected object is an industrial tile , false otherwise
     *  
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof IndustrialTile && this.equals((IndustrialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(IndustrialTile o) {
        return this == o || super.equals(o) && o.workersCapacity == this.workersCapacity && o.maxJoiningWorkers == this.maxJoiningWorkers
                && o.maxLeavingWorkers == this.maxLeavingWorkers && o.maxNeededEnergy == this.maxNeededEnergy && o.maxNeededWater == this.maxNeededWater;
    }

    /**
     * return true if the selected building is destroyed, false otherwise
     *  
     */
    @Override
    public boolean isDestroyed() {
        return this.state == ConstructionState.DESTROYED;
    }

    // Change
    /**
     * removes the selected building from the map and changes the city resources given the building 
     *  
     */
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {

            res.decreaseWorkersCapacity(this.workersCapacity);
            res.decreaseProductsCapacity(this.productsCapacity);

            res.fireWorkers(Math.min(this.workersCapacity,getWorkers(res)));

            super.disassemble(res);
        }
    }

    
    /**
     * evolve takes a building from the under_construction state to the built state and updates the city 
     * resources given the change
     *  
     */
    @Override
    public void evolve(CityResources res) {
        super.evolve(res);

        if (this.state == ConstructionState.BUILT) {

            res.increaseWorkersCapacity(this.workersCapacity);
            res.increaseProductsCapacity(this.productsCapacity);

           // res.hireWorkers(this.maxJoiningWorkers);
            this.update(res);
        }
    }

    /**
     * Update changes the state of the building if their are riot happening in the city and puts the 
     * building on riot mode so that it stops producing anything. If their are to much buildings on riot 
     * compared to the number set by the Riotevent, the buildings go back to usual.
     * 
     * Then when the building is on the state "built" , given it's connection to the networks( road, power and 
     * water network) and the fact that enough energy are being produced or not it's workers population will rise or fall.
     * It will also produce more or less products bound to the available energy.
     * 
     */
    @Override
    public void update(CityResources res) {
    	if (res.getriotbuilding()<res.geteventsetriotbuilding() && this.state==ConstructionState.BUILT){
    		this.state = ConstructionState.RIOT;
    		res.riotbuildingchange(1);
    	}
    	if (res.getriotbuilding()>res.geteventsetriotbuilding() && this.state==ConstructionState.RIOT){
    		this.state=ConstructionState.BUILT;
    		res.riotbuildingchange(-1);
    		
    	}
    	if (this.state == ConstructionState.BUILT) {
            final int workers = this.getWorkers(res);

            final int busyPercentage = workers * 100 / this.workersCapacity; // Integer                                                   // division
            final int neededEnergy = Math.max(1, busyPercentage * this.maxNeededEnergy / 100); // Integer

            final int neededWater = Math.max(1, busyPercentage * this.maxNeededWater / 100);   


            boolean isResActif=res.isAdjacentActiveGlobal(position);
            if(isResActif){
	            if (res.getUnconsumedEnergy() >= neededEnergy && res.getUnconsumedWater() >= neededWater) {
	            	res.consumeEnergy(neededEnergy);

	                res.consumeWater(neededWater);
	                this.isEnergyMissing = false;
	                this.isWaterMissing = false;
	
	                // Less space is available, less newcomers join
	                final int vacantPercentage = 100 - busyPercentage;
	                final int newcomers = Math.max(vacantPercentage * this.maxJoiningWorkers / 100,0);
	                
	                res.hireWorkers(newcomers);
	                res.storeProducts(this.getProduction(res));
	            } else {
	            	final int consumedEnergy = Math.min(res.getUnconsumedEnergy(),neededEnergy);
	            	final int consumedWater = Math.min(res.getUnconsumedWater(),neededWater);
	            	
	            	res.consumeWater(consumedWater);
	                res.consumeEnergy(consumedEnergy);
	                //this.isEnergyMissing = true;
	
	                // More energy units are missing, more inhabitants leave
	                int missingPercentage = 100;
	                if(res.getUnconsumedEnergy() < neededEnergy){
	                	missingPercentage-= consumedEnergy * 100 / neededEnergy;
	                	this.isEnergyMissing = true;
	                }
	                if(res.getUnconsumedWater() < neededWater){
	                	missingPercentage-= consumedWater * 100 / neededWater;
	                	this.isWaterMissing = true;
	                }
	                missingPercentage=Math.max(0, missingPercentage);
	                final int leavingWorkers = Math.min(this.maxLeavingWorkers, missingPercentage * workers / 100); // Integer

	                                                                                                                                  // division

	                res.fireWorkers(leavingWorkers);
	            }
            }else{
            	this.isEnergyMissing=true;
            	this.isWaterMissing=true;
            	res.fireWorkers(this.maxLeavingWorkers);


            }
        }
    }

    // Implementation
    /**
     * @param res
     * @return Approximation of the number of inhabitants in the current
     *         residence if the population is uniformly distributed.
     *
     *         e.g. if the residence capacity is X = 50, the city capacity is Y
     *         = 100 (including X) and the population is Z = 20, then the
     *         residence has (X / Y) * Z = 10 inhabitants.
     */
    private int getWorkers(CityResources res) {

        final int caapcityPercentage = this.workersCapacity * 100 / Math.max(res.getWorkersCapacity(),1); // Integer                                                                                          // division
        return res.getWorkingPopulation() * caapcityPercentage / 100; // Integer

                                                               // division
    }

}