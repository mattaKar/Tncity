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

package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import ui.Impot;
import ui.PropertiesView;
import dialog.DebtMessage;
import dialog.GestionImpots;
import dialog.NoNeedHospital;
import dialog.NoNeedPolicesation;
import dialog.WarningMessage;
import localization.LocalizedTexts;
import model.difficulty.DifficultyLevel;
import model.event.Event;
import model.event.EventFactory;
import model.tiles.Evolvable;

import model.tiles.ForestTile;
import model.tiles.GrassTile;
import model.tiles.WaterTile;
import model.tiles.ShoreTile;

import model.tiles.ForestTile;

import model.tiles.CityHall;
import model.tiles.Tile;

import model.tools.BulldozerTool;
import model.tools.CityHallTool;
import model.tools.PowerPlantConstructionTool;
import model.tools.PublicGardenConstructionTool;
import model.tools.ResidentialZoneDelimiterTool;
import model.tools.CommercialZoneDelimiterTool;
import model.tools.IndustrialZoneDelimiterTool;
import model.tools.RoadTool;
import model.tools.HospitalConstructionTool;
import model.tools.Tool;
import model.tools.WaterPlantConstructionTool;
import model.tools.PolicestationConstructionTool;

import calculMat.MatRes;
import calculMat.TypeRes;

/**
 *  GameBoard is one of the main component of the program, 
 *  it contains the different informations regarding which buildings are built were
 */
public class GameBoard extends Observable {

    // Constant
	/**
	 *  sets the difficulty lever to standard
	 */
    public final static DifficultyLevel DEFAULT_DIFFICULTY = DifficultyLevel.STANDARD_LEVEL;

    /**
	 *  initialize the tile position in the top left corner
	 */
    public final static TilePosition DEFAULT_SELECTED_LOCATION = new TilePosition(0, 0);

    /**
	 *  initialize the selected tool to the first one (bulldozer)
	 */
    public final static int DEFAULT_SELECTED_TOOL = 0;

    /**
	 *  sets the number of buildings capable of evolving at once to 5
	 */
    public final static int MAX_HANDLED_EVOLUTIONS = 5;

    /**
	 *  sets the null message to nothing
	 */
    public final static String NOTHING_MESSAGE = "";

    /**
	 *  sets an int counter to 0
	 */
    public final static AtomicInteger ROUNDCOUNTER = new AtomicInteger(0);
    
    /**
	 *  sets the properties for a second counter
	 */
    private PropertiesView vi;
    
    /**
     * allows to create only one windows about needed hospital and policestation
     */
    
    private boolean variableSeuil=true;
    
    /**
     * allows to create only one windows DebtMessage when the currency resources are under 0.    
     */
    private boolean variableDebt=false;

    // Implementation
    /**
     * Map of the world.
     */
    private final Tile[][] tiles;

    /**
     * Available tools.
     */

    private final List<Tool> tools;

    /**
     * {@link #getSelectedTool()}
     */
    private Tool selectedTool;

    /**
     * {@link #getSelectedTile()}
     */
    private Tile selectedTile;

    /**
     * Pending evolutions.
     */
    private final Queue<Evolvable> pendingEvolutions;

    /**
     * Events to be applied to the world at the next refresh.
     */
    private final List<Event> pendingEventsList;

    /**
     * Available money.
     */
    private CityResources resources;    

    /**
     * Status message.
     */
    private String message;

    /**
     * {@link #getTexts()}
     */
    private final LocalizedTexts texts;

