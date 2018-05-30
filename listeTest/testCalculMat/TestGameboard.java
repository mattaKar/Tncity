package testCalculMat;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import calculMat.EtatRes;
import calculMat.TypeRes;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.GameBoard;
import model.tiles.CityHall;
import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.RoadTile;
import model.tiles.ShoreTile;
import model.tiles.WaterTile;
import model.tools.RoadTool;
import model.tools.WaterPlantConstructionTool;
import model.tools.CityHallTool;
import model.tools.PowerPlantConstructionTool;

/**
 * a list of Test to check if the GameBoard accept the MatRes
 * @author guilain
 *
 */
public class TestGameboard {

	GameBoard mundo;
	int heigth;
	int width;
	LocalizedTexts text;
	EtatRes[] vectReseaux;
	
	/**
	 * a GameBoard is built, with the first Tile as GrassTile, in order to build on this tile
	 */
	@Before
	public void setUp(){
		heigth=10;
		width=20;
		text= new UKTexts();
		mundo = new GameBoard(heigth,width,text);
		int a=TypeRes.getNbRes();
		vectReseaux=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux[i]=EtatRes.PASDERES;
		mundo.setSelectedTool(new RoadTool());
		for(int row=0;row<5;row++){
			for(int column=0;column<5;column++){
				mundo.setTile(row, column, GrassTile.getDefault());
			}
		}
	}
	
	/**
	 * check if the first Tile have no effect on the networks
	 */
	@Test
	public void verifResTile(){
		for (int i = 0; i < heigth; i++) {
			for(int j=0; j<width;j++){
				for(int k=0;k<3;k++)
					assertTrue(mundo.getTile(i, j).getVectReseaux()[k]==vectReseaux[k]);
			}
		}
	}

	/**
	 * check if the build of buildings on the GameBoard have the good interaction with the matrix of networks
	 */
	@Test
	public void verifResProd(){
		mundo.setSelectedTool(new CityHallTool());
		mundo.effectTile(1, 0);
		mundo.setSelectedTool(new PowerPlantConstructionTool());
		mundo.effectTile(0, 1);
		mundo.setSelectedTool(new WaterPlantConstructionTool());
		mundo.effectTile(0, 2);
		mundo.setSelectedTool(new RoadTool());
		mundo.effectTile(1, 1);
		mundo.effectTile(1, 2);
		int a=TypeRes.getNbRes();
		EtatRes[] vectReseaux2=new EtatRes[a];
		for(int i=0;i<a;i++)
			vectReseaux2[i]=EtatRes.RESINACTIF;
		
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 0,TypeRes.Route),EtatRes.PROD);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(0, 1,TypeRes.Elec),EtatRes.PROD);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(0, 2,TypeRes.Eau),EtatRes.PROD);
		
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Eau),EtatRes.RESINACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Elec),EtatRes.RESINACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Route),EtatRes.RESINACTIF);
		mundo.getResources().refreshMatRes();
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Eau),EtatRes.RESACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Elec),EtatRes.RESACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 1,TypeRes.Route),EtatRes.RESACTIF);
		
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 2,TypeRes.Eau),EtatRes.RESACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 2,TypeRes.Elec),EtatRes.RESACTIF);
		assertEquals(mundo.getResources().getMatRes().getEtatRes(1, 2,TypeRes.Route),EtatRes.RESACTIF);
		
	}
	
	
}
