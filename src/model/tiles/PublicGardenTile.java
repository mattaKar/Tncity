package model.tiles;

import model.CityResources;

/**
 * A tile intended only to improve the global happiness of the city
 */
public class PublicGardenTile extends Tile implements Destroyable {
	/**
	 * total number of gardens in the city
	 */
	public static int nbrGarden=0;
	
    /**
     * Evolution state
     */
    protected boolean isDestroyed;
	
    /**
     * usual number a garden will bring to the city
     */
	private final static int HAPPINESS_BRING=40;

	//constructor
	/**
     * Creates a regular public garden tile and instantiate the number of garden in the city
     */
	public PublicGardenTile() {
		super();
		PublicGardenTile.nbrGarden=nbrGarden+1;
        this.isDestroyed = false;
	}

	//status
	
	/**
     * returns true if the garden selected is destroyed, false otherwise
     */
	@Override
	public boolean isDestroyed() {
		return this.isDestroyed;
	}
	
    //change
	
	/**
     * Removes the selected tile from the gameboard and updates the number of gardens
     */
	@Override
	public void disassemble(CityResources res) {
		PublicGardenTile.nbrGarden=nbrGarden-1;
		this.isDestroyed=true;

	}
	
	 // Status
	/**
     * equals return true if the selected object is a public garden tile, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof PublicGardenTile && this.equals((PublicGardenTile) o);
    }

    /**
     * updates give (each round) a fixed amount of happiness and a
     *  variable amount given the money spent on public garden maintenance
     */
	@Override
	public void update(CityResources res) {
		if(!this.isDestroyed)
			res.increaseHappiness(HAPPINESS_BRING + res.impot.tauxPP);

	}

}
