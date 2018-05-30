package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class WarningMessage {
	
	
	JFrame Warning= new JFrame();
	
	public WarningMessage(){

	    
		
		Warning.setTitle("Warning");
		Warning.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("Your population is over 200 habitants.");
	    label1.setSize(400,25);
	    label1.setLocation(30,50);
	    
	    JLabel label2 = new JLabel("You should build an hospital and a police station.");
	    label2.setSize(400,25);
	    label2.setLocation(10,70);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    pan.add(label2);
	    Warning.add(pan);
	    
	    Warning.setLocationRelativeTo(null);            

	    Warning.setVisible(true);
	}

}
