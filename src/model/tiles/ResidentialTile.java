/**
 * TNCity
 * Copyright (c) 2017
 *  Jean-Philippe Eisenbarth,
 *  Victorien Elvinger
 *  Martine Gautier,
 *  Quentin Laporte-Chabasse
 *
 *  This file is part of TNCity.
 *
 *  TNCity is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  TNCity is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License
 *  along with TNCity.  If not, see <http://www.gnu.org/licenses/>.
 */

package model.tiles;

import model.CityResources;

/**
 * Enable to welcome new inhabitants and consume energy units according to the
 * number of inhabitants.
 */
public class ResidentialTile extends BuildableTile {
	

    // Constants
    /**
     * Default value of {@link ResidentialTile#getEvolutionEnergyConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_ENERGY_CONSUMPTION = 5;
    
    /**
     * Default value of {@link ResidentialTile#getEvolutionWaterConsumption()}
     */
    public final static int DEFAULT_EVOLUTION_WATER_CONSUMPTION = 5;
    
    /**
     * Default value of {@link ResidentialTile#maxJoiningInhabitants}
     */
    private final static int DEFAULT_MAX_JOINING_INHABITANTS = 15;

    /**
     * Default value of {@link ResidentialTile#maxLeavingInhabitants}
     */
    private final static int DEFAULT_MAX_LEAVING_INHABITANTS = 10;

    /**
     * Default value of {@link ResidentialTile#getMaxNeededEnergy()}
     */
    public final static int DEFAULT_MAX_NEEDED_ENERGY = 30;
    

    /**
     * Default value of {@link ResidentialTile#getMaxNeededWater()}
     */
    public final static int DEFAULT_MAX_NEEDED_WATER = 30;
    

    /**
     * Default value of {@link ResidentialTile#getInhabitantsCapacity()}
     */
    public final static int DEFAULT_INHABITANTS_CAPACITY = 40;

    // Implementation
    /**
     * {@link #getInhabitantsCapacity()}
     */
    private final int inhabitantsCapacity;

    /**
     * Maximum number of newcomers for each update.
     */
    private final int maxJoiningInhabitants;

    /**
     * Maximum number of leaving inhabitants for each update.
     */
    private final int maxLeavingInhabitants;

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
     */
    public ResidentialTile(int capacity) {
        super(ResidentialTile.DEFAULT_EVOLUTION_ENERGY_CONSUMPTION, DEFAULT_EVOLUTION_WATER_CONSUMPTION);

        this.inhabitantsCapacity = capacity;
        this.maxNeededEnergy = ResidentialTile.DEFAULT_MAX_NEEDED_ENERGY;
        this.maxNeededWater= ResidentialTile.DEFAULT_MAX_NEEDED_WATER;
        this.maxJoiningInhabitants = ResidentialTile.DEFAULT_MAX_JOINING_INHABITANTS;
        this.maxLeavingInhabitants = ResidentialTile.DEFAULT_MAX_LEAVING_INHABITANTS;
    }

    /**
     * Create with default settings.
     */
    public ResidentialTile() {
        this(ResidentialTile.DEFAULT_INHABITANTS_CAPACITY);
    }


    // Access
    /**
     * @return Maximum number of inhabitants.
     */
    public final int getInhabitantsCapacity() {
        return this.inhabitantsCapacity;
    }

    /**
     * @return Maximum number of energy units to consume. This maximum is
     *         consumed if the residence is full.
     */
    public final int getMaxNeededEnergy() {
        return this.maxNeededEnergy;
    }
    

    /**
     * @return Maximum number of water units to consume. This maximum is
     *         consumed if the residence is full.
     */
    public final int getMaxNeededWater() {
        return this.maxNeededWater;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 17 + this.inhabitantsCapacity;
        result = result * 17 + this.maxJoiningInhabitants;
        result = result * 17 + this.maxLeavingInhabitants;
        result = result * 17 + this.maxNeededEnergy;
        result = result * 17 + this.maxNeededWater;
        return result;
    }

    // Status
    /**
     * return true if the selected object is a residential tile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof ResidentialTile && this.equals((ResidentialTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(ResidentialTile o) {
        return this == o || super.equals(o) && o.inhabitantsCapacity == this.inhabitantsCapacity && o.maxJoiningInhabitants == this.maxJoiningInhabitants
                && o.maxLeavingInhabitants == this.maxLeavingInhabitants && o.maxNeededEnergy == this.maxNeededEnergy && o.maxNeededWater==this.maxNeededWater;
    }

    /**
     * destroy the selected building
     */
    @Override
    public boolean isDestroyed() {
        return this.state == ConstructionState.DESTROYED;
    }

    // Change
    /**
     * remove the selected residential zone tile and changes the resources given the tile chosen 
     */
    @Override
    public void disassemble(CityResources res) {
        if (this.state == ConstructionState.BUILT) {
            res.decreasePopulationCapacity(this.inhabitantsCapacity);

            super.disassemble(res);
        }
    }

    /**
     * build the selected residential zone and changes population capacity given the building built
     */
    @Override
    public void evolve(CityResources res) {
        super.evolve(res);

        if (this.state == ConstructionState.BUILT) {
            res.increasePopulationCapacity(this.inhabitantsCapacity);

            this.update(res);
        }
    }
    
    /**
     * Update changes the state of the building if their are riot happening in the city and puts the 
     * building on riot mode so that it stops producing anything. If their are to much buildings on riot 
     * compared to the number set by the Riotevent, the buildings go back to usual.
     * 
     * Then when the building is on the state "built" , given it's connection to the networks( road, power and 
     * water network) and the fact that enough energy are being produced or not it's population will rise or fall.
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
            final int inhabitants = this.getInhabitants(res);

            final int busyPercentage = inhabitants * 100 / this.inhabitantsCapacity; // Integer
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
	
	                // Less space is available, less newcomers join
	                final int vacantPercentage = 100 - busyPercentage;
	                final int newcomers = vacantPercentage * this.maxJoiningInhabitants / 100;
	
	                res.increasePopulation(newcomers);
	            } else {
	            	final int consumedEnergy = Math.min(res.getUnconsumedEnergy(),neededEnergy);
	            	final int consumedWater = Math.min(res.getUnconsumedWater(),neededWater);
	            	
	            	res.consumeWater(consumedWater);
	                res.consumeEnergy(consumedEnergy);
	                
	
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
	                final int leavingInhabitants = Math.min(this.maxLeavingInhabitants, missingPercentage * inhabitants / 100); // Integer
	                                                                                                                                  // division
	
	                res.decreasePopulation(leavingInhabitants);
	            }
            }else{
            	this.isEnergyMissing=true;
            	this.isWaterMissing=true;
            	int leavingInhabitants=Math.min(this.maxLeavingInhabitants,(int) (inhabitants*0.1));
            	res.decreasePopulation(leavingInhabitants);
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
    public int getInhabitants(CityResources res) {
        assert res.getPopulationCapacity() != 0;

        final int caapcityPercentage = this.inhabitantsCapacity * 100 / res.getPopulationCapacity(); // Integer
                                                                                            // division
        return res.getPopulation() * caapcityPercentage / 100; // Integer
                                                               // division
    }

}
