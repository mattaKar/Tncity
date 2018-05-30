package test.model.tiles;



import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;
import model.tiles.ConstructionState;
import model.tiles.ResidentialTile;


/**
 * list of test checking the class Residential
 * @author Guilain
 *
 */
public class ResidentialTileTest {
	
    int heigth=10;
    int width=20;
    
    /**
     * check the constructor
     */
    @Test
	public void Testinit() {
		ResidentialTile res = new ResidentialTile();
        assertEquals(ResidentialTile.DEFAULT_INHABITANTS_CAPACITY, res.getInhabitantsCapacity());
        res = new ResidentialTile(10);
        assertEquals(10, res.getInhabitantsCapacity());
	}
	
    
    /**
     * check if the function evolve build the building, and check the effect on the GameBoard
     */
    @Test
    public void testEvolve() {
        ResidentialTile ppt = new ResidentialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);        
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        int initialValue = resources.getPopulation();
        ppt.evolve(resources);
        assertEquals(ppt.getState(),ConstructionState.BUILT);
        assertEquals(initialValue + ppt.getInhabitants(resources), resources.getPopulation());
    }
    
    
    /**
     * check that the building can't be build if there is no access to various network
     * three Tile are create with access to all networks except one
     * the test check that the building cannot evolve
     */
    @Test
    public void testEvolveEnergieWaterRoad() {
    	ResidentialTile ppt = new ResidentialTile();
        CityResources resources1 = new CityResources(100, heigth, width);	//manque electricité
        CityResources resources2 = new CityResources(100, heigth, width);	//manque eau
        CityResources resources3 = new CityResources(100, heigth, width);	//manque route
        int extraProduction = 100;
        int initialValue = resources1.getPopulation();
        ppt.setCoord(0, 0);
        resources1.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);        
        resources2.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);        
        resources3.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.RESINACTIF);
        
        resources2.increaseEnergyProduction(extraProduction);
        resources3.increaseEnergyProduction(extraProduction);
        
        resources1.increaseWaterProduction(extraProduction);
        resources3.increaseWaterProduction(extraProduction);
        
        ppt.evolve(resources1);
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        assertEquals(resources1.getPopulation(),initialValue);
        
        ppt.evolve(resources2);
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        assertEquals(resources2.getPopulation(),initialValue);
        
        ppt.evolve(resources3);
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        assertEquals(resources3.getPopulation(),initialValue);
    }
    /**
     * check if the function disassemble remove the Tile and remove all of his benefits
     */
    @Test
    public void testDisassemble() {
    	ResidentialTile ppt = new ResidentialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);    
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        
        ppt.evolve(resources);
        int initialCapacity = resources.getPopulationCapacity();
        
        ppt.disassemble(resources);
        assertEquals(ppt.getState(),ConstructionState.DESTROYED);
        assertEquals(Math.max(0, initialCapacity - ppt.getInhabitantsCapacity()), resources.getPopulationCapacity());
    }
    
    
    /**
     * check if the function isEquals return True between two instance of the Tile in two various GameBoard
     */
    @Test
    public void testIsEquals() {
        ResidentialTile ppt1 = new ResidentialTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt1.update(resources);
        ppt1.disassemble(resources);
        ResidentialTile ppt2 = new ResidentialTile();
        ppt2.update(resources);
        ppt2.disassemble(resources);
        Assert.assertTrue(ppt1.equals(ppt2));
    }
	
	/**
	 * check the permanent effect of the building, giving by the function Update
	 */
	@Test
	public void testUpdate(){
		ResidentialTile ppt = new ResidentialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);    
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        ppt.evolve(resources);
        int initialValue = resources.getPopulation();
        int currentInhabitants = ppt.getInhabitants(resources);
        ppt.update(resources);
        assertEquals(initialValue + ppt.getInhabitants(resources) - currentInhabitants, resources.getPopulation());
	}
	
	
}
