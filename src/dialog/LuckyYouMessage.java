package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class LuckyYouMessage {
	

	JFrame LuckyYou= new JFrame();
	
	public LuckyYouMessage(){
		

		LuckyYou.setTitle("Chance!");
		LuckyYou.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("you're so lucky, the bank found out she owes you 100$");
	    label1.setSize(400,25);
	    label1.setLocation(30,5);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    
	    LuckyYou.add(pan);
	    
	    LuckyYou.setLocationRelativeTo(null);            
	   	LuckyYou.setVisible(true);
	}

}
