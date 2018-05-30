package test.model.tiles;
import org.junit.Test;

import org.junit.Assert;

import model.CityResources;
import model.tiles.PowerPlantTile;

/**
 * list of test checking the class PowerPlant
 * @author Guilain
 *
 */
public class PowerPlantTileTest {
    int heigth=10;
    int width=20;
	
    /**
     * check the constructor
     */
    @Test
    public void testInit() {
        PowerPlantTile ppt = new PowerPlantTile();
        Assert.assertEquals(PowerPlantTile.DEFAULT_PRODUCTION_CAPACITY, ppt.getProductionCapacity());
        ppt = new PowerPlantTile(10);
        Assert.assertEquals(10, ppt.getProductionCapacity());
    }
    
    /**
	 * check the permanent effect of the building, giving by the function Update
	 */
    @Test
    public void testUpdate() {
        PowerPlantTile ppt = new PowerPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        int initialValue = resources.getEnergyProduction();
        ppt.update(resources);
        Assert.assertEquals(initialValue + ppt.getProduction(), resources.getEnergyProduction());
    }
    
    /**
     * check if the function disassemble remove the Tile and remove all of his benefits
     */
    @Test
    public void testDisassemble() {
        PowerPlantTile ppt = new PowerPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt.update(resources);
        int initialProduction = resources.getEnergyProduction();
        ppt.disassemble(resources);
        Assert.assertEquals(Math.max(0, initialProduction - ppt.getProductionCapacity()), resources.getEnergyProduction());
    }
    
    /**
     * check if the result of disassemble give a building with the state isDestroyed
     */
    @Test
    public void testIsDestroyed() {
        PowerPlantTile ppt = new PowerPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt.disassemble(resources);
        Assert.assertEquals(true, ppt.isDestroyed());
    }
    
    /**
     * check if the function isEquals return True between two instance of the Tile in two various GameBoard
     */
    @Test
    public void testIsEquals() {
        PowerPlantTile ppt1 = new PowerPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt1.update(resources);
        ppt1.disassemble(resources);
        PowerPlantTile ppt2 = new PowerPlantTile();
        ppt2.update(resources);
        ppt2.disassemble(resources);
        Assert.assertEquals(true, ppt1.equals(ppt2));
    }
    
    
}
