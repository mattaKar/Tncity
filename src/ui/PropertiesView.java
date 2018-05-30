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



import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import localization.LocalizedTexts;
import model.GameBoard;
/**
 *  PropertiesView is meant to help the player by giving him the status of different resources like
 *  population and such
 */
public class PropertiesView extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    /**
	 *  different labels for the resources
	 */
    private JLabel currency;
    private JLabel energy;
    private JLabel unworkingPop;
    private JLabel products;
    private JLabel Pop;
    private JLabel workingPop;
    private JLabel date;
    private JLabel care;
    private JLabel happiness;
    int numeromoi;
    ArrayList<String> mois; 


    private JLabel water;

    /**
	 *  constructor setting the different informations 
	 */
    public PropertiesView(GameBoard w, LocalizedTexts texts) {
        super();
        mois = new ArrayList<>();
        mois.add("January");
        mois.add("February");
        mois.add("March");
        mois.add("April");
        mois.add("May");
        mois.add("June");
        mois.add("July");
        mois.add("August");
        mois.add("September");
        mois.add("October");
        mois.add("November");
        mois.add("December");
        numeromoi=0;
        
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(new JLabel(texts.getCurrencyLabel()));
        this.currency = new JLabel(Integer.toString(w.getCurrency()));
        this.add(this.currency);

        this.add(new JLabel(texts.getUnconsumedEnergyLabel()));
        this.energy = new JLabel(Integer.toString(w.getEnergy()));
        this.add(this.energy);
        
        this.add(new JLabel(texts.getUnconsumedWaterLabel()));
        this.water = new JLabel(Integer.toString(w.getWater()));
        this.add(this.water);
        
        this.add(new JLabel(texts.getPopulationLabel()));
        this.Pop = new JLabel(Integer.toString(w.getPopulation()));
        this.add(this.Pop);

        this.add(new JLabel(texts.getUnworkingPopulationLabel()));
        this.unworkingPop = new JLabel(Integer.toString(w.getUnworkingPopulation()));
        this.add(this.unworkingPop);
        
        this.add(new JLabel(texts.getWorkingPopulationLabel()));
        this.workingPop = new JLabel(Integer.toString(w.getWorkingPopulation()));
        this.add(this.workingPop);
       
        this.add(new JLabel(texts.getStoredProductsLabel()));
        this.products = new JLabel(Integer.toString(w.getProducts()));
        this.add(this.products);
        
        this.add(new JLabel(texts.getCareCapacityLabel()));
        this.care=new JLabel(Integer.toString(w.getCare()));
        this.add(this.care);
        
        this.add(new JLabel(texts.getHappinessLabel()));
        this.happiness=new JLabel(Integer.toString(w.getHappiness()));
        this.add(this.happiness);
        
        this.add(new JLabel("date"));
        this.date= new JLabel(mois.get(numeromoi));
        this.add(this.date);

    }

    /**
	 *  update takes the property views to the current data so that the player is always up-to-date
	 */
    @Override
    public void update(Observable o, Object arg) {
        assert o instanceof GameBoard;
        GameBoard world = (GameBoard) o;
        this.currency.setText(MessageFormat.format(world.getTexts().getCurrencyMsg(), world.getCurrency()));
        this.energy.setText("" + world.getEnergy());
        this.unworkingPop.setText("" + world.getUnworkingPopulation());
        this.workingPop.setText("" + world.getWorkingPopulation());
        this.Pop.setText("" + world.getPopulation());
        this.products.setText("" + world.getProducts());

        this.water.setText("" + world.getWater());
        this.care.setText(""+world.getCare());
        this.happiness.setText(""+world.getHappiness());

        this.date.setText("" + this.mois.get(numeromoi%12) + "   " + (1985+numeromoi/12));
    }
    
    /**
	 * nextTick is a side count that allows the date to be displayed
	 */
    public void nextTick(){
    	numeromoi++;
    }

}
