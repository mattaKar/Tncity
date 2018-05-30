package test.model.tiles;

import org.junit.Assert;
import org.junit.Test;

import model.CityResources;
import model.tiles.WaterPlantTile;

/**
 * list of test checking the class WaterPlant
 * @author Guilain
 *
 */
public class WaterPlantTileTest {

	int heigth=10;
    int width=20;
	
    /**
     * check the constructor
     */
    @Test
    public void testInit() {
        WaterPlantTile ppt = new WaterPlantTile();
        Assert.assertEquals(WaterPlantTile.DEFAULT_PRODUCTION_CAPACITY, ppt.getProductionCapacity());
        ppt = new WaterPlantTile(10);
        Assert.assertEquals(10, ppt.getProductionCapacity());
    }
    
    /**
	 * check the permanent effect of the building, giving by the function Update
	 */
    @Test
    public void testUpdate() {
        WaterPlantTile ppt = new WaterPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        int initialValue = resources.getWaterProduction();
        ppt.update(resources);
        Assert.assertEquals(initialValue + ppt.getProduction(), resources.getWaterProduction());
    }
    
    /**
     * check if the function disassemble remove the Tile and remove all of his benefits
     */
    @Test
    public void testDisassemble() {
        WaterPlantTile ppt = new WaterPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt.update(resources);
        int initialProduction = resources.getWaterProduction();
        ppt.disassemble(resources);
        Assert.assertEquals(Math.max(0, initialProduction - ppt.getProductionCapacity()), resources.getWaterProduction());
    }
    
    /**
     * check if the result of disassemble give a building with the state isDestroyed
     */
    @Test
    public void testIsDestroyed() {
        WaterPlantTile ppt = new WaterPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt.disassemble(resources);
        Assert.assertTrue( ppt.isDestroyed());
    }
    
    /**
     * check if the function isEquals return True between two instance of the Tile in two various GameBoard
     */
    @Test
    public void testIsEquals() {
        WaterPlantTile ppt1 = new WaterPlantTile();
        CityResources resources = new CityResources(100, heigth, width);
        ppt1.update(resources);
        ppt1.disassemble(resources);
        WaterPlantTile ppt2 = new WaterPlantTile();
        ppt2.update(resources);
        ppt2.disassemble(resources);
        Assert.assertTrue( ppt1.equals(ppt2));
    }

}
