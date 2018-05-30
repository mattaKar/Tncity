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

import calculMat.TypeRes;
import model.CityResources;

/**
 * Tile that can evolve and be destroyed. An evolution has an energy cost.
 */
public abstract class BuildableTile extends Tile implements Evolvable, Destroyable {

    // Implementation
	
    /**
     * {@link #isEnergyMissing()}
     */
    protected boolean isEnergyMissing;
    
    /**
     * {@link #getEvolutionEnergyConsumption()}
     */
    private final int evolutionEnergyConsumption;


    /**
     * {@link #isEnergyMissing()}
     */
    protected boolean isWaterMissing;

    /**
     * {@link #getEvolutionEnergyConsumption()}
     */
    private final int evolutionWaterConsumption;


    /**
     * {@link #getState()}
     */
    protected ConstructionState state;

    // Creation
    /**
     * Create a tile under construction.
     *
     * @param evolutionEnergyConsumption
     *            - {@link #getEvolutionEnergyConsumption()}
     * @param evolutionWaterConsumption TODO
     */

    public BuildableTile(int evolutionEnergyConsumption,int evolutionWaterConsumption) {
        assert evolutionEnergyConsumption >= 0 && evolutionWaterConsumption>=0;


        this.evolutionEnergyConsumption = evolutionEnergyConsumption;
        this.evolutionWaterConsumption = evolutionWaterConsumption;
        this.state = ConstructionState.UNDER_CONSTRUCTION;
        this.isEnergyMissing = false;
        this.isWaterMissing= false;
    }

    // Access
    /**
     * @return Consumed energy during an evolution.
     */
    public final int getEvolutionEnergyConsumption() {
        return this.evolutionEnergyConsumption;
    }
    

    /**
     * @return Consumed water during an evolution.
     */
    public final int getEvolutionWaterConsumption() {
        return this.evolutionWaterConsumption;
    }

    /**
     * @return construction's state.
     */
    public final ConstructionState getState() {
        return this.state;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + evolutionEnergyConsumption;
        result = result * 17 + evolutionWaterConsumption;
        result = result * 17 + state.hashCode();
        return result;
    }

    // Status
    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(BuildableTile o) {
        return o.evolutionEnergyConsumption == evolutionEnergyConsumption && o.evolutionWaterConsumption == evolutionWaterConsumption && o.state == state;

    }

    // Status
    /**
     * returns true if the state of a building is under_construction, false otherwise
     */
    @Override
    public final boolean canEvolve() {
        return this.state == ConstructionState.UNDER_CONSTRUCTION;
    }

    /**
     * @return Is energy missing in order to evolve or to update?
     */
    public final boolean isEnergyMissing() {
        return this.isEnergyMissing;
    }
    

    /**
     * @return Is water missing in order to evolve or to update?
     */
    public final boolean isWaterMissing() {
        return this.isWaterMissing;
    }

    // Change

    /**
     * Sets the state of a given building to destroyed
     */
    @Override
    public void disassemble(CityResources res) {
        this.state = ConstructionState.DESTROYED;
    }
    
    /**
     * if a building is beside a road that is connected to the 
     * main resources (cityhall, waterplant and powerplant), then the building will evolve
     * toward a "built" state and start producing what it is intended to produce.
     * Else it will give the accurate tag to show to the player what is missing for the building to be built
     */
    @Override
    public void evolve(CityResources res) {
    	boolean isResActif=res.isAdjacentActiveGlobal(position) || (this instanceof CityHall);
        if (canEvolve()) {
            if (res.getUnconsumedEnergy() >= evolutionEnergyConsumption && isResActif && res.getUnconsumedWater() >= evolutionWaterConsumption) {
                this.isEnergyMissing = false;
                this.isWaterMissing = false;
                res.consumeEnergy(this.evolutionEnergyConsumption);
                res.consumeWater(this.evolutionWaterConsumption);
                this.state = ConstructionState.BUILT;
            } else {
            	if(res.getUnconsumedEnergy() < evolutionEnergyConsumption)
            		this.isEnergyMissing = true;
            	if(res.getUnconsumedWater() < evolutionWaterConsumption)
            		this.isWaterMissing = true;
            }
        }
    }

}
