package ui;

import model.CityResources;
import model.GameBoard;
import model.tiles.HospitalTile;
import model.tiles.PolicestationTile;
import model.tiles.PublicGardenTile;

/**
 * Impot (french for taxes) is a class bound to the cityhall and other resources, it uses the values chosen in the 
 * GestionImpot display to set them to the actual values in game
 */
public class Impot {
	
	// Constants
	
	/**
	 * variable handling the local taxes applied on the number of inhabitants (mainly residences)
	 */
	public int impLoc;
	
	/**
	 * variable bound to the imploc varying between 0 and 100
	 */
	public int tauxloc;
	
	/**
	 * variable handling tax on products sold by the shops (commercial tiles)
	 */
	public int TVA;
	
	
	/**
	 * variable bound to the TVA varying between 0 and 100
	 */
	public int tauxTVA;
	
	/**
	 * variable handling budget spent on hospitals 
	 */
	public int impSan;
	
	/**
	 * variable bound to the impSan varying between 0 and 100
	 */
	public int tauxHosp;
	
	/**
	 * variable handling budget spent on police stations
	 */
	public int impSecu;
	
	/**
	 * variable bound to the impSecu varying between 0 and 100
	 */
	public int tauxSecu;
	
	/**
	 * variable handling budget spent on public gardens
	 */
	public int impPP;
	
	/**
	 *  variable bound to the impPP varying between 0 and 100
	 */
	public int tauxPP;
	
	/**
	 * variable gathering the different earnings
	 */
	public int TotalRec;
	
	/**
	 *  variable gathering the different losses
	 */
	public int TotalDep;
	
	
	/**
	 *  Impot constructor that set the base taxes for the different variables 
	 */
	public Impot(){

		this.impLoc= 0;
		this.tauxloc=20;

		this.TVA=0;
		this.tauxTVA=20;
		
		this.impSan=0;
		this.tauxHosp=50;
		
		this.impSecu=0;
		this.tauxSecu=50;
		
		this.impPP=0;
		this.tauxPP=40;
		

		this.TotalRec=impLoc+TVA;
		this.TotalDep=impSan+impSecu;
	
	}
	
	
	/**
	 *  RefreshImport gives the different taxes percentage given what was set by the player
	 */
	public void refreshImpot(GameBoard w){
		
		 int nbrHab=w.getPopulation();
		 this.impLoc= (int)((tauxloc*nbrHab)/100);
		
		
		int PrdVendu=w.getProducts();
		this.TVA=(int)((tauxTVA*PrdVendu)/100);
		
		
		int nbrHop=HospitalTile.nbrHosp;
		this.impSan=tauxHosp*nbrHop;
		
		
		int nbrPol=PolicestationTile.nbrPol;
		this.impSecu=tauxSecu*nbrPol;
		
		
		int nbrPP=PublicGardenTile.nbrGarden;
		this.impPP=tauxPP*nbrPP;
		
		
		this.TotalRec=impLoc+TVA;
		this.TotalDep=impSan+impSecu+impPP;
		
	}
	
	
}
