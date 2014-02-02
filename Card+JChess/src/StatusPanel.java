import java.awt.Dimension;

import javax.swing.JPanel;

import Card.Card;


public class StatusPanel extends JPanel{
	private Card card;
	public StatusPanel(){
		setPreferredSize(new Dimension(50,50));
	}
	public void setCard(Card c){
		card = c;
	}
}
