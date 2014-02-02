import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Common.Data;

public class FrontEnd extends JFrame{
	Field field = new Field();
	PrivateArea northArea = new PrivateArea(PrivateArea.ELEMENT.NORTH);
	PrivateArea southArea = new PrivateArea(PrivateArea.ELEMENT.SOUTH);
	StatusPanel statusPanel = new StatusPanel();

	public FrontEnd(){
		init();
	}
	private void init(){
		this.setLayout(new BorderLayout());
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(new BorderLayout());
		cardPanel.add(northArea,BorderLayout.NORTH);
		cardPanel.add(field,BorderLayout.CENTER);
		cardPanel.add(southArea,BorderLayout.SOUTH);
		add(cardPanel,BorderLayout.WEST);
		statusPanel.setBorder(new EtchedBorder());
		add(statusPanel,BorderLayout.EAST);
		pack();
	}
	
	private void run(){
		while(true){
			try{
				Thread.sleep(1000/Data.FPS);
				repaint();
			}catch(InterruptedException e){
				
			}
		}
	}
	
	public static void main(String[] args) {
		FrontEnd frontend = new FrontEnd();
		frontend.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frontend.setVisible(true);
		frontend.run();
	}
}
