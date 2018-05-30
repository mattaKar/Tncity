
package model.tiles;

import model.CityResources;

/**
 * Enable to welcome new workers and consume energy units according to the
 * number of workers. Sell (consume products) according to the number of people in the city and 
 * bound to the number of workers in the shop
 */
public class CommercialTile extends BuildableTile {

    // Constants
    /**
     * Default value of {@link CommercialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;

    /**

     * Default value of {@link CommercialTile#getEvolutionWaterConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 5;
    
    /**
     * Default value of {@link CommercialTile#maxJoiningWorkers}
     */
    private final static int DEFAULT_MAX_JOINING_WORKERS = 15;

    /**
     * Default value of {@link CommercialTile#maxLeavingWorkers}
     */
    private final static int DEFAULT_MAX_LEAVING_WORKERS = 5;

    /**
     * Default value of {@link CommercialTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;

    /**
     * Default value of {@link CommercialTile#getMaxNeededWater()}
     */
    public final static int DEFAULT_MAX_NEEDED_WATER = 30;
    /**
     * Default value of {@link CommercialTile#getWorkersCapacity()}
     */
    public final static int DEFAULT_WORKERS_CAPACITY = 20;
    /**
     * Default value of {@link CommercialTile#getConsumptionCapacity()}
     */
    public final static int DEFAULT_MAX_CONSUMPTION_CAPACITY = 60;
    
    


    // Implementation
    /**
     * {@link #getWorkersCapacity()}
     */
    private final int workersCapacity;

    /**
     * Maximum number of new workers for each update.
     */

    private final int maxJoiningWorkers;

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

    
    /**
     * {@link #getMaxConsumption()}
     */
    private final int maxConsumption;
    
    
    // Creation
    /**
     * @param capacity

     *            - {@link #getInhabitantsCapacity()}

     *            - {@link #getworkersCapacity()}

     */
    public CommercialTile(int capacity) {
        super(CommercialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, DEFAULT_EVOLUTION_WATER_CONSUMPTION);


        this.workersCapacity = capacity;
        this.maxNeededEnergy = CommercialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededWater = CommercialTile.DEFAULT_MAX_NEEDED_WATER;
        this.maxJoiningWorkers = CommercialTile.DEFAULT_MAX_JOINING_WORKERS;
        this.maxLeavingWorkers = CommercialTile.DEFAULT_MAX_LEAVING_WORKERS;
        this.maxConsumption = CommercialTile.DEFAULT_MAX_CONSUMPTION_CAPACITY;

    }

    /**
     * Create with default settings.
     */
    public CommercialTile() {

        this(CommercialTile.DEFAULT_WORKERS_CAPACITY);

    }

    // Access
    /**
     * @return Maximum number of workers.
     */

    public final int getworkersCapacity() {
        return this.workersCapacity;
    }
    
    


    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the shop is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }
    

    /**
     * @return Maximum number of water units to consume. This maximum is
     *         consumed if the shop is full.
     */

    public final int getMaxNeededWater() {
        return this.maxNeededWater;
    }
    
    /**
     * @return Maximum number of product units to consume. This maximum is
     *         consumed if the shop is full.
     */

    public final int getMaxComsumption() {
        return this.maxConsumption;
    }

    
    /**
     *    gives the amount of products to be consumed by the city bound to various factors
     *    such as the population amount, happiness, the number of workers in the shop.
     */
    public int getConsumption(CityResources res){
    	double busyPercentage = this.getWorkers(res)/((double)Math.max(this.workersCapacity,1));
    	int conso = (int)(res.getOldHappiness()*busyPercentage*((double)res.getPopulation())/50);//150=3*100/2
    	conso = Math.min(conso,this.maxConsumption);
    	return(conso);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = result * 17 + this.workersCapacity;
        result = result * 17 + this.maxJoiningWorkers;
        result = result * 17 + this.maxLeavingWorkers;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededWater;

        return result;
    }

    // Status
    /**
     * returns true if the selected object is an instance of commercial tile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof CommercialTile && this.equals((CommercialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(CommercialTile o) {
        return this == o || super.equals(o) && o.workersCapacity == this.workersCapacity && o.maxJoiningWorkers == this.maxJoiningWorkers && o.maxLeavingWorkers == this.maxLeavingWorkers && o.maxNeededEnergy == this.maxNeededEnergy && o.maxNeededWater == this.maxNeededWater;


    }
    /**
     * returns true if the selected commercial tile is destroyed, false otherwise
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
     * It will also sell more or less products bound to the available energy.
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

				final int busyPercentage = workers * 100 / this.workersCapacity; // Integer
                                                                         // division
				final int neededEnergy = Math.max(1, busyPercentage * this.maxNeededEnergy / 100); // Integer
                                                                                   // division

				final int neededWater = Math.max(1, busyPercentage * this.maxNeededWater / 100);

				boolean isResActif=res.isAdjacentActiveGlobal(position);
				if(isResActif){
	            if (res.getUnconsumedEnergy() >= neededEnergy && res.getUnconsumedWater() >= neededWater) {
	                res.consumeEnergy(neededEnergy);
	                res.consumeWater(neededWater);
	                this.isEnergyMissing = false;
	                this.isWaterMissing = false;
	                res.consumeProducts(this.getConsumption(res));
	                
	                // Less space is available, less newcomers join

	                final int vacantPercentage = 100 - busyPercentage;
	                
	                final int newworkers =Math.max(vacantPercentage * this.maxJoiningWorkers / 100,0);

	                res.hireWorkers(newworkers);
	            } else {
	            	final int consumedEnergy = Math.min(res.getUnconsumedEnergy(),neededEnergy);

	            	final int consumedWater = Math.min(res.getUnconsumedWater(),neededWater);
	            	
	            	res.consumeWater(consumedWater);
	                res.consumeEnergy(consumedEnergy);
	                

	                // More energy units are missing, more inhabitants leave
	                /*
	                final int missingEnergyPercentage = 100 - consumedEnergy * 100 / neededEnergy; // Integer                                                                                     // division
	                */
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
       // assert res.getWorkersCapacity() != 0;
        final int caapcityPercentage = this.workersCapacity * 100 / Math.max(res.getWorkersCapacity(),1); // Integer                                                                                          // division
        return res.getWorkingPopulation() * caapcityPercentage / 100; // Integer
                                                               // division
    }
}


