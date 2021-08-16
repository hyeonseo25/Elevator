package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import components.Elevator;

public class ElevatorOutPanel extends JPanel{
	private Image elevatorDoorImg;
	private ImageIcon closeImg = new ImageIcon("images/door.png");
	private ImageIcon openImg = new ImageIcon("images/openDoor.png");
	private JButton upButton;
	private JButton downButton;
	private JPanel buttonPanel;
	private JLabel floorlb;
	private JComboBox floorcb;
	private Elevator elevator;
	private int nowFloor;
	private boolean open;
	private int[][] buttonChecked = new int[2][15];
	private Font f1 = new Font("³ª´®¹Ù¸¥°íµñ",Font.BOLD,56);//±âº»ÆùÆ®
	
	public int getNowFloor() {
		return nowFloor;
	}

	public void setNowFloor(int nowFloor) {
		this.nowFloor = nowFloor;
	}

	public ElevatorOutPanel(Elevator e) {
		this.elevator = e;
		nowFloor = 1;
		init();
	}
	
	private void init() {
		makeObject();
		placeObject();
		setActionListener();
		repaintThread();
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK,1));
		elevatorDoorImg = closeImg.getImage();
	}
	
	public void repaintThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					repaint();
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
		// ¹®ÀÌ ¿­¸®°í ´ÝÈú ¶§
		if(elevator.isOpen()&&elevator.getFloor()==nowFloor) {
			this.open = true;
	    	if(elevator.getOutButtonChecked()[1][nowFloor]==0) {
	    		upButton.setBackground(Color.WHITE);
	    	}
	    	if(elevator.getOutButtonChecked()[0][nowFloor]==0){
	    		downButton.setBackground(Color.WHITE);
	    	}
			elevatorDoorImg = openImg.getImage();
		}else if(this.open == true) {
			elevatorDoorImg = closeImg.getImage();
		}
	}
	
	private void makeObject() {
		// make up&down button
		upButton = new JButton("upButton");
		upButton.setText("¡è");
		upButton.setBackground(Color.white);
		upButton.setMargin(new Insets(0,0,0,0));
		
		downButton = new JButton("downButton");
		downButton.setText("¡é");
		downButton.setBackground(Color.white);
		downButton.setMargin(new Insets(0,0,0,0));
		
		// make button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.GRAY);
		
		// make floor label
		floorlb = new JLabel(Integer.toString(nowFloor));
		floorlb.setHorizontalAlignment(JLabel.CENTER);
		floorlb.setFont(f1);
		
		// make choice floor combobox
		floorcb = new JComboBox();
		for(int i=1; i<=15; i++) {
			floorcb.addItem(i);
		}
	}
	
	private void placeObject() {
		buttonPanel.setBounds(590, 260, 46, 106);
		
		upButton.setBounds(8, 8, 30, 30);
		downButton.setBounds(8, 70, 30, 30);
		
		buttonPanel.add(upButton);
		buttonPanel.add(downButton);
		
		floorlb.setBounds(320, 30, 150, 70);
		floorcb.setBounds(10, 10, 80, 30);
		
		add(buttonPanel);
		add(floorlb);
		add(floorcb);
	}
	
	private void setActionListener() {
		floorcb.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	setNowFloor(floorcb.getSelectedIndex()+1);
		    	upButton.setBackground(Color.WHITE);
		    	downButton.setBackground(Color.WHITE);
		    	if(elevator.getOutButtonChecked()[1][nowFloor-1]==1) {
		    		upButton.setBackground(Color.RED);
		    	}
		    	if(elevator.getOutButtonChecked()[0][nowFloor-1]==1){
		    		downButton.setBackground(Color.RED);
		    	}
		    	floorlb.setText(Integer.toString(getNowFloor()));
			}
		});
		
		upButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	buttonChecked[1][nowFloor-1] = 1;
		    	upButton.setBackground(Color.RED);
		    	if(elevator.getStatus()!=1&&nowFloor<elevator.getDestination()) {
		    		elevator.setCheck(true);
		    	}else if(elevator.isCheck()==true) {
		    		elevator.setCheck(false);
		    	}
		    	elevator.setDestination(nowFloor);
				elevator.getOutButtonChecked()[1][nowFloor-1] = 1;
			}
		});
		
		downButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	buttonChecked[0][nowFloor-1] = 1;
		    	downButton.setBackground(Color.RED);
		    	if(elevator.getStatus()!=0&&nowFloor>elevator.getDestination()) {
		    		elevator.setCheck(true);
		    	}else if(elevator.isCheck()==true) {
		    		elevator.setCheck(false);
		    	}
		    	elevator.setDestination(nowFloor);
				elevator.getOutButtonChecked()[0][nowFloor-1] = 1;
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(elevatorDoorImg, (this.getWidth()-elevatorDoorImg.getWidth(null))/2, 180, this);
	}
} 