package launcher;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * a class intended to add a background to the main menu
 */
public class Panneau extends JPanel {
	private final static long serialVersionUID = 1L;


	public void paintComponent(Graphics g){
		try {
			Image img = ImageIO.read(new File("opening.png"));
			//Pour une image de fond
			g.drawImage(img, 0,0, 1000, 800, this);
		} catch (IOException e) {
			e.printStackTrace();
		}                
	}
	
}