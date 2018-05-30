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

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.GameBoard;
import model.tools.Tool;

/**
 *  enable a view of the tools for the player
 */
public class ToolsView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    public ToolsView(GameBoard w) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        final Iterator<Tool> it = w.toolIterator();
        while (it.hasNext()) {
            this.add(new ToolUI(w, it.next()));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameBoard;
    }

}
