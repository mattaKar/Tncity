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

package ui;

import java.net.URL;
import java.util.Observable;

import javax.swing.ImageIcon;

import model.tiles.BuildableTile;
import model.tiles.Destroyable;
import model.tiles.ForestTile;
import model.tiles.ShoreTile;
import model.tiles.PowerPlantTile;
import model.tiles.RoadTile;
import model.tiles.Tile;
import model.tiles.WaterPlantTile;
import model.tiles.WaterTile;
import model.tiles.PublicGardenTile;
import model.tools.Tool;
import model.GameBoard;

/**
 * A Tile's or tool"s icon name is based on its class name and it's surroundings.
 *
 * @author Martine Gautier
 * @author Victorien Elvinger
 *
 */
public class IconFactory {

    // Constant
    /**
     * Default value of {@link IconFactory#getpathTemplate()} 1: id
     */
    public final static String DEFAULT_PATH_TEMPLATE = "resources/icons/%s.png";
    
    /**
     * {@link IconFactory#getInstance()}
     */
    private final static IconFactory INSTANCE = new IconFactory(IconFactory.DEFAULT_PATH_TEMPLATE);

    /**
     * 1: path
     */
    private final static String ERROR_MESSAGE_TEMPLATE = "error: icon not found at %s";

    // Constant (id)
    /**
     * Default 'no-icon' for tool.
     */
    private final static String NO_ICON_TOOL_ID = "no-icon-tool";

    /**
     * Default 'no-icon' for tile.
     */
    private final static String NO_ICON_TILE_ID = "no-icon-tile";

    /**
     * Icon for all Destroyable tiles where {@link Destroyable#isDestroyed()} is
     * true.
     */
    public final static String DEBRIS_TILE_ID = "debris";

    /**
     * Id if {@link BuildableTile#isEnergyMissing()} is enabled.
     */
    public final static String MISSING_ENERGY_POSTID = "missing-energy";
    
    /**
     * Id if {@link BuildableTile#isWaterMissing()} is enabled.
     */
    public final static String MISSING_WATER_POSTID ="missing-water";
    
    /**
     * Id if the tile up-north of the chosen tile is of the same instance or meant to connect 
     * in a specific way to it
     */
    public final static String SAME_NORTH_POSTID ="north";
    
    /**
     * Id if the tile down-south of the chosen tile is of the same instance or meant to connect 
     * in a specific way to it
     */
    public final static String SAME_SOUTH_POSTID ="south";
    
    /**
     * Id if the tile east of the chosen tile is of the same instance or meant to connect 
     * in a specific way to it
     */
    public final static String SAME_EAST_POSTID ="east";
    
    /**
     * Id if the tile west of the chosen tile is of the same instance or meant to connect 
     * in a specific way to it
     */
    public final static String SAME_WEST_POSTID ="west";

    
    // Implementation
    private final String pathTemplate;

    // Factory
    static public IconFactory getInstance() {
        return IconFactory.INSTANCE;
    }

    // Creation
    /**
     * Prefer use {@link IconFactory#getInstance()} instead.
     *
     * @param pathTpl
     */
    private IconFactory(String pathTpl) {
        this.pathTemplate = pathTpl;
    }

    // Access
    /**
     * @return Path template to icons.
     */
    public String getpathTemplate() {
        return this.pathTemplate;
    }

    /**
     * @param aTool
     * @return Icon associated to {@value aTool}.
     */
    public ImageIcon getToolIcon(Tool aTool) {
    	final String toolId = this.dashSeparatedWordsFromCamelCase(aTool.getClass().getSimpleName());
        return this.getIcon(toolId, IconFactory.NO_ICON_TOOL_ID);
    }

    /**
     * @param aTile
     * @return Icon associated to {@value aTile}.
     */
    public ImageIcon getTileIcon(Tile aTile,Tile aTile_North,Tile aTile_South,Tile aTile_East,Tile aTile_West) {
        return this.getIcon(this.getTileId(aTile,aTile_North,aTile_South,aTile_East,aTile_West), IconFactory.NO_ICON_TILE_ID);
    }

    // Implementation
    /**
     * @param aIconId
     * @param aReplacementIconId
     *            - May be null.
     * @return Retrieve icon with {@value aId} as name or {@value aIconId} if
     *         {@value aId} doesn't exist. If both are not retrievable then
     *         throw a runtime exception.
     */
    public ImageIcon getIcon(String aIconId, String aReplacementIconId) {
        final String path = String.format(this.pathTemplate, aIconId);
        final URL url1 = ClassLoader.getSystemResource(path);

        if (url1 != null) {
            return new ImageIcon(url1);
        } else if (aReplacementIconId != null) {
            return this.getIcon(aReplacementIconId, null);
        } else {
            throw new RuntimeException(String.format(IconFactory.ERROR_MESSAGE_TEMPLATE, path));
        }
    }

    /**
     * e.g. turns "RandomWord" into "random-word".
     *
     * @param s
     *            - CamelCase string
     * @return lower-case string with dash-separated words.
     */
    private String dashSeparatedWordsFromCamelCase(String s) {
        final String regexPattern = "([a-z])([A-Z]+)";
        final String replacementPattern = "$1-$2";

        return s.replaceAll(regexPattern, replacementPattern).toLowerCase();
    }

