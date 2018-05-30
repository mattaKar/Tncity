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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.GameBoard;
import model.tiles.Tile;
import model.tools.Tool;
import model.tools.RoadTool;
import model.tiles.GrassTile;
import ui.IconFactory;

/**
 *  TileUI allows the display of the different icons for tiles
 */
public class TileUI extends JLabel {

    private static final long serialVersionUID = 1L;

    private int row;

    private int column;

    private GameBoard model;

    /**
	 *  Gives the different base interactions with the board
	 */
    public TileUI(GameBoard m, final int row, final int column) {
        super(" ");
        this.model = m;
        this.row = row;
        this.column = column;
        this.setBorder(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TileUI.this.model.effectTile(row, column);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                TileUI.this.model.setSelectedTile(row, column);
            }
        });

        this.update();

    }

    /**
	 *  refresh the tile icon 
	 */
    public void update() {
        final Tile elt = this.model.getTile(this.row, this.column);
        Tile elt_North = elt;
        Tile elt_South = elt;
        Tile elt_East = elt;
        Tile elt_West = elt;
        
        if (this.row!=0 ){
        	elt_North = this.model.getTile(this.row-1, this.column);
        }
        if (this.row!=model.getHeight()-1  ){
       	elt_South = this.model.getTile(this.row+1, this.column);
       }
        if (this.column!=0 ){
       	elt_West = this.model.getTile(this.row, this.column-1);
       }
        if (this.column!=model.getWidth()-1){
       	elt_East = this.model.getTile(this.row, this.column+1);
       }
        	
        final Tool selectedTool = this.model.getSelectedTool();
        
        if (selectedTool.canEffect(elt)) {
            final int cost = selectedTool.getCost(elt);
            this.setToolTipText(MessageFormat.format(this.model.getTexts().getCurrencyMsg(), cost));
        } else {
            this.setToolTipText(this.model.getTexts().getToolCannotAffectMsg());
        }

        	ImageIcon ii = IconFactory.getInstance().getTileIcon(elt,elt_North,elt_South,elt_East,elt_West);
            this.setIcon(ii);
        
        
    }

}
