package testCalculMat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculMat.EtatRes;
import calculMat.TypeRes;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.ShoreTile;
import model.tiles.Tile;

/**
 * a list of test to check the vector vectReseaux
 * @author Guilain
 *
 */
public class TestTileReseaux {

	
	Tile grass;
	Tile forest;
	Tile shore;
	int a;
	EtatRes[] vectReseaux;
	
	/**
	 * a vector vectReseaux is initialized with the enumeration PASDERES for the three networks
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception{
		grass = GrassTile.getDefault();
		forest = ForestTile.getDefault();
		shore = ShoreTile.getDefault();
		a=TypeRes.getNbRes();
		vectReseaux=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux[i]=EtatRes.PASDERES;
	}
		
	/**
	 * this test check that all initial tile have no effect on the networks
	 */
	@Test
	public void testVectRes(){
		assertEquals(3,a);
		for(int i=0;i<a;i++){
			assertTrue(grass.getVectReseaux()[i]==vectReseaux[i]);
			assertTrue(forest.getVectReseaux()[i]==vectReseaux[i]);
			assertTrue(shore.getVectReseaux()[i]==vectReseaux[i]);
		}
	}

}