    // Creation
    /**
     * Create a rectangle world with {@value height * width} tiles.
     * the world is randomly generated using a random matrix with the same dimension 
     * than the ones used for the board. this allows the creation of oceans and forest 
     * with an acceptably organic look at this scale. A variety of filters are then applied in order for the map
     * to make sense (e.g. having a lot of shores adjacent to each other makes no sense).
     * Although, some filters will create some issues when they are applied so there might be graphic 
     * glitches remaining on the map.
     *
     * @param height
     *            - {@link #getHeight()}
     * @param width
     *            - {@link #getWidth()}
     * @param difficulty
     *            - Game difficulty level.
     * @param texts
     *            - {@link #getTexts()}
     */
    public GameBoard(int height, int width, DifficultyLevel difficulty, LocalizedTexts texts) {
        assert width > 0 && height > 0 : "Dimensions incorrectes";
        int tableau[][] = new int[height][width] ;
        Random randomGenerator = new Random();
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		tableau[i][j]=randomGenerator.nextInt(1000);
        	}
        }
        
        //next instructions allows the expansion of water pools
        int tableauterrain[][] = new int[height][width] ;
        this.tiles = new Tile[height][width];
        for (int z=0;z<8;z++){
        	for (int i=1;i<height-1;i++){
            	for (int j=1;j<width-1;j++){
            		if(tableau[i][j]<15){
            			tableau[i+1][j]=tableau[i+1][j]/8*4;
            			tableau[i-1][j]=tableau[i-1][j]/8*4;
            			tableau[i][j+1]=tableau[i][j+1]/8*4;
            			tableau[i][j-1]=tableau[i][j-1]/8*4;
            			tableau[i+1][j+1]=tableau[i+1][j+1]/8*4;
            			tableau[i-1][j+1]=tableau[i-1][j+1]/8*4;
            			tableau[i-1][j-1]=tableau[i-1][j-1]/8*4;
            			tableau[i+1][j-1]=tableau[i+1][j-1]/8*4;

            		}
            	}
            }
        }
        
      //these instructions set the different tiles to the terrain tile depending on the number in the matrix
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		if (tableau[i][j]<15){
        			if(tableau[Math.min(i+1,height-1)][j]>15 || tableau[Math.max(i-1,0)][j]>15 || tableau[i][Math.min(j+1,width-1)]>15 
        					|| tableau[i][Math.max(j-1,0)]>15 || tableau[Math.min(i+1,height-1)][Math.min(j+1,width-1)]>15 
        					||tableau[Math.max(i-1,0)][Math.min(j+1,width-1)]>15  || tableau[Math.min(i+1,height-1)][Math.max(j-1,0)]>15 || tableau[Math.max(i-1,0)][Math.max(j-1,0)]>15 ){
        				tableauterrain[i][j] = 3;
        			} else {
        				tableauterrain[i][j] = 2;
        			}
        		} if (tableau[i][j]<100 && tableau[i][j]>=15){
        			tableauterrain[i][j] = 1;
        		} if ( tableau[i][j]>=100){
        			tableauterrain[i][j] = 0;
        		}
        	}
        }
        

       //next instructions removes isolated shores a.k.a: those with 3 neighboring land tiles
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		if(tableauterrain[i][j]==3){
        			int a=0;
        			if(i!=height-1){
        				if (tableauterrain[i+1][j]<2 ){
        					a++;
        				}
        			}else{
        				a++;
        			}
        		
        			if(i!=0){
        				if (tableauterrain[i-1][j]<2 ){
        					a++;
        				}
        			}else{
        				a++;
        			}
        			if(j!=0){
        				if (tableauterrain[i][j-1]<2 ){
        					a++;
        				}
        			}else{
        				a++;
        			}
        			if(j!=width-1){
        				if (tableauterrain[i][j+1]<2 ){
        					a++;
        				}
        			} else{
        				a++;
        			}
        			if (a>=3){
        				tableauterrain[i][j]=0;
        			}
        		}
        		
        		
        	}
        }	
        
       //next instructions suppresses 4 blocs shores except on the sides of the board
        for (int i=2;i<height-1;i++){
        	for (int j=2;j<width-1;j++){
        		if (tableauterrain[i][j]==3 && tableauterrain[i-1][j]==3 && tableauterrain[i][j-1]==3 && tableauterrain[i-1][j-1]==3 ){
        			if (tableauterrain[i-2][j]<=1 && tableauterrain[i-2][j-1]<=1 ){
        				tableauterrain[i-1][j]=0;
        				tableauterrain[i-1][j-1]=0;
        			}
        			if (tableauterrain[i+1][j]<=1 && tableauterrain[i+1][j-1]<=1 ){
        				tableauterrain[i][j]=0;
        				tableauterrain[i][j-1]=0;
        			}
        			if (tableauterrain[i][j-2]<=1 && tableauterrain[i-1][j-2]<=1 ){
        				tableauterrain[i-1][j-1]=0;
        				tableauterrain[i][j-1]=0;
        			}
        			if (tableauterrain[i][j+1]<=1 && tableauterrain[i-1][j+1]<=1 ){
        				tableauterrain[i-1][j]=0;
        				tableauterrain[i][j]=0;
        			}
        		}
        	}
        }
      //next instructions suppresses 4 blocs shores on the sides of the board
        for (int j=1;j<width;j++){
        	if (tableauterrain[1][j]==3 && tableauterrain[0][j]==3 && tableauterrain[1][j-1]==3 && tableauterrain[0][j-1]==3){
        		tableauterrain[1][j]=3;
        		tableauterrain[0][j]=0;
        		tableauterrain[1][j-1]=3;
        		tableauterrain[0][j-1]=0;
        	}
        }
        for (int j=1;j<width;j++){
        	if (tableauterrain[height-2][j]==3 && tableauterrain[height-1][j]==3 && tableauterrain[height-2][j-1]==3 && tableauterrain[height-1][j-1]==3){
        		tableauterrain[height-2][j]=3;
        		tableauterrain[height-1][j]=0;
        		tableauterrain[height-2][j-1]=3;
        		tableauterrain[height-1][j-1]=0;
        	}
        }
        for (int i=1;i<height;i++){
        	if (tableauterrain[i][0]==3 && tableauterrain[i][1]==3 && tableauterrain[i-1][0]==3 && tableauterrain[i-1][1]==3){
        		tableauterrain[i][1]=3;
        		tableauterrain[i][0]=0;
        		tableauterrain[i-1][1]=3;
        		tableauterrain[i-1][0]=0;
        	}
        }
        for (int i=1;i<height;i++){
        	if (tableauterrain[i][width-2]==3 && tableauterrain[i][width-1]==3 && tableauterrain[i-1][width-2]==3 && tableauterrain[i-1][width-1]==3){
        		tableauterrain[i][width-2]=3;
        		tableauterrain[i][width-1]=0;
        		tableauterrain[i-1][width-2]=3;
        		tableauterrain[i-1][width-1]=0;
        	}
        }
        
      //next instructions generates shores on water once again has some might be missing because of the filters
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		if (tableauterrain[i][j]==2){
        			if(tableauterrain[Math.min(i+1,height-1)][j]<=1 || tableauterrain[Math.max(i-1,0)][j]<=1 || tableauterrain[i][Math.min(j+1,width-1)]<=1 
        					|| tableauterrain[i][Math.max(j-1,0)]<=1 || tableauterrain[Math.min(i+1,height-1)][Math.min(j+1,width-1)]<=1 
        					||tableauterrain[Math.max(i-1,0)][Math.min(j+1,width-1)]<=1  || tableauterrain[Math.min(i+1,height-1)][Math.max(j-1,0)]<=1||
        							tableauterrain[Math.max(i-1,0)][Math.max(j-1,0)]<=1 ){
        				tableauterrain[i][j] = 3;
        			} else {
        				tableauterrain[i][j] = 2;
        			}
        		}
        	}
        }
      //next instructions generates shores on land once again has some might be missing because of the filters
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		if (tableauterrain[i][j]==3){
        			if((tableauterrain[Math.min(i+1,height-1)][j]<=1 || tableauterrain[Math.min(i+1,height-1)][j]==3) 
        					&& (tableauterrain[Math.max(i-1,0)][j]<=1 || tableauterrain[Math.max(i-1,0)][j]==3) 
        					&& (tableauterrain[i][Math.min(j+1,width-1)]<=1 || tableauterrain[i][Math.min(j+1,width-1)]==3 ) 
        					&& (tableauterrain[i][Math.max(j-1,0)]<=1 || tableauterrain[i][Math.max(j-1,0)]==3) 
        					&& (tableauterrain[Math.min(i+1,height-1)][Math.min(j+1,width-1)]<=1 || tableauterrain[Math.min(i+1,height-1)][Math.min(j+1,width-1)]==3)
        					&& (tableauterrain[Math.max(i-1,0)][Math.min(j+1,width-1)]<=1 || tableauterrain[Math.max(i-1,0)][Math.min(j+1,width-1)]==3) 
        					&& (tableauterrain[Math.min(i+1,height-1)][Math.max(j-1,0)]<=1 || tableauterrain[Math.min(i+1,height-1)][Math.max(j-1,0)]==3 )
        					&& (tableauterrain[Math.max(i-1,0)][Math.max(j-1,0)]<=1 || tableauterrain[Math.max(i-1,0)][Math.max(j-1,0)]==3)){
        				tableauterrain[i][j] = 0;
        			} else {
        				tableauterrain[i][j] = 3;
        			}
        		}
        	}
        }
        
        
      //next instructions applies the matrix to the actual board
        for (int i=0;i<height;i++){
        	for (int j=0;j<width;j++){
        		if (tableauterrain[i][j]==3){        			
        			this.tiles[i][j] = ShoreTile.getDefault();
        		}if (tableauterrain[i][j]==2){
        			this.tiles[i][j] = WaterTile.getDefault();
        		}if (tableauterrain[i][j]==1){
        			this.tiles[i][j] = ForestTile.getDefault();
        		} if ( tableauterrain[i][j]==0){
        			this.tiles[i][j] = GrassTile.getDefault();
        		}
        	}
        }
    
        

        this.selectedTile = this.getTile(GameBoard.DEFAULT_SELECTED_LOCATION.getRow(), GameBoard.DEFAULT_SELECTED_LOCATION.getColumn());

        this.tools = new ArrayList<>();
        this.tools.add(new BulldozerTool());
        this.tools.add(new CityHallTool());   
        this.tools.add(new PowerPlantConstructionTool());
        this.tools.add(new WaterPlantConstructionTool());
        this.tools.add(new ResidentialZoneDelimiterTool());
        this.tools.add(new IndustrialZoneDelimiterTool());
        this.tools.add(new CommercialZoneDelimiterTool());        
        this.tools.add(new HospitalConstructionTool());
        this.tools.add(new PolicestationConstructionTool());
        this.tools.add(new RoadTool());
        this.tools.add(new PublicGardenConstructionTool());
        

        this.selectedTool = this.tools.get(GameBoard.DEFAULT_SELECTED_TOOL);

        this.pendingEvolutions = new LinkedList<>();
        this.pendingEventsList = new LinkedList<>();
        this.resources = new CityResources(difficulty.getInitialCurrency(), height, width);        
        this.message = GameBoard.NOTHING_MESSAGE;
        this.texts = texts;
        
    }
    
    

    /**
     * Create a rectangle world with {@value height * width} tiles.
     *
     * @param height
     *            - {@link #getHeight()}
     * @param width
     *            - {@link #getWidth()}
     * @param texts
     *            - {@link #getTexts()}
     */
    public GameBoard(int height, int width, LocalizedTexts texts) {
        this(height, width, GameBoard.DEFAULT_DIFFICULTY, texts);
    }

    /**
     * Create a square world with {@value length * length} tiles.
     *
     * @param length
     *            - {@link #getWidth()} and {@link #getHeight()}
     * @param texts
     *            - {@link #getTexts()}
     */
    public GameBoard(int length, LocalizedTexts texts) {
        this(length, length, texts);
    }

    // Access
    public LocalizedTexts getTexts() {
        return this.texts;
    }

    // Access (World)
    
  
    /**
     * gets the properties view
     */
    public PropertiesView getVi() {
		return vi;
	}
    /**
     * set the properties view
     */
	public void setVi(PropertiesView vi) {
		this.vi = vi;
	}

	/**
     * @return Height of the world in row count.
     */
    public int getHeight() {
        return this.tiles.length;
    }

    /**
     * @return Width of the world in column count.
     */
    public int getWidth() {
        return this.tiles[0].length;
    }

    /**
     * @param row
     *            - Row number
     * @param column
     *            - Column number
     * @return Cell with at location ({@value row}, {@value column}).
     * @exception AssertionError
     *                if {@value row} or {@value column} is invalid.
     */
    public Tile getTile(int row, int column) {
        assert row >= 0 && row < this.getHeight() && column >= 0 && column < this.getWidth() : "Ligne/Colonne incorrecte";
        return this.tiles[row][column];
    }

    // Access (Tool)
    /**
     * @return Number of available tools.
     */
    public int getToolCount() {
        return this.tools.size();
    }


	/**
     * @return Tools' iterator of available tools.
     */
    

    public Iterator<Tool> toolIterator() {
        return this.tools.iterator();
    }

    // Access (Selection)
    /**
     * @return Selected tile.
     */
    public Tile getSelectedTile() {
        return this.selectedTile;
    }
    /**
     * @return Selected Tool.
     */
    public Tool getSelectedTool() {
        return this.selectedTool;
    }

    // Access (City Resources)
    /**
     * different get for the resources in city resources
     */
    public int getCurrency() {
        return this.resources.getCurrency();
    }

    public int getUnworkingPopulation() {
        return this.resources.getUnworkingPopulation();
    }
    
    public int getWorkingPopulation(){
    	return this.resources.getWorkingPopulation();
    }
    
    public int getPopulation() {
        return this.resources.getPopulation();
    }

    public int getEnergy() {
        return this.resources.getUnconsumedEnergy();
    }

    public int getProducts() {
        return this.resources.getProductsCount();
    }
    
    public int getWater() {
        return this.resources.getUnconsumedWater();
    }
    
    public int getCare() {
        return this.resources.getCareCapacity();
    }
    
    public int getHappiness() {
        return (int) this.resources.getOldHappiness();
    }


    // Access (Status)
    /**
     * @return Status message.
     */
    public String getMessage() {
        return this.message;
    }

    // Change (Selection)
    /**
     *
     * @param tool
     *            - {@link #getSelectedTool()}.
     */
    public void setSelectedTool(Tool tool) {
        this.selectedTool = tool;
        this.notifyViews();
    }

    /**
     * Select the tile at location ({@value row}, {@value column}).
     *
     * @param row
     * @param column
     */
    public void setSelectedTile(int row, int column) {
        this.selectedTile = this.getTile(row, column);
        this.notifyViews();
    }

    /**
     * Return a set of TilePosition defining a square created from the given
     * <code>startingTile</code> and the <code>areaSize</code>.
     *
     * @param startingTile
     * @param areaSize
     * @return Set of TilePosition
     */
    public Set<TilePosition> getTilesArea(TilePosition startingTile, int areaSize) {
        Set<TilePosition> tilesArea = new HashSet<>();

        for (int i = 0; i < areaSize; i++) {
            for (int j = 0; j < areaSize; j++) {
                int newRow = startingTile.getRow() + i < this.getHeight() ? startingTile.getRow() + i : this.getHeight() - 1;
                int newColumn = startingTile.getColumn() + j < this.getWidth() ? startingTile.getColumn() + j : this.getWidth() - 1;
                TilePosition newTile = new TilePosition(newRow, newColumn);
                tilesArea.add(newTile);
            }
        }
        return tilesArea;
    }

    // Change (World)
    /**
     * Effect the tile at location {@value row}, {@value column}) with
     * {@link #getSelectedTool()} if possible.
     *
     * @param row
     * @param column
     */
    public void effectTile(int row, int column) {
        assert row >= 0 && row < this.getHeight() && column >= 0 && column < this.getWidth() : "Ligne/Colonne incorrecte";

        final Tile currentTile = this.tiles[row][column];

        if(this.selectedTile instanceof CityHall){//si on clique sur la mairie, on ouvre la fen�tre des imp�ts
        	GestionImpots imp= new GestionImpots(resources.impot);

        }else{
	
	        if (this.selectedTool.canEffect(currentTile)) {
	        	if(this.selectedTool instanceof CityHallTool && hasCityHall()){ //empeche de poser 2 mairies
	        		this.message = "il y a d�j� une mairie";
	        	}else if((this.selectedTool instanceof HospitalConstructionTool || this.selectedTool instanceof PolicestationConstructionTool)&& this.seuilHabitant(resources)){
	        		//empeche de pose un hopital ou un commissariat s'il y a moins de 200 habitants
	        		this.message="vous n'avez pas besoin d'un hopital";
	        		if(this.selectedTool instanceof HospitalConstructionTool){
	        			NoNeedHospital nnh= new NoNeedHospital();
	        		}else if( this.selectedTool instanceof PolicestationConstructionTool){
	        			NoNeedPolicesation nnps = new NoNeedPolicesation();
	        		}     		
	        		
	        	}else {
	            if (this.selectedTool.isAfordable(currentTile, this.resources)) {
	
	                final Tile newTile = this.selectedTool.effect(currentTile, this.resources);
	                this.tiles[row][column] = newTile;
	                this.resources.setEtatRes(row, column, newTile.getVectReseaux());
	                newTile.setCoord(row,column);
	
	                this.pendingEvolutions.remove(currentTile);
	                if (newTile instanceof Evolvable) {
	                    this.pendingEvolutions.add((Evolvable) newTile);
	                }
	            } else {
	                this.message = this.texts.getMissingResourcesMsg();
	            }
	        	}
	        } else {
	            this.message = this.texts.getToolCannotAffectMsg();
	        }
        }

        this.notifyViews();
    }

    /**
     * Compute the next world state.
     */
    public void nextState() {
        GameBoard.ROUNDCOUNTER.incrementAndGet();
        this.vi.nextTick();
        this.resources.resetEphemerals();
        this.resources.refreshMatRes();
        this.applyPendingEvents();
        this.applyNewEvent();
        this.updateTiles();
        this.applyEvolutions();
        this.notifyViews();
        this.resources.impot.refreshImpot(this);
        this.resources.credit(this.resources.impot.TotalRec);
        this.resources.spend(this.resources.impot.TotalDep);
        this.resources.calculOfHappiness();
        
        if(this.variableSeuil && seuilHabitant(resources)==false){
        	this.variableSeuil=false;
        	WarningMessage seuilAtteint = new WarningMessage();
        }
        
        if(resources.getCurrency()>0 && this.variableDebt==true ){ //initializes variableDebt when currency goes over  after being under 0
        	this.variableDebt=false;
        }else if(this.variableDebt==false && resources.getCurrency()<0){//puts variableDebt to true to indicate that a DebtMessage has already been create
        	this.variableDebt=true;
        	DebtMessage dette = new DebtMessage();
        }
        
    }

    /**
     * Applies the effects of all the pending events (resulting from the
     * previous one).
     */
    private void applyPendingEvents() {
        List<Event> entry;
        for (Event event : this.pendingEventsList) {
            entry = event.applyEffects(this.resources);
            this.pendingEventsList.addAll(entry);
        }
    }

    /**
     * Generates a new event and applies its effects.
     */
    private void applyNewEvent() {
        Event event = EventFactory.generateEvent(this);
        List<Event> resultingEvents = event.applyEffects(this.resources);
        assert resultingEvents != null;
        String eventMessage = event.getMessage(this.texts);
        assert eventMessage != null : "The event message must not be null.";
        this.message = eventMessage.isEmpty() ? GameBoard.NOTHING_MESSAGE : eventMessage;
        this.pendingEventsList.addAll(resultingEvents);
    }

    // Implementation (Notification)
    /**
     * Notify view of a state change.
     */
    private void notifyViews() {
        this.setChanged();
        this.notifyObservers();
        this.message = GameBoard.NOTHING_MESSAGE;
    }

    /**
     * Apply evolutions in the order where it was registered.
     */
    private void applyEvolutions() {
        final int count = Math.min(GameBoard.MAX_HANDLED_EVOLUTIONS, this.pendingEvolutions.size());

        for (int i = 0; i < count; i++) {
            final Evolvable e = this.pendingEvolutions.poll(); // Not null
            
            e.evolve(this.resources);

            if (e.canEvolve()) {
                this.pendingEvolutions.add(e);
            }
        }
    }

    /**
     * Update all tiles via {@link Tile#update(CityResources)}.
     */
    private void updateTiles() {
        for (final Tile[] rows : this.tiles) {
            for (final Tile t : rows) {
    	/*for(int row=0;row<this.getHeight();row++){
    		for(int column=0;column<this.getWidth();column++){
    			Tile t=getTile(row, column);*/
                t.update(this.resources);
            }
        }
    }
    
    /**
     * returns true if a cityhall has already been built, false otherwise
     */
    private boolean hasCityHall(){
    	
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
            	if(this.tiles[i][j] instanceof CityHall ){
            		return true;

            	}
            }
        }
    return false;	
    }
    
    /**
     * 
     * @param res
     * @return true if the number of habitants <200
     */
    
    public boolean seuilHabitant(CityResources res){
    	if(res.getPopulation()<200){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * to be used only on test class
     * @param row
     * @param column
     * @param tile
     */
    public void setTile(int row,int column,Tile tile){
    	this.tiles[row][column]=tile;
    }
    
    /**
     * to be used only on test class
     * @param row
     * @param column
     * @param tile
     */
    public CityResources getResources(){
    	return this.resources;
    }

}
