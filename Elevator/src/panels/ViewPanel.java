package panels;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import components.Elevator;

public class ViewPanel extends JPanel{
	private Elevator elevator;
	private int selectedFloor = 0;
	private JToggleButton[] elevatorLocation = new JToggleButton[15];
	
	public ViewPanel(Elevator e) {
		this.elevator = e;
		init();
	}
	
	private void init() {
		makeObject();
		placeObject();
		repaintThread();
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK,1));
	}
	
	public void repaintThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						setView();
						Thread.sleep(40);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	private void setView() {
		if(selectedFloor !=elevator.getFloor()) {
			elevatorLocation[selectedFloor-1].setBackground(Color.WHITE);
			elevatorLocation[elevator.getFloor()-1].setBackground(Color.YELLOW);
			selectedFloor = elevator.getFloor();
		}
		
	}
	
	private void makeObject() {
		for(int i=0; i<elevatorLocation.length; i++) {
			elevatorLocation[i] = new JToggleButton("ElevatorLocation" + i+1);
			elevatorLocation[i].setText(Integer.toString(i+1));
			elevatorLocation[i].setBackground(Color.white);
			elevatorLocation[i].setMargin(new Insets(0,0,0,0));
			elevatorLocation[i].setEnabled(false);
		}
		selectedFloor = elevator.getFloor();
		elevatorLocation[selectedFloor-1].setBackground(Color.YELLOW);
	}
	
	private void placeObject() {
		for(int i=elevatorLocation.length-1; i>=0; i--) {
			elevatorLocation[i].setBounds(0, (14-i)*64, 400, 64);
			add(elevatorLocation[i]);
		}
	}
	
}