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

package model;

import calculMat.EtatRes;
import calculMat.MatRes;
import model.tiles.PolicestationTile;
import ui.Impot;

/**
 * Represents the resources and the parameters of the city. An ephemeral
 * resource is reset at each step thanks to {@link CityResources#getVat()}.
 */
public class CityResources {

    // Constant
    /**
     * Default value for {@link CityResources#getVat()}.
     */
    public final static int DEFAULT_VAT = 20;

    // Implementation (Currency)
    /**
     * {@link #getCurrency()}
     */
    private int currency;

    /**
     * {@link #getVat()}
     */
    private int vat;

    // Implementation (Energy)
    /**
     * {@link #getUnconsumedEnergy()}
     */
    private int unconsumedEnergy;

    /**
     * {@link #getEnergyProduction()}
     */
    private int energyProduction;


    // Implementation (Water)
    /**
     * {@link #getUnconsumedWater()}
     */
    private int unconsumedWater;

    /**
     * {@link #getWaterProduction()}
     */
    private int waterProduction;

    // Implementation (Population)
    /**
     * {@link #getUnworkingPopulation()}
     */
    private int unworkingPopulation;

    /**
     * {@link #getPopulation()}
     */
    private int population;

    /**
     * {@link #getPopulationCapacity()}
     */
    private int populationCapacity;
    
    /**
     * {@link #getWorkersCapacity()}
     */
    private int workersCapacity;
    
    /**
     * {@link #getWorkers()}
     */
    private int workers;

    // Implementation (Product)
    /**
     * {@link #getProductsCount()}
     */
    private int productsCount;

    /**
     * {@link #getProductsCapacity()}
     */
    private int productsCapacity;

    // Implementation (networks)
    /**
     * MatRes: road power and water network
     */
    private MatRes MatRes;
    
    // Implementation (health)
    /**
     * {@link #getIllnessRate()}
     */
    private int illnessRate;
    
    /**
     * {@link #getCareCapacity()}
     */
    private int careCapacity;
  
    // Implementation (security)
    /**
     * {@link #getPolicestationCapacity()}
     */
    private int PolicestationCapacity;
    
    /**
     * {@link #getSecurityRate()}
     */
    private int securityRate;
    
    // Implementation (taxes)
    /**
     * sets the taxes for the game
     */
    public Impot impot;
    
    // Implementation (riots)
    /**
     * {@link #getriotbuilding()}
     */
    private int riotbuilding;
    
    /**
     * {@link #geteventsetriotbuilding()}
     */
    private int eventsetriotbuilding;
    

    /**
     * percentage between 0 and 100 where 50 means the people are neither happy nor unhappy
     */
    private double happiness;
    
    /**
     * ancient tick value of happiness that we store to calculate the consumption
     */
    private double oldHappiness;

    
    
    // Creation
    /**
     *
     * @param aCurrency
     *            - {@link #getCurrency()}
     * @param height 
     * @param width 
     */
    public CityResources(int aCurrency, int height, int width) {
        assert aCurrency >= 0;

        this.currency = aCurrency;
        this.vat = CityResources.DEFAULT_VAT;
        this.MatRes=new MatRes(height,width);
        this.impot=new Impot();
        this.illnessRate= 0;
        this.securityRate=0;

        this.riotbuilding=0;
        this.eventsetriotbuilding=0;

        this.happiness=50;
        this.oldHappiness=50;


    }

    /**
     *
     * @param aCurrency
     *            - {@link #getCurrency()}
     * @param aPopulation
     *            - {@link #getPopulation()} and
     *            {@link #getPopulationCapacity()}
     * @param height 
     * @param width 
     */
    public CityResources(int aCurrency, int aPopulation, int height, int width) {
        this(aCurrency, height, width);
        assert aPopulation >= 0;

        this.population = aPopulation;
        this.populationCapacity = aPopulation;

        this.resetEphemerals();
    }

