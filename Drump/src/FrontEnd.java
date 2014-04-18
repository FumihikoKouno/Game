import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import game.common.Data;
import game.zones.Field;
import communication.CommunicationSettingPanel;
import communication.SocketHandler;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


public class FrontEnd extends JFrame {
	public Field field;
	public CommunicationSettingPanel csp;
	public FrontEnd(){
		init();
	}
	public void init(){
		setTitle("Drump");
		csp = new CommunicationSettingPanel();
		add(csp);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		gameLoop();
	}
	
	public void update(){
		if(field == null){
			SocketHandler tmp = csp.getSocketHandler();
			if(tmp != null){
				JSplitPane game = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				field = new Field(tmp);
				JTextArea textArea = new JTextArea();
				textArea.setSize(new Dimension(Field.WIDTH, 200));
				textArea.setEnabled(false);
				textArea.setForeground(Color.BLACK);
				field.setTextArea(textArea);
				remove(csp);
				game.setLeftComponent(field);
				game.setRightComponent(textArea);
				add(game);
				pack();
			}
		}else{
			field.update();
//			field.repaint();
		}
	}
	
	public void gameLoop(){
		long SPF = 1000/Data.FPS;
		long time;
		while(true){
			try{
				time = System.currentTimeMillis();
				update();
				time = System.currentTimeMillis()-time;
				if(SPF>time) Thread.sleep(SPF-time);
			}catch(InterruptedException e){}
		}
	}
	public static void main(String[] args) {
		FrontEnd fe = new FrontEnd();
	}

}
