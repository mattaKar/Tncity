package test.model.tiles;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.CityResources;
import model.tiles.ConstructionState;
import model.tiles.IndustrialTile;


/**
 * list of test checking the class Industrial
 * @author Guilain
 *
 */
public class IndustrialTileTest {

	int heigth=10;
    int width=20;
    
    /**
     * check the constructor
     */
    @Test
	public void Testinit() {
		IndustrialTile res = new IndustrialTile();
        Assert.assertEquals(IndustrialTile.DEFAULT_WORKERS_CAPACITY, res.getworkersCapacity());
        res = new IndustrialTile(10);
        assertEquals(10, res.getworkersCapacity());
	}
    
    /**
     * check if the function evolve build the building, and check the effect on the GameBoard
     */
    @Test
    public void testEvolve() {
    	IndustrialTile ppt = new IndustrialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        resources.increasePopulationCapacity(extraProduction);
        resources.increasePopulation(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD); 
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        
        int initialValue = resources.getProductsCount();
        int producCap = resources.getProductsCapacity();
        ppt.evolve(resources);
        assertEquals(ppt.getState(),ConstructionState.BUILT);
        assertEquals(initialValue + ppt.getProduction(resources), resources.getProductsCount());
        assertEquals(producCap + ppt.getproductsCapacity(),resources.getProductsCapacity());
    }
    
    /**
     * check that the building can't be build if there is no access to various network
     * three Tile are create with access to all networks except one
     * the test check that the building cannot evolve
     */
    @Test
    public void testEvolveEnergieWaterRoad() {
    	IndustrialTile ppt = new IndustrialTile();
        CityResources resources1 = new CityResources(100, heigth, width);	//manque electricité
        CityResources resources2 = new CityResources(100, heigth, width);	//manque eau
        CityResources resources3 = new CityResources(100, heigth, width);	//manque route
        int extraProduction = 100;
        int initialValue = resources1.getProductsCount();
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
        assertEquals(resources1.getProductsCount(),initialValue);
        
        ppt.evolve(resources2);
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        assertEquals(resources2.getProductsCount(),initialValue);
        
        ppt.evolve(resources3);
        assertEquals(ppt.getState(),ConstructionState.UNDER_CONSTRUCTION);
        assertEquals(resources3.getProductsCount(),initialValue);
    }
    
    /**
     * check if the function isEquals return True between two instance of the Tile in two various GameBoard
     */
    @Test
    public void testDisassemble() {
    	IndustrialTile ppt = new IndustrialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        resources.increasePopulationCapacity(extraProduction);
        resources.increasePopulation(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);    
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        
        ppt.evolve(resources);
        assertEquals(ppt.getState(),ConstructionState.BUILT);
        int initialCapacity = resources.getProductsCount();
        assertEquals(ppt.maxJoiningWorkers,resources.getWorkingPopulation());
        ppt.disassemble(resources);
        assertEquals(ppt.getState(),ConstructionState.DESTROYED);
        assertEquals(Math.max(0, initialCapacity - ppt.getproductsCapacity()), resources.getProductsCount());
        assertEquals(resources.getUnworkingPopulation(),resources.getPopulation());
    }
    
    
	/**
	 * check the permanent effect of the building, giving by the function Update
	 */
    @Test
    public void testIsEquals() {
    	IndustrialTile ppt1 = new IndustrialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        resources.increasePopulationCapacity(extraProduction);
        resources.increasePopulation(extraProduction);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);  
        ppt1.setCoord(0, 0);
        ppt1.evolve(resources);
        ppt1.disassemble(resources);
        IndustrialTile ppt2 = new IndustrialTile();
        ppt2.setCoord(0, 2);
        ppt2.evolve(resources);
        ppt2.disassemble(resources);
        assertTrue(ppt1.equals(ppt2));
    }
    
    
    @Test
    public void testUpdate(){
    	IndustrialTile ppt = new IndustrialTile();
        CityResources resources = new CityResources(100, heigth, width);
        int extraProduction = 100;
        resources.increaseEnergyProduction(extraProduction);
        resources.increaseWaterProduction(extraProduction);
        resources.increasePopulationCapacity(extraProduction);
        resources.increasePopulation(extraProduction);
        ppt.setCoord(0, 0);
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Route, EtatRes.PROD);    
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Elec, EtatRes.PROD);   
        resources.getMatRes().setEtatRes(0, 1, TypeRes.Eau, EtatRes.PROD);  
        
        ppt.evolve(resources);
        int initialValue = resources.getProductsCount();
        ppt.update(resources);
        assertEquals(initialValue + ppt.getProduction(resources), resources.getProductsCount());
    }
    
}