    // Status
    @Override
    public boolean equals(Object o) {
        return o instanceof CityResources && this.equals((CityResources) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(CityResources o) {
        return this == o || super.equals(o) && o.currency == this.currency && o.vat == this.vat && o.unconsumedEnergy == this.unconsumedEnergy && o.energyProduction == this.energyProduction
                && o.unworkingPopulation == this.unworkingPopulation && o.population == this.population && o.populationCapacity == this.populationCapacity && o.productsCount == this.productsCount
                && o.productsCapacity == this.productsCapacity && o.unconsumedWater == this.unconsumedWater && o.waterProduction == this.waterProduction;
    }

    // Access
    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.currency;
        result = result * 17 + this.vat;
        result = result * 17 + this.unconsumedEnergy;
        result = result * 17 + this.energyProduction;
        result = result * 17 + this.unconsumedWater;
        result = result * 17 + this.waterProduction;
        result = result * 17 + this.unworkingPopulation;
        result = result * 17 + this.population;
        result = result * 17 + this.populationCapacity;
        result = result * 17 + this.productsCount;
        result = result * 17 + this.productsCapacity;
        result = result * 17 + this.illnessRate;
        result = result * 17 + this.careCapacity;
        result = result * 17 + this.riotbuilding;
        result = result * 17 + this.eventsetriotbuilding;
        result = result * 17 + this.securityRate;
        result = result * 17 + this.PolicestationCapacity;
        
        return result;
    }

    // Access (Currency)
    /**
     *
     * @return Accumulated currency.
     */
    public int getCurrency() {
        return this.currency;
    }

    /**
     * @return Value-Added-Tax in percentage.
     */
    public int getVat() {
        return this.vat;
    }

    // Access (Energy)
    /**
     * @return Number of consumed energy units.
     */
    public int getConsumedEnergy() {
        return this.energyProduction - this.unconsumedEnergy;
    }

    /**
     * @return Number of available energy units.
     */
    public int getUnconsumedEnergy() {
        return this.unconsumedEnergy;
    }

    /**
     *
     * @return Monthly production of energy units.
     */
    public int getEnergyProduction() {
        return this.energyProduction;
    }

    // Access (Water)
    /**
     * @return Number of consumed Water units.
     */
    public int getConsumedWater() {
        return this.waterProduction - this.unconsumedWater;
    }

    /**
     * @return Number of available Water units.
     */
    public int getUnconsumedWater() {
        return this.unconsumedWater;
    }

    /**
     *
     * @return Monthly production of Water units.
     */
    public int getWaterProduction() {
        return this.waterProduction;
    }
    // Access (Population)
    /**
     * @return Number of job-less citizens.
     */
    public int getUnworkingPopulation() {
        return this.unworkingPopulation;
    }

    /**
     * @return Number of citizens with a job.
     */
    public int getWorkingPopulation() {
        return this.population - this.unworkingPopulation;
    }

    /**
     * @return Total number of citizens.
     */
    public int getPopulation() {
        return this.population;
    }

    /**
     *
     * @return Capacity of the city.
     */
    public int getPopulationCapacity() {
        return this.populationCapacity;
    }
    
    // Access (public services)
    /**
     * 
     * @return capacity of hospitals
     */
    public int getCareCapacity(){
    	return this.careCapacity;
    }
    
    /**
     * 
     * @return capacity of police stations
     */
    
    public int getPolicestationCapacity(){
    	return this.PolicestationCapacity;
    }

    // Access (Product)
    /**
     * @return Accumulated number of products.
     */
    public int getProductsCount() {
        return this.productsCount;
    }

    /**
     * @return Maximum number of products that can be stored.
     */
    public int getProductsCapacity() {
        return this.productsCapacity;
    }
    
    /**
     * @return Maximum number of workers that can be employed.
     */
    public int getWorkersCapacity() {
        return this.workersCapacity;
    }

    // Access (MatRes)
    /**
     * @return MatRes, the network informative matrix
     */
    public MatRes getMatRes() {
		return MatRes;
	}
    
    // Access (city grades)
    /**
     * @return the IllnessRate
     */
    public int getIllnessRate(){
    	return this.illnessRate;
    }
    
    /**
     * @return the SecurityRate
     */
    public int getSecurityRate() {
		return this.securityRate;
	}

	// Access (rioting buildings)
    
    /**
     * @return the buildings actually rioting in your city
     */
    public int getriotbuilding(){
    	return this.riotbuilding;
    }
    
    /**
     * @return the buildings supposed to riot according to the events that happened
     */
    public int geteventsetriotbuilding(){
    	return this.eventsetriotbuilding;
    }
    // change (riot buildings)
    
    /**
     * changes the number of rioting building by a given amount
     */
    public void riotbuildingchange(int amount) {
        this.riotbuilding = this.riotbuilding + amount;
    }
    
    /**
     * changes the number of event set rioting building by a given amount
     */
    public void eventsetriotbuildingchange(int amount) {
        this.eventsetriotbuilding = this.eventsetriotbuilding + amount;
    }
    
    /**
     * gets the last Tick happiness
     */
    public double getOldHappiness(){
    	return this.oldHappiness;
    }
    
    // Change (illness)
    /**
     * Increase {@link #getillnessRate()} by {@value amount}.
     *
     * @param amount
     */
    public void illnessRateUp(int amount) {
        assert amount >= 0;

        this.illnessRate = this.illnessRate + amount;
    }
    
    /**
     * Decrease {@link #getillnessRate()} by {@value amount}.
     *
     * @param amount
     */
    public void illnessRateDown(int amount) {
        assert amount >= 0;

        this.illnessRate = this.illnessRate - amount;
    }
    
   
    //change (care capacity)
    
    /**
     * Decrease {@link #getCareCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void decreaseCareCapacity(int amount) {
        assert 0 <= amount && amount <= this.getCareCapacity();

        this.careCapacity = this.careCapacity - amount;
    }
    
    /**
     * Increase {@link #getCareCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void increaseCareCapacity(int amount) {
        assert 0 <= amount;

        this.careCapacity = this.careCapacity + amount;
    }

    
 // Change (securityRate)
    /**
     * Increase {@link #getsecurityRate()} by {@value amount}.
     *
     * @param amount
     */
    public void securityRateUp(int amount) {
        assert amount >= 0;

        this.securityRate = this.securityRate + amount;
    }
    
    /**
     * Decrease {@link #getsecurityRate()} by {@value amount}.
     *
     * @param amount
     */
    public void securityRateDown(int amount) {
        assert amount >= 0;

        this.securityRate = this.securityRate - amount;
    }
    //change (Policestation capacity)
    /**
     * Decrease {@link #getPolicestationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void decreasePolicestationCapacity(int amount) {
        assert 0 <= amount && amount <= this.getPolicestationCapacity();

        this.PolicestationCapacity = this.PolicestationCapacity - amount;
    }
    
    /**
     * Increase {@link #getPolicestationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void increasePolicestationCapacity(int amount) {
        assert 0 <= amount;

        this.PolicestationCapacity = this.PolicestationCapacity + amount;
    }

	// Change (Currency)
    /**
     * Decrease {@link #getCurrency()} by {@value amount}.
     *
     * @param amount
     */
    public void credit(int amount) {
        assert amount >= 0;

        this.currency = this.currency + amount;
    }

    /**
     * Get VAT on {@value currencyAmount} and {@link #credit(int)} with the
     * obtained result.
     *
     * @param currencyAmount
     */
    public void creditWithTaxes(int currencyAmount) {
        assert currencyAmount >= 0;

        this.credit(currencyAmount * this.vat / 100); // Integer division
    }

    /**
     * Increase {@link #getCurrency()} by {@value amount}.
     *
     * @param amount
     */
    public void spend(int amount) {
        assert amount >= 0;

        this.currency = this.currency - amount;
    }

    // Change (Energy)
    /***
     * Increase {@link #getConsumedEnergy()} by {@value amount}.
     *
     * @param amount
     */
    public void consumeEnergy(int amount) {
        assert 0 <= amount && amount <= this.getUnconsumedEnergy();

        this.unconsumedEnergy = this.unconsumedEnergy - amount;
    }

    /**
     * Decrease {@link #getEnergyProduction()} by {@value amount}.
     *
     * @param amount
     */
    public void decreaseEnergyProduction(int amount) {
        assert amount >= 0;

        this.energyProduction = Math.max(0, this.energyProduction - amount);
        this.unconsumedEnergy = Math.min(this.unconsumedEnergy, this.energyProduction);
    }

    /**
     * Increase {@link #getEnergyProduction()} by {@value amount}.
     *
     * @param amount
     */
    public void increaseEnergyProduction(int amount) {
        assert amount >= 0;

        this.energyProduction = this.energyProduction + amount;
        this.unconsumedEnergy = this.unconsumedEnergy + amount;
    }

    // Change (Water)
    /***
     * Increase {@link #getConsumedWater()} by {@value amount}.
     *
     * @param amount
     */
    public void consumeWater(int amount) {
        assert 0 <= amount && amount <= this.getUnconsumedWater();

        this.unconsumedWater = this.unconsumedWater - amount;
    }

    /**
     * Decrease {@link #getWaterProduction()} by {@value amount}.
     *
     * @param amount
     */
    public void decreaseWaterProduction(int amount) {
        assert amount >= 0;

        this.waterProduction = Math.max(0, this.waterProduction - amount);
        this.unconsumedWater = Math.min(this.unconsumedWater, this.waterProduction);
    }

    /**
     * Increase {@link #getWaterProduction()} by {@value amount}.
     *
     * @param amount
     */
    public void increaseWaterProduction(int amount) {
        assert amount >= 0;

        this.waterProduction = this.waterProduction + amount;
        this.unconsumedWater = this.unconsumedWater + amount;
    }
    // Change (Population)
    /**
     * Increase {@link #getWorkingPopulation()} by {@value amount}.
     *
     * @param amount
     */
    public void hireWorkers(int amount) {
        amount=Math.max(0, Math.min(amount, this.getUnworkingPopulation()));
    	assert 0 <= amount && amount <= this.getUnworkingPopulation();

        if (amount<=this.getUnworkingPopulation()){
        		this.unworkingPopulation = this.unworkingPopulation - amount;
    }
        else {
        	this.unworkingPopulation = 0;
        }
    }
    
    /**
     * Decrease {@link #getWorkingPopulation()} by {@value amount}.
     *
     * @param amount
     */
    public void fireWorkers(int amount) {
    	amount=Math.max(0, Math.min(amount, this.getWorkingPopulation()));
    	assert 0 <= amount && amount <= this.getWorkingPopulation();
    	
    	this.unworkingPopulation = this.unworkingPopulation + amount;
    }

    /**
     * Increase {@link #getWorkingPopulationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void increaseWorkersCapacity(int amount) {
        assert amount >= 0;

        this.workersCapacity = this.workersCapacity + amount;
    }

    /**
     * Decrease {@link #getWorkingPopulationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void decreaseWorkersCapacity(int amount) {
        assert 0 <= amount && amount <= this.getWorkersCapacity();

        this.workersCapacity = this.workersCapacity - amount;
        this.workers = Math.min(this.workers, this.workersCapacity);
    }

    /**
     * Increase {@link #getPopulation()} by {@value amount}.
     *
     * @param amount
     */
    public void increasePopulation(int amount) {
        assert amount >= 0;

        final int joiningPopulation = Math.min(this.populationCapacity - this.population, amount);
        this.population = this.population + joiningPopulation;
        this.unworkingPopulation = this.unworkingPopulation + joiningPopulation;
    }

    /**
     * Decrease {@link #getPopulation()} by {@value amount}.
     *
     * @param amount
     */
    public void decreasePopulation(int amount) {
        assert amount >= 0;

        this.population = Math.max(0, this.population - amount);
        this.unworkingPopulation = Math.min(this.unworkingPopulation, this.population);
    }

    /**
     * Increase {@link #getPopulationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void increasePopulationCapacity(int amount) {
        assert amount >= 0;

        this.populationCapacity = this.populationCapacity + amount;
    }

    /**
     * Decrease {@link #getPopulationCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void decreasePopulationCapacity(int amount) {
        assert 0 <= amount && amount <= this.getPopulationCapacity();

        this.populationCapacity = this.populationCapacity - amount;
        this.population = Math.min(this.population, this.populationCapacity);
    }

    // Change (Product)
    /**
     * Decrease {@link #getProductsCount()} by {@value amount}.
     *
     * @param amount
     */
    public void consumeProducts(int amount) {
        assert amount >= 0;
        double happpinesBring =(100-impot.tauxTVA)*Math.min(amount,this.productsCount)/30;
        this.happiness+=happpinesBring;
        
        this.productsCount = Math.max(0, this.productsCount - amount);
        
    }

    /**
     * Increase {@link #getProductsCount()} by {@value amount}.
     *
     * @param amount
     */
    public void storeProducts(int amount) {
        assert amount >= 0;

        this.productsCount = Math.min(this.productsCapacity, this.productsCount + amount);
    }

    /**
     * Decrease {@link #getProductsCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void decreaseProductsCapacity(int amount) {
        assert 0 <= amount && amount <= this.getProductsCapacity();

        this.productsCapacity = this.productsCapacity - amount;
        this.productsCount = Math.min(this.productsCount, this.productsCapacity);
    }

    /**
     * Increase {@link #getProductsCapacity()} by {@value amount}.
     *
     * @param amount
     */
    public void increaseProductsCapacity(int amount) {
        assert amount >= 0;

        this.productsCapacity = this.productsCapacity + amount;
    }

    // Change (happiness)
    /**
     * increases happiness by a given amount (can be negative)
     */
    public void increaseHappiness(int amount){
		this.happiness+=amount;
	}

    // Reset
    /**
     * Reset ephemeral resources.
     */
    public void resetEphemerals() {
        this.unworkingPopulation = this.population - getWorkingPopulation();
        this.unconsumedEnergy = this.energyProduction;
        this.unconsumedWater = this.waterProduction;
        this.riotbuilding= getriotbuilding();
        this.eventsetriotbuilding= geteventsetriotbuilding();
        int a=this.careCapacity;
        this.careCapacity=0;
        if (this.population >= 200){
        if (this.population >= a ){
	        	this.illnessRate = Math.min(this.illnessRate + (this.population - a)/this.population*10,100);
	        }else{
	        	this.illnessRate = Math.max(this.illnessRate - (a - this.population )*10/this.population,0);
	        }
        }
        int b=this.PolicestationCapacity;
        this.PolicestationCapacity=0;
        if (this.population >= 200){
	        if (this.population >= b ){
	        	this.securityRate = Math.min(this.securityRate + (this.population - b)/Math.max(b,10)*100,100);
	        }else{
	        	this.securityRate = Math.max(this.securityRate - (b - this.population )*100/Math.max(b,10),0);
	        }
        }
        this.refreshHappiness();
    }

    /**
     * refresh the MatRes Matrix for the next round
     */
	public void refreshMatRes() {
		this.MatRes.refresh();
		
	}
	/**
	 * copy the actual MatRes matrix
	 * @param row
	 * @param column
	 * @param Etat
	 */
	public void setEtatRes(int row,int column,EtatRes[] Etat){
		this.MatRes.setEtatRes(row, column, Etat);
	}
	
	/**
     * returns true if the actual tile is adjacent to an active one according to the MatRes matrix
     */
	public boolean isAdjacentActiveGlobal(TilePosition position){
		return MatRes.isAdjacentActiveGlobal(position);
	}
	

	/**
     * calculates the happiness for the next turn bound to different 
     * factors such as unemployment taxes and others..
     */
	public void calculOfHappiness(){
		double tempo=this.happiness;//-this.getPopulation()*(100-this.getIllnessRate())*((double)(100-this.impot.tauxloc)/10000)*(100-this.getSecurityRate());
		double dimiIll = Math.max(1,this.getIllnessRate());
		double dimiSecu = Math.max(1,100-this.getSecurityRate() );
		double dimiImpot = Math.max(1, this.impot.tauxloc);
		dimiImpot = dimiImpot * dimiImpot;
		tempo = tempo - this.getPopulation()*dimiIll*(dimiImpot)*dimiSecu/100000;
		tempo = tempo - this.getUnworkingPopulation()/10; //le chomage rend malheureux
		tempo-= PolicestationTile.nbrPol*10;
		tempo = tempo/Math.max(1,((double)this.getPopulationCapacity()));
		double tempo2 = (Math.atan(tempo)*100/Math.PI+50);
		this.happiness=  (this.oldHappiness+tempo2)/2;

	}

	/**
     * Transfers happiness to the oldhappiness variable (storage)
     */
	public void refreshHappiness(){
		this.oldHappiness = this.happiness;
		assert 0<=this.oldHappiness && this.oldHappiness<100;
        this.happiness=0;
		
	}
}
