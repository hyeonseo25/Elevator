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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import components.Elevator;

public class ElevatorInPanel extends JPanel{
	private Image elevatorDoorImg;
	private ImageIcon closeImg = new ImageIcon("images/door.png");
	private ImageIcon openImg = new ImageIcon("images/openDoor.png");
	private JToggleButton[] floorButton = new JToggleButton[15];
	private JButton openButton;
	private JButton closeButton;
	private JPanel buttonPanel;
	private JLabel floorlb;
	private Elevator elevator;
	private boolean open;
	private Font f1 = new Font("나눔바른고딕",Font.BOLD,36);//기본폰트
	
	public ElevatorInPanel(Elevator e) {
		this.elevator = e;
		init();
	}
	
	private void init() {
		makeObject();
		placeObject();
		setActionListener();
		repaintThread();
		
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
		// 층 이동할 때 숫자 바뀌게
		if(Integer.parseInt(floorlb.getText())!=elevator.getFloor()) {
			floorlb.setText(Integer.toString(elevator.getFloor()));
		}
		
		// 문이 열리고 닫힐 때
		if(elevator.isOpen()) {
			this.open = true;
			floorButton[elevator.getFloor()-1].setSelected(false);
			elevatorDoorImg = openImg.getImage();
		}else if(this.open == true) {
			elevatorDoorImg = closeImg.getImage();
		}
	}
	
	private void makeObject() {
		// make floor buttons
		for(int i=0; i<floorButton.length; i++) {
			floorButton[i] = new JToggleButton("FloorButton" + i+1);
			floorButton[i].setText(Integer.toString(i+1));
			floorButton[i].setBackground(Color.white);
			floorButton[i].setMargin(new Insets(0,0,0,0));
		}
		
		// make open&close button
		openButton = new JButton("openButton");
		openButton.setText("< >");
		openButton.setBackground(Color.white);
		openButton.setMargin(new Insets(0,0,0,0));
		
		closeButton = new JButton("closeButton");
		closeButton.setText("><");
		closeButton.setBackground(Color.white);
		closeButton.setMargin(new Insets(0,0,0,0));
		
		// make button panel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBackground(Color.GRAY);
		
		// make floor label
		floorlb = new JLabel(Integer.toString(elevator.getFloor()));
		floorlb.setOpaque(true);
		floorlb.setBackground(Color.BLACK);
		floorlb.setHorizontalAlignment(JLabel.CENTER);
		floorlb.setForeground(Color.YELLOW);
		floorlb.setFont(f1);
	}
	
	private void placeObject() {
		buttonPanel.setBounds(650, 140, 75, 320);
		
		// set button's location
		for(int i=7; i> 0; i--) {
			floorButton[i].setBounds(5, 5+35*(7-i), 30, 30);
		}
		for(int i=14; i>7 ; i--) {
			floorButton[i].setBounds(40, 5+35*(14-i), 30, 30);
		}
		floorButton[0].setBounds(22, 250, 30, 30);
		openButton.setBounds(5, 285, 30, 30);
		closeButton.setBounds(40, 285, 30, 30);
		
		// add buttons on button panel
		for(int i=0; i<floorButton.length; i++) {
			buttonPanel.add(floorButton[i]);
		}
		buttonPanel.add(openButton);
		buttonPanel.add(closeButton);
		
		floorlb.setBounds(320, 30, 150, 70);
		add(buttonPanel);
		add(floorlb);
		
		// floorButton[0].setSelected(false); 버튼 클릭 해제하는거!!
	}
	
	private void setActionListener() {
		for(int i=0; i<floorButton.length; i++) {
			floorButton[i].addActionListener(new ActionListener() {
				int i;
				public void actionPerformed(ActionEvent e) {
					if(elevator.getStatus()!=0&&i+1>elevator.getDestination()) {
			    		elevator.setCheck(false);
			    	}else if(elevator.getStatus()!=1&&i+1<elevator.getDestination()) {
			    		elevator.setCheck(false);
			    	}
					elevator.setDestination(i+1);
					elevator.getInButtonChecked()[i] = 1;
				}
				public ActionListener setParams(int i) {
					this.i = i;
					return this;
				}
			}.setParams(i));
		}
		
		openButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	elevator.setOpenSecond(6);
		    	if(elevator.getFloor()==Integer.parseInt(floorlb.getText())&&!elevator.isOpen()) { // 움직이는 중인지 이런거 추가해야할듯
		    		elevator.openDoor();
		    	}
			}
		});
		
		closeButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	elevator.setOpenSecond(1);
			}
		});
	}
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(elevatorDoorImg, (this.getWidth()-elevatorDoorImg.getWidth(null))/2, 180, this);
	}
}