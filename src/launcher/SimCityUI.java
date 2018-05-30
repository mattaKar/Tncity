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

package launcher;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.*;

import localization.FRTexts;
import localization.LocalizedTexts;
import localization.UKTexts;
import model.GameBoard;
import ui.ToolsView;
import ui.MessagesView;
import ui.PropertiesView;
import ui.RefreshView;
import ui.GameBoardView;

public final class SimCityUI extends JFrame {

    // Constants
    private final static long serialVersionUID = 1L;

    private final static int DEFAULT_HEIGHT = 15;

    private final static int DEFAULT_WIDTH = 26;

    private LocalizedTexts texts = new UKTexts();
    
    
    
    JFrame menu = new JFrame();
    JPanel pan = new Panneau();
    
    
    // Entry point
    /**
     * Run without arguments or with two arguments:
     *
     * First argument: height Second argument: width
     *
     * @param args
     */
    public static void main(String[] args) {
        final int height;
        final int width;

        if (args.length == 2) {
            final Scanner hSc = new Scanner(args[0]);
            final Scanner wSc = new Scanner(args[1]);

            if (hSc.hasNextInt()) { // if it is an integer
                height = hSc.nextInt();
            } else {
                height = SimCityUI.DEFAULT_HEIGHT;
                System.err.println("pasing: First argument must be an integer");
            }

            if (wSc.hasNextInt()) { // if it is an integer
                width = wSc.nextInt();
            } else {
                width = SimCityUI.DEFAULT_WIDTH;
                System.err.println("pasing: Second argument must be an integer");
            }

            hSc.close();
            wSc.close();
        } else {
            height = SimCityUI.DEFAULT_HEIGHT;
            width = SimCityUI.DEFAULT_WIDTH;

            if (args.length != 0) {
                System.err.println("pasing: Wrong number of arguments");
            }
        }

        // Pour que ce soit le thread graphique qui construise les composants
        // graphiques
        SwingUtilities.invokeLater(() -> new SimCityUI(height, width));
    }

    // Creation
    
    /**
     * Creates the different views, first the main menu and which allows to start or
     *  change language, then the game menu where the game is played
     */
    
    public SimCityUI(int hauteur, int largeur) {
    	
    	
 
    	menu.setTitle("Menu TNCYTY");
    	menu.setSize(1000,800);
    	menu.setLocationRelativeTo(null);
    	menu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	GridBagConstraints gbc = new GridBagConstraints();
    	pan.setLayout(new GridBagLayout());
    	JButton b1= new JButton("start");
    	gbc.gridx=1;
    	gbc.gridy=2;
    	gbc.gridheight=2;
    	pan.add(b1,gbc);
    	JButton b2= new JButton("English");
    	gbc.gridx=1;
    	gbc.gridy=4;
    	gbc.gridheight=2;
    	pan.add(b2,gbc);
    	JButton b3= new JButton("French");
    	gbc.gridx=1;
    	gbc.gridy=6;
    	gbc.gridheight=2;
    	pan.add(b3,gbc);
    	
    	menu.add(pan);
    	menu.setResizable(false);
    	menu.setVisible(true);
    	
    	
    	b1.addActionListener(new ActionListener() {
    		@Override
            public void actionPerformed(ActionEvent e) {
    		
    			menu.dispose();
    			JFrame jeu= new JFrame();  

    			// choix de la langue
    			

    			// Création du monde
    	        GameBoard monde = new GameBoard(hauteur, largeur, texts);

    	        // Création de la vue du monde, placée au centre de la fenêtre
    	        GameBoardView vm = new GameBoardView(monde);
    	        monde.addObserver(vm);
    	        jeu.add(vm, BorderLayout.CENTER);

    	        // Création de la palette des éléments de jeu, placée en haut
    	        ToolsView ve = new ToolsView(monde);
    	        monde.addObserver(ve);
    	        jeu.add(ve, BorderLayout.NORTH);

    	        // Création du panneau d'informations
    	        PropertiesView vi = new PropertiesView(monde, texts);
    	        monde.addObserver(vi);
    	        monde.setVi(vi);

    	        // Création du panneau de rafraichissement
    	        RefreshView rv = new RefreshView(monde);
    	        JPanel right = new JPanel();
    	        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
    	        right.add(vi);
    	        right.add(Box.createVerticalGlue());
    	        right.add(rv);
    	        jeu.add(right, BorderLayout.EAST);

    	        // Création du panneau de message
    	        MessagesView mv = new MessagesView();
    	        monde.addObserver(mv);
    	        jeu.add(mv, BorderLayout.SOUTH);

    	        jeu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	        jeu.pack();

    	        jeu.setResizable(true);
    	        jeu.setVisible(true);
    			
    		}
    	});
    	b2.addActionListener(new ActionListener() {
    		@Override
            public void actionPerformed(ActionEvent e) {
    			texts = new UKTexts();
    		}
    	});
    	b3.addActionListener(new ActionListener() {
    		@Override
            public void actionPerformed(ActionEvent e) {
    			texts = new FRTexts();
    		}
    	});
        
    	
    	

    }

}
