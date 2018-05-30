package dialog;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ui.RefreshView;

public class NoNeedPolicesation {
	
	
	JFrame NoNeedPolicesation= new JFrame();
	
	public NoNeedPolicesation(){

	    
		
		NoNeedPolicesation.setTitle("No Need");
		NoNeedPolicesation.setSize(500, 200);  		    
	    JLabel label1 = new JLabel("You don't need any police station because ");
	    label1.setSize(400,25);
	    label1.setLocation(30,5);
	    
	    JLabel label2=new JLabel("your population is under 200 habitants");
	    label2.setSize(400,25);
	    label2.setLocation(30,25);
	    
	    JPanel pan = new JPanel(null);
	    pan.setLayout(null);
	    pan.add(label1);
	    pan.add(label2);
	    NoNeedPolicesation.add(pan);
	    
	    NoNeedPolicesation.setLocationRelativeTo(null);            

	    NoNeedPolicesation.setVisible(true);
	}

}
