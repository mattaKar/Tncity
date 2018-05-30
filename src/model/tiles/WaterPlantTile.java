package model.tiles;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;

public class WaterPlantTile extends Tile implements Destroyable {

    // Constant
    /**
     * Extra water produces for each new update. In the limit of the capacity
     * {@link #getProductionCapacity()}.
     */
    public final static int EXTRA_WATER_PRODUCTION = 30;

    /**
     * Default value of {@link WaterPlantTile2#getProductionCapacity()}
     */
    public final static int DEFAULT_PRODUCTION_CAPACITY = 210;

    // Implementation
    /**
     * {@link #getProduction()}
     */
    protected int production;

    /**
     * {@link #getProductionCapacity()}
     */
    protected final int productionCapacity;

    /**
     * Evolution state
     */
    protected boolean isDestroyed;
    
    /**
     * Create with default settings.
     */
	public WaterPlantTile() {
		this(WaterPlantTile.DEFAULT_PRODUCTION_CAPACITY);
	}

	/**
     * create with a specified production Capacity
     * 
     */
	public WaterPlantTile(int defaultProductionCapacity) {
		super();
        this.productionCapacity = defaultProductionCapacity;
        this.production = 0;
        this.isDestroyed = false;
        vectReseaux[TypeRes.Eau.numRes()]=EtatRes.PROD;
	}

    // Access
    /**
     * @return Current production.
     */
	public int getProduction() {
		return production;
	}

	/**
     * @return Maximum production.
     */
	public int getProductionCapacity() {
		return productionCapacity;
	}

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 17 + this.production;
        result = result * 17 + this.productionCapacity;
        result = result * 17 + Boolean.hashCode(this.isDestroyed);
        return result;
    }

    // Status
	/**
     * equals return true if the selected object is a WaterPlant Tile, false otherwise
     * 
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof WaterPlantTile && this.equals((WaterPlantTile) o);
    }

    /**
     * @param o
     * @return Is {@value o} equals to this?
     */
    public boolean equals(WaterPlantTile o) {
        return this == o || o.production == this.production && o.productionCapacity == this.productionCapacity && o.isDestroyed == this.isDestroyed;
    }
	
	/**
     * changes the construction state of a building so it is destroyed.
     * 
     */
	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
     * removes the building it is applied upon and changes the city resources given the change
     * 
     */
	@Override
	public void disassemble(CityResources res) {
		if (!this.isDestroyed) {
            res.decreaseWaterProduction(this.productionCapacity);
            this.isDestroyed = true;
        }

	}

	/**
     * while the building is standing up, update increases 
     * it's production each turn before reaching the maximum production
     * 
     */
	@Override
	public void update(CityResources res) {
		if (!this.isDestroyed) {
            // Double production
            final int extraProduction = Math.min(WaterPlantTile.EXTRA_WATER_PRODUCTION, this.productionCapacity - this.production);

            this.production = this.production + extraProduction;
            res.increaseWaterProduction(extraProduction);
        }

	}

}
