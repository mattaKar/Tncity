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

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;
import model.TilePosition;


/**
 * Abstract superclass for all tiles.
 */
public abstract class Tile {

	/**
	 * every tile got a specified vector giving it's connection to each network(road,power,water)
	 * 
	 */
	protected EtatRes[] vectReseaux;
	
	/**
	 * position of the tile on the map (coordinates).
	 */
	
	protected TilePosition position;
	
	/**
     * Creates a new vector with a not connected to network information when a tile is constructed
     * 
     */
	public Tile(){
		int a=TypeRes.getNbRes();
		vectReseaux=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux[i]=EtatRes.PASDERES;
	}
    // Change
    /**
     * Go to the next state.
     *
     * @param res
     */
    public abstract void update(CityResources res);
    
    /**
     * return the state of the network of the given tile
     * 
     */
	public EtatRes[] getVectReseaux() {
		return vectReseaux;
	}
	
	/**
     * returns the coordinates of the given tile.
     * 
     */
	public TilePosition getCoord() {
		return this.position;
	}
	
	/**
     * changes the coordinates of a given tile to typed ones.
     * 
     */
	public void setCoord(int row, int column) {
		this.position=new TilePosition(row,column);
	}

}
