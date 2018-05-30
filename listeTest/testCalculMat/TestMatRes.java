package testCalculMat;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculMat.EtatRes;
import calculMat.MatRes;
import calculMat.TypeRes;

/**
 * various test on the implementation of the matrix MatRes
 * @author Guilain
 *
 */
public class TestMatRes {
	MatRes mat1;
	MatRes mat2;
	int height=10;
	int width=20;
	int nbRes=3;
	
	/**
	 * Initialization of two MatRes
	 * the first, mat1, is empty, so full of the enumeration PASDERES
	 * the second, mat2, is filled with a line of RESINACTIF for the network EAU, cut in the beginning with one enumeration PASDERES
	 * 		it is filled with other enumeration : production of water, and some enumeration of other network, in order to check if there are conflicts
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception{
		mat1=new MatRes(height,width);
		mat2=new MatRes(height,width);
		for(int i=0;i<width;i++)
			mat2.setEtatRes(5, i, TypeRes.Eau, EtatRes.RESINACTIF);
		mat2.setEtatRes(5, 1, TypeRes.Eau, EtatRes.PASDERES);
		mat2.setEtatRes(5, 6, TypeRes.Eau, EtatRes.PROD);
		mat2.setEtatRes(3, 8, TypeRes.Elec, EtatRes.RESACTIF);
		mat2.setEtatRes(8, 3, TypeRes.Route, EtatRes.RESACTIF);
	}
	
	/**
	 * check if the matrix mat1 is full of the enumeration PASDERES, and if the function getEtatRes is rigth
	 */
	@Test
	public void testgetEtatRes(){
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                	assertTrue(mat1.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                	assertTrue(mat1.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                	assertTrue(mat1.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
            }
        }
	}

	/**
	 * check if the function setEtatRes change the matrix MatRes
	 */
	@Test
	public void testSetEtatRes(){
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                	if(i==3 && j==8){
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.RESACTIF);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                	}
                	else if(i==8 && j==3){
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.RESACTIF);
                	}
                	else if(i==5){
                		if(j==6){
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PROD);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                		else if(j==1){
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                		else{
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.RESINACTIF);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                	}
                	else{
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                	}
            }
        }
	}
	
	/**
	 * check if the function refresh is correct
	 * the enumeration of the network Elec and Route should be RESINACTIF, and a part of the network Eau should be RESACTIF
	 */
	@Test
	public void testRefresh(){
		mat2.refresh();
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                	if(i==3 && j==8){
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.RESINACTIF);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                	}
                	else if(i==8 && j==3){
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.RESINACTIF);
                	}
                	else if(i==5){
                		if(j==6){
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PROD);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                		else if(j==1){
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                		else if(j==0){
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.RESINACTIF);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                		else{
                			assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.RESACTIF);
                    		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                		}
                	}
                	else{
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Elec)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Eau)==EtatRes.PASDERES);
                		assertTrue(mat2.getEtatRes(i,j,TypeRes.Route)==EtatRes.PASDERES);
                	}
            }
        }
	}
	
	/**
	 * check if the function isActif return a boolean true if the network is PROD or ACTIVE, and return False if the network is PASDERES or INACTIVE
	 */
	@Test
	public void testIsActif(){
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                	if(i==3 && j==8){
                		assertTrue(mat2.isActif(i,j,TypeRes.Elec));
                		assertFalse(mat2.isActif(i,j,TypeRes.Eau));
                		assertFalse(mat2.isActif(i,j,TypeRes.Route));
                	}
                	else if(i==8 && j==3){
                		assertFalse(mat2.isActif(i,j,TypeRes.Elec));
                		assertFalse(mat2.isActif(i,j,TypeRes.Eau));
                		assertTrue(mat2.isActif(i,j,TypeRes.Route));
                	}
                	else if(i==5){
                		if(j==6){
                			assertFalse(mat2.isActif(i,j,TypeRes.Elec));
                    		assertTrue(mat2.isActif(i,j,TypeRes.Eau));
                    		assertFalse(mat2.isActif(i,j,TypeRes.Route));
                		}
                		else if(j==0){
                			assertFalse(mat2.isActif(i,j,TypeRes.Elec));
                    		assertFalse(mat2.isActif(i,j,TypeRes.Eau));
                    		assertFalse(mat2.isActif(i,j,TypeRes.Route));
                		}
                		else{
                			assertFalse(mat2.isActif(i,j,TypeRes.Elec));
                    		assertFalse(mat2.isActif(i,j,TypeRes.Eau));
                    		assertFalse(mat2.isActif(i,j,TypeRes.Route));
                		}
                	}
                	else{
                		assertFalse(mat2.isActif(i,j,TypeRes.Elec));
                		assertFalse(mat2.isActif(i,j,TypeRes.Eau));
                		assertFalse(mat2.isActif(i,j,TypeRes.Route));
                	}
            }
        }
	}
	
	/**
	 * check if the function setEtatRes is correct with a vector as attribute and has the same effect than setEtatRes
	 */
	@Test
	public void testSetEtatRes1(){
		int a=TypeRes.getNbRes();
		EtatRes[]vectReseaux=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux[i]=EtatRes.PROD;
		int row=6;
		int column=15;
		mat1.setEtatRes(row, column, vectReseaux);
		assertTrue(mat1.getEtatRes(row, column, TypeRes.Eau)==EtatRes.PROD);
		assertTrue(mat1.getEtatRes(row, column, TypeRes.Elec)==EtatRes.PROD);
		assertTrue(mat1.getEtatRes(row, column, TypeRes.Route)==EtatRes.PROD);

	}
	
	/**
	 * check if the function isAdjacentActiveGlobal return a boolean true if all of networks are PROD or ACTIVE, and return False if networks are PASDERES or INACTIVE
	 */
	@Test
	public void testIsAdjacentActifGlobal(){
		int a=TypeRes.getNbRes();
		EtatRes[]vectReseaux=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux[i]=EtatRes.PROD;
		mat1.setEtatRes(1, 0, vectReseaux);
		assertTrue(mat1.isAdjacentActiveGlobal(1, 1));
		assertFalse(mat1.isAdjacentActiveGlobal(1, 2));
	}
}
