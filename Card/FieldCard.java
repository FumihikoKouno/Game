import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class FieldCard extends Card implements Common{
	
	public FieldCard(int cardNumber){
		super(cardNumber);
	}
	
	private int max(int a, int b){
		if(a < b) return b;
		else return a;
	}
	
	private int abs(int x){
		if(x < 0) return -x;
		else return x;
	}
	
	public void draw(int turn, int field, Graphics g){
		if((field * turn > 0) || (abs(field) == 2 || abs(field) == 4)){
			g.drawImage(cardImage,IMAGE_X,IMAGE_Y,null);
			g.drawImage(elementImage,ELEMENT_X,ELEMENT_Y,
				ELEMENT_X + ELEMENT_SIZE, ELEMENT_Y + ELEMENT_SIZE,
				element * ELEMENT_SIZE, 0,
				element * ELEMENT_SIZE + ELEMENT_SIZE, ELEMENT_SIZE,
				null);
			g.setFont(font);
			g.setColor(Color.white);
			
			g.drawString(mSkillName, M_SKILL_NAME_X, M_SKILL_NAME_Y);
			g.drawString(aSkillName, A_SKILL_NAME_X, A_SKILL_NAME_Y);
			g.drawString(power+"",POWER_X,POWER_Y);
		}
		
	}
}