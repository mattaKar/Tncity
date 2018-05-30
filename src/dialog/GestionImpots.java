package dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import ui.Impot;
import ui.RefreshView;

import javax.swing.JButton;


public class GestionImpots extends JFrame {

	protected int saveVitesse;
	
	JFrame gestionImpot= new JFrame();
	
	
	/**
	 * GestionImpot (Taxes Manager) Opens a window in which the players can select the amount of taxes he wants
	 * the citizens and industries to pay and the budget it wants to spend on the public services
	 */
	  public GestionImpots(Impot i){
		  
		  this.saveVitesse=RefreshView.vitesse;
		  
		    gestionImpot.setTitle("Taxes Manager");
		    gestionImpot.setSize(500, 500);
		    //title    		    
		    JLabel label7 = new JLabel("Earnings");
		    label7.setSize(180,25);
		    label7.setLocation(200,5);
		   
		  //spinner info
		    int max=100;
		    int min=0;
		    int step=1;	
		  //taxes VAT
		    int tauxTVA =i.tauxTVA;

		  //Spinner
		    SpinnerModel model1 = new SpinnerNumberModel(tauxTVA, min, max, step);
		    JSpinner spinner1 = new JSpinner(model1);
		    spinner1.setSize(100,25);
		    spinner1.setLocation(350,50);
		  //spinner label
		    
		    JLabel label1 = new JLabel("VAT applied to sold products in %");
		    label1.setSize(300,25);
		    label1.setLocation(20,50);
		    
		    tauxTVA= (int) spinner1.getValue();
		    
		    //residential tiles taxes
		    int tauxLoc = i.tauxloc;
		    
		    SpinnerModel model2 = new SpinnerNumberModel(tauxLoc, min, max, step);
		    JSpinner spinner2 = new JSpinner(model2);

		    spinner2.setSize(100,25);	
		    spinner2.setLocation(350,100);
		    JLabel label2 = new JLabel("Local taxes per inhabitant in %");
		    label2.setSize(300,25);
		    label2.setLocation(20,100);
		    
		    tauxLoc=(int) spinner2.getValue();   
		    
		    JLabel label8 = new JLabel("Expenditures");
		    label8.setSize(180,25);
		    label8.setLocation(200,200);
		    
		    //health tax info
		    int maxSan= 100000000;
		    int tauxSan = i.tauxHosp;
		    int minSan=10;
		    
		    SpinnerModel model4 = new SpinnerNumberModel(tauxSan, minSan, maxSan, step);
		    JSpinner spinner4 = new JSpinner(model4);
		    spinner4.setSize(100,25);	
		    spinner4.setLocation(350,250);
		    JLabel label4 = new JLabel("Health budget per hospital each month");
		    label4.setSize(300,25);
		    label4.setLocation(20,250);
		    
		    //security tax info
		    int tauxSecu=i.tauxSecu;
		    int minSecu=10;		    

		    SpinnerModel model5 = new SpinnerNumberModel(tauxSecu, minSecu, max, step);
		    JSpinner spinner5 = new JSpinner(model5);
		    spinner5.setSize(100,25);
		    spinner5.setLocation(350,300);
		    JLabel label5 = new JLabel("Security budget per police station each month");
		    label5.setSize(300,25);
		    label5.setLocation(20,300);
		    
		    
		    //leisure tax info
		    int tauxPP=i.tauxPP;
		    int minPP=20;		    

		    SpinnerModel model6 = new SpinnerNumberModel(tauxPP, minPP, max, step);
		    JSpinner spinner6 = new JSpinner(model6);
		    spinner6.setSize(100,25);
		    spinner6.setLocation(350,350);
		    JLabel label6 = new JLabel("leisure budget per building each month");
		    label6.setSize(300,25);
		    label6.setLocation(20,350);
		    
		  
		    //save button
		    JButton validation = new JButton("save");
		    validation.setSize(100,25);
		    validation.setLocation(300,390);
		    
		    validation.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		i.tauxloc=(int) spinner2.getValue();
		    		i.tauxTVA=(int) spinner1.getValue();
		    		i.tauxHosp=(int) spinner4.getValue();
		    		i.tauxSecu=(int)spinner5.getValue();
		    		i.tauxPP=(int)spinner6.getValue();
		    		RefreshView.vitesse=saveVitesse;
		    		gestionImpot.dispose();

		    	}
		    });
		    
		    
		    JPanel pan = new JPanel(null);
		    pan.setLayout(null);
		    pan.add(label7);
		    
		    pan.add(label1);
		    pan.add(spinner1);
		    pan.add(label2);
		    pan.add(spinner2);
		    pan.add(label8);
		    pan.add(label4);
		    pan.add(spinner4);
		    pan.add(label5);
		    pan.add(spinner5);
		    pan.add(validation);
		    pan.add(label7);
		    pan.add(label8);
		    pan.add(spinner6);
		    pan.add(label6);
		    gestionImpot.add(pan);
		    
		    gestionImpot.setLocationRelativeTo(null);            
		    gestionImpot.setVisible(true);
		    RefreshView.vitesse=0;
		  }
	  
	
	  

}
