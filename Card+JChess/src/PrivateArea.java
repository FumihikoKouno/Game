import java.awt.BorderLayout;

import javax.swing.JPanel;


public class PrivateArea extends JPanel{
	enum ELEMENT{
		NORTH,
		SOUTH,
	}
	
	private Hand hand;
	private Deck deck;
	
	private ELEMENT element;
	public PrivateArea(ELEMENT elem){
		setLayout(new BorderLayout());
		element = elem;
		switch(element){
		case SOUTH:
			hand = new Hand(Hand.ELEMENT.SOUTH);
			deck = new Deck(Deck.ELEMENT.SOUTH);
			add(deck,BorderLayout.EAST);
			break;
		case NORTH:
			hand = new Hand(Hand.ELEMENT.NORTH);
			deck = new Deck(Deck.ELEMENT.NORTH);
			add(deck,BorderLayout.WEST);
			break;
		default:
			break;
		}
		add(hand,BorderLayout.CENTER);
	}
}
