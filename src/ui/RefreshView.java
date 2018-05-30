
package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;








import model.GameBoard;

/**
 *  RefreshView is a class meant to handle the timer refresh
 */
public class RefreshView extends JPanel {
	
	/**
	 *  variable gathering the actual speed of the game
	 */
	public static int vitesse;
	
    // Constant
    private static final long serialVersionUID = 1L;

    // Creation
    /**
	 *  creates a timer with 3 speed: pause, speed1 and speed2 , the timer automatically gives the
	 *   nextstate instruction is a speed has been chosen until the player hit the pause button again.
	 */
    public RefreshView(GameBoard w) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton speed1 = new JButton(">");
        JButton speed2 = new JButton(">>");
        JButton pause = new JButton("||");
        

        speed1.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
            		vitesse=0;
            	
            		int interval = 5000; // 5 sec
            		Date timeToRun = new Date(System.currentTimeMillis() + interval);
            		Timer timer = new Timer();
            	    
            		timer.schedule(new TimerTask() {
            			public void run() {
            				w.nextState();
            				if (vitesse!=0){
            					timer.cancel();
            				}
            	               
            	               
            			}
            		}, timeToRun, 5000);
            	
            }
        });

        speed2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            		vitesse=1;
            	
            		int interval = 2000; // 2 sec

            		Date timeToRun = new Date(System.currentTimeMillis() + interval);
            		Timer timer = new Timer();
            	    
            		timer.schedule(new TimerTask() {
            			public void run() {
            				w.nextState();

            				if (vitesse!=1){

            					timer.cancel();
            				}
            	               
            	               
            			}
            		}, timeToRun, 2000);
            	
            }
        });
        
        pause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            		vitesse=2;
	
            }
        });
        this.add(pause);
        this.add(speed1);
        this.add(speed2);
        
    }

}