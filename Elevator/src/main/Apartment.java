package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import components.Elevator;
import panels.ElevatorOutPanel;
import panels.ElevatorInPanel;
import panels.ViewPanel;

public class Apartment {
	private JFrame frame;
	private JPanel mainPanel;
	private Elevator elevator;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Apartment apartment = new Apartment();
	}
	
	public Apartment() {
		init();
	}
	
	private void init() {
		frame = new JFrame();
		frame.setTitle("엘레베이터"); // 프로그램 이름 지정
		frame.setSize(1200,1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		elevator = new Elevator();
		
		ElevatorOutPanel pn1 = new ElevatorOutPanel(elevator);
		ElevatorInPanel pn2 = new ElevatorInPanel(elevator);
		ViewPanel pn3 = new ViewPanel(elevator);
		pn1.setLayout(null);
		pn2.setLayout(null);
		pn3.setLayout(null);
			
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout()); //레이아웃 생성자
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH; //x,y축 다채움

        gbc.weightx=0.2; // 가로 비율 2:1
        gbc.weighty=0.1; // 세로 비율 1:1
        gbc.gridx=0;  
        gbc.gridy=0; 
        mainPanel.add(pn1,gbc); // 0,0 위치에 패널 배치

        gbc.weighty=0.1; // 세로 비율 1:1
        gbc.gridx=0;  
        gbc.gridy=1; 
        mainPanel.add(pn2,gbc); // 0,1 위치에 패널 배치

        gbc.weightx=0.1; // 가로 비율 2:1
        gbc.gridx=1;  
        gbc.gridy=0;  
        gbc.gridheight = 2; // 세로로 두칸 차지하게
        mainPanel.add(pn3,gbc); // 1,0 위치에 패널 배치

        frame.add(mainPanel);
        frame.setVisible(true); // 창 보이게하기
		
	}
}
