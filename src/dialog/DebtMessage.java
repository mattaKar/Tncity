package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class DebtMessage {
	
	
	JFrame Debt= new JFrame();
	
	public DebtMessage(){

	    
		
		Debt.setTitle("Warning");
		Debt.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("Your currency resources are under 0.");
	    label1.setSize(400,25);
	    label1.setLocation(30,50);
	    
	    JLabel label2 = new JLabel("You can't place any tile until it goes over 0.");
	    label2.setSize(400,25);
	    label2.setLocation(30,70);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    pan.add(label2);
	    Debt.add(pan);
	    
	    Debt.setLocationRelativeTo(null);            

	    Debt.setVisible(true);
	}

}
