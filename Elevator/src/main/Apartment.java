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
		frame.setTitle("����������"); // ���α׷� �̸� ����
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
		mainPanel.setLayout(new GridBagLayout()); //���̾ƿ� ������
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH; //x,y�� ��ä��

        gbc.weightx=0.2; // ���� ���� 2:1
        gbc.weighty=0.1; // ���� ���� 1:1
        gbc.gridx=0;  
        gbc.gridy=0; 
        mainPanel.add(pn1,gbc); // 0,0 ��ġ�� �г� ��ġ

        gbc.weighty=0.1; // ���� ���� 1:1
        gbc.gridx=0;  
        gbc.gridy=1; 
        mainPanel.add(pn2,gbc); // 0,1 ��ġ�� �г� ��ġ

        gbc.weightx=0.1; // ���� ���� 2:1
        gbc.gridx=1;  
        gbc.gridy=0;  
        gbc.gridheight = 2; // ���η� ��ĭ �����ϰ�
        mainPanel.add(pn3,gbc); // 1,0 ��ġ�� �г� ��ġ

        frame.add(mainPanel);
        frame.setVisible(true); // â ���̰��ϱ�
		
	}
}
