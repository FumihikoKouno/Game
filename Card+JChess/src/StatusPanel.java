import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Card.Card;


public class StatusPanel extends JPanel{
	private Card card;
	public StatusPanel(){
		setPreferredSize(new Dimension(100,400));
	}
	public void setCard(Card c){
		card = c;
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(18,18,64,64);
		if(card != null){
			g.drawString("Active Skill",5,150);
			g.drawString(""+card.getActiveSkillDescription(), 10, 170);
			g.drawString("Passive Skill", 5, 250);
			g.drawString(""+card.getPassiveSkillDescription(), 10, 270);
		}
	}
}
