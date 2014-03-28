import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class Block extends JPanel {
	public static final int SIZE = 32;
	public int otiTime;
	public int attackedTime;
	public boolean fall;
	
	public Block(){
		setPreferredSize(new Dimension(SIZE+2,SIZE+2));
	}
	
	public int getOtiTime(){
		return otiTime>>1;
	}
	
	public void attacked(){
		attackedTime = 1;
	}
	
	public boolean falled(){
		return fall;
	}
	
	public void update(){
		if(attackedTime>0){
			otiTime++;
			if(otiTime >= SIZE){
				attackedTime=0;
				otiTime=0;
			}
		}
	}

}
