package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class RiotMessage {
	
	
	JFrame Riot= new JFrame();
	
	public RiotMessage(){

	    
		
		Riot.setTitle("neglectfull !");
	    Riot.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("You have been neglecting your people, they are riotting");
	    label1.setSize(400,25);
	    label1.setLocation(30,5);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    Riot.add(pan);
	    
	   	Riot.setLocationRelativeTo(null);            

	    Riot.setVisible(true);
	}

}
