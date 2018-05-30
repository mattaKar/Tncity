package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class NoNeedHospital {
	
	
	JFrame NoNeedHospital= new JFrame();
	
	public NoNeedHospital(){

	    
		
		NoNeedHospital.setTitle("No Need");
		NoNeedHospital.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("You don't need any hospital because");
	    label1.setSize(400,25);
	    label1.setLocation(30,5);
	    
	    JLabel label2=new JLabel("your population is under 200 habitants");
	    label2.setSize(400,25);
	    label2.setLocation(30,25);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    pan.add(label2);
	    NoNeedHospital.add(pan);
	    
	    NoNeedHospital.setLocationRelativeTo(null);            

	    NoNeedHospital.setVisible(true);
	}

}