    /**
     * @param aTile
     * @return Id that corresponds to {@value aTile}.
     */
    private String getTileId(Tile aTile,Tile aTile_North,Tile aTile_South,Tile aTile_East,Tile aTile_West) {
        if (aTile instanceof Destroyable && ((Destroyable) aTile).isDestroyed()) {
            return IconFactory.DEBRIS_TILE_ID;
        } else {
            final String id = this.dashSeparatedWordsFromCamelCase(aTile.getClass().getSimpleName());
            // Turn the class's name into dash-separated words in lower-case.
            
 
            
            	if (aTile instanceof BuildableTile) {
            		
            		final BuildableTile t = (BuildableTile) aTile;

                    final String statePostId = '-' + t.getState().name().toLowerCase().replace('_', '-');
                    
                    
            		if (aTile instanceof RoadTile){

                		final String northPostId;
                		if(aTile_North instanceof BuildableTile | aTile_North instanceof PowerPlantTile | aTile_North instanceof WaterPlantTile | aTile_North instanceof PublicGardenTile){
                			northPostId="-" + IconFactory.SAME_NORTH_POSTID;
                		}else {
                			northPostId="";
                		}
                		final String southPostId;
                		if(aTile_South instanceof BuildableTile | aTile_South instanceof PowerPlantTile | aTile_South instanceof WaterPlantTile | aTile_South instanceof PublicGardenTile){
                			southPostId="-" + IconFactory.SAME_SOUTH_POSTID;
                		}else {
                			southPostId="";
                		}
                		final String eastPostId;
                		if(aTile_East instanceof BuildableTile | aTile_East instanceof PowerPlantTile | aTile_East instanceof WaterPlantTile| aTile_East instanceof PublicGardenTile){
                			eastPostId="-" + IconFactory.SAME_EAST_POSTID;
                		}else {
                			eastPostId="";
                		}
                		final String westPostId;
                		if(aTile_West instanceof BuildableTile | aTile_West instanceof PowerPlantTile | aTile_West instanceof WaterPlantTile| aTile_West instanceof PublicGardenTile){
                			westPostId="-" + IconFactory.SAME_WEST_POSTID;
                		}else {
                			westPostId="";
                		}
                		return id + statePostId + northPostId + southPostId + eastPostId + westPostId;
                		
                	} else {
                		
                        // Turn enumeration value into dash-separated words in
                        // lower-case


                        final String energyPostId;
                        if (t.isEnergyMissing()) {
                            energyPostId = '-' + IconFactory.MISSING_ENERGY_POSTID;
                        } else {
                            energyPostId = "";
                        }
                        
                        final String waterPostId;
                        if (t.isWaterMissing()) {
                            waterPostId = '-' + IconFactory.MISSING_WATER_POSTID;
                        } else {
                            waterPostId = "";
                        }
                       
                        return id + statePostId + energyPostId + waterPostId;
                        
                	}

            } else {
            	if (aTile instanceof ForestTile){

            		final String northPostId;
            		if(aTile_North instanceof ForestTile ){
            			northPostId="-" + IconFactory.SAME_NORTH_POSTID;
            		}else {
            			northPostId="";
            		}
            		final String southPostId;
            		if(aTile_South instanceof ForestTile){
            			southPostId="-" + IconFactory.SAME_SOUTH_POSTID;
            		}else {
            			southPostId="";
            		}
            		final String eastPostId;
            		if(aTile_East instanceof ForestTile){
            			eastPostId="-" + IconFactory.SAME_EAST_POSTID;
            		}else {
            			eastPostId="";
            		}
            		final String westPostId;
            		if(aTile_West instanceof ForestTile){
            			westPostId="-" + IconFactory.SAME_WEST_POSTID;
            		}else {
            			westPostId="";
            		}
            		return id + northPostId + southPostId + eastPostId + westPostId;
            		
            		
            	}
            	if (aTile instanceof ShoreTile){

            		String northPostId;
            		if(aTile_North instanceof ShoreTile || aTile_North instanceof WaterTile){
            			northPostId="-shore" + IconFactory.SAME_NORTH_POSTID;
            			if(aTile_North instanceof WaterTile){
                			northPostId="-water" + IconFactory.SAME_NORTH_POSTID;
                		}
            		}
            		else {
            			northPostId="-other" + IconFactory.SAME_NORTH_POSTID;;
            		}
            		String southPostId;
            		if(aTile_South instanceof ShoreTile || aTile_South instanceof WaterTile){
            			southPostId="-shore" + IconFactory.SAME_SOUTH_POSTID;
            			if(aTile_South instanceof WaterTile){
                			southPostId="-water" + IconFactory.SAME_SOUTH_POSTID;
                		}
            		}
            		else {
            		
            			southPostId="-other" + IconFactory.SAME_SOUTH_POSTID;;
            		}
            		String eastPostId;
            		if(aTile_East instanceof ShoreTile || aTile_East instanceof WaterTile){
            			eastPostId="-shore" + IconFactory.SAME_EAST_POSTID;
            			if(aTile_East instanceof WaterTile){
                			eastPostId="-water" + IconFactory.SAME_EAST_POSTID;
                		}
            		}
            		else {
            		
            			eastPostId="-other" + IconFactory.SAME_EAST_POSTID;;
            		}
            		String westPostId;
            		if(aTile_West instanceof ShoreTile || aTile_West instanceof WaterTile){
            			westPostId="-shore" + IconFactory.SAME_WEST_POSTID;
            			if(aTile_West instanceof WaterTile){
                			westPostId="-water" + IconFactory.SAME_WEST_POSTID;
                		}
            		}
            		else {
            		
            			westPostId="-other" + IconFactory.SAME_WEST_POSTID;;
            		}

            		return id + northPostId + southPostId + eastPostId + westPostId;
            	}
            	else{
                return id;
            	}
            }
            
        }
    }

}
