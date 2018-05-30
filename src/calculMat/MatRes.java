package calculMat;

import java.util.ArrayList;

import model.TilePosition;
/**
 * Matrix containing data about networks
 * @author guilain
 *
 */
public class MatRes {
	/**
	 * the Matrix itself that will contain all the data about networks
	 */
	private EtatRes[][][] MatricedesReseaux;	
	
	/**
	 * setting the size of the matrix
	 */
	private int heigth;
	private int width;
	
	/**
	 * setting the number of networks we work with
	 */
	private static final int nbRes=TypeRes.nbRes; 
	
/**
 * creating the matrix without networks
 * @param height 
 * @param width 
 */
	public MatRes(int height,int width) {		
		this.heigth=height;
		this.width=width;
        this.MatricedesReseaux=new EtatRes[height][width][nbRes];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for(int k=0;k<nbRes;k++){
                	this.MatricedesReseaux[i][j][k]=EtatRes.PASDERES;
                }
            }
        }	}
	
	/**
	 * gives the network state in the given tile
	 */
	public EtatRes getEtatRes(int row,int column,TypeRes type){
		assert row>=0 && row<heigth && column>=0 && column<width;
		return MatricedesReseaux[row][column][type.numRes()];
	}
	

	/** 
	 * modify the network on the given tile
	 * @param row
	 * @param column
	 * @param type
	 * @param newEtat
	 */
	public void setEtatRes(int row,int column,TypeRes type,EtatRes newEtat){
		MatricedesReseaux[row][column][type.numRes()]=newEtat;
	}
	
	/**
	 * copy of the network vector of each tile
	 * @param row
	 * @param column
	 * @param newEtat network Vector of each tile
	 */
	public void setEtatRes(int row,int column, EtatRes[] newEtat){
		assert row>=0 && row<heigth;
		assert column>=0 && column<width;
		for(int i=0;i<TypeRes.getNbRes();i++)
			MatricedesReseaux[row][column][i]=newEtat[i];
	}
	
	/**
	 * checks the networks is PROD or RESACTIF in a given tile
	 */
	public boolean isActif(int row,int column,TypeRes type){		
		EtatRes etat=getEtatRes(row,column,type);
		return (etat==EtatRes.RESACTIF)||(etat==EtatRes.PROD);
	}
	/**
	 * updates the MatRes, sets to RESINACTIF tiles that are RESACTIF and that 
	 * are not connected to producing tiles
	 */
	public void refresh(){
		ArrayList<int[]> listProd;
		listProd=new ArrayList<int[]>();
		for (int row = 0; row < heigth; row++) {
            for (int column = 0; column < width; column++) {
                for(int k=0;k<nbRes;k++){
                	if (MatricedesReseaux[row][column][k]==EtatRes.RESACTIF)
                		MatricedesReseaux[row][column][k]=EtatRes.RESINACTIF;
                	else if(MatricedesReseaux[row][column][k]==EtatRes.PROD){
                		int[] temp=new int[3];
                		temp[0]=row;
                		temp[1]=column;
                		temp[2]=k;
                		listProd.add(temp);
                	}
                }
            }
        }
		int n=listProd.size();
		for(int m=0;m<n;m++){
			int[] temp;
			temp=(int[]) listProd.get(m);
			this.mult(temp[0], temp[1], temp[2]);
		}
		
	}
	
	/**
	 * goes across the board to gather information for the MatRes
	 */
	public void mult(int i,int j, int k){		
		if(i>0 && this.MatricedesReseaux[i-1][j][k]==EtatRes.RESINACTIF){
			MatricedesReseaux[i-1][j][k]=EtatRes.RESACTIF;
			mult(i-1,j,k);
		}
		if(i<heigth-1 && this.MatricedesReseaux[i+1][j][k]==EtatRes.RESINACTIF){
			MatricedesReseaux[i+1][j][k]=EtatRes.RESACTIF;
			mult(i+1,j,k);
		}	
		if(j>0 && this.MatricedesReseaux[i][j-1][k]==EtatRes.RESINACTIF){
			MatricedesReseaux[i][j-1][k]=EtatRes.RESACTIF;
			mult(i,j-1,k);
		}
		if(j<width-1 && this.MatricedesReseaux[i][j+1][k]==EtatRes.RESINACTIF){
			MatricedesReseaux[i][j+1][k]=EtatRes.RESACTIF;
			mult(i,j+1,k);
		}	
	}
	
	/**
	 * checks if a building got an access to the needed resources
	 */
	public boolean isAdjacentActive(int row,int column,TypeRes type){
		boolean res=false;
		if(row >0){
			res=res||isActif(row-1,column,type);
		}
		if(row <heigth-1){
			res=res||isActif(row+1,column,type);
		}
		if(column >0){
			res=res||isActif(row,column-1,type);
		}
		if(column <heigth-1){
			res=res||isActif(row,column+1,type);
		}
		return res;
	}
	/**
	 * checks if a building got an access to the needed resources
	 */
	public boolean isAdjacentActiveGlobal(int row,int column){
		boolean res=true;
		for(int i=0;i<TypeRes.nbRes;i++)
			res=res && isAdjacentActive(row,column,TypeRes.intToTypeRes(i));
		return res;
	}
	
	/**
	 * same as the regular one, only the coordinates are given in the form of a Tileposition
	 */
	public boolean isAdjacentActiveGlobal(TilePosition coord){
		//return isAdjacentActive(coord.getRow(),coord.getColumn(),TypeRes.Route);
		return isAdjacentActiveGlobal(coord.getRow(),coord.getColumn());
	}
	
	/**
	 * checks if a building has access to all the needed resources
	 * @param condition :if needed, roads, water, power...
	 */
	public boolean isAdjacentActiveGlobal(int row,int column,boolean[] condition){
		boolean res=false;
		for(int i=0;i<TypeRes.nbRes;i++)
			// il s'agit d'une implication condition=>isAdjActif
			res=res && (!condition[i] || (condition[i]&&isAdjacentActive(row,column,TypeRes.intToTypeRes(i))));
		return res;
	}
}
