import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import Card.Box;
import Card.Card;
import Common.Data;


public class Deck extends JPanel{
	private Box box;
	private Card[] deck = new Card[Data.DECK_SIZE];
	private int index;
	
	enum ELEMENT{
		NORTH,
		SOUTH,
	};
	private ELEMENT element;
	private Field field;
	private StatusPanel statusPanel;
	
	public Deck(ELEMENT elem){
		setPreferredSize(new Dimension(Data.BOX_SIZE<<1, Data.BOX_SIZE));
		element = elem;
		index = 0;
		switch(element){
		case SOUTH:
			box = new Box(Box.ELEMENT.SOUTH_DECK);
			break;
		case NORTH:
			box = new Box(Box.ELEMENT.NORTH_DECK);
			break;
		default:
			return;
		}
		initDeck();
		shuffle();
	}
	
	private void initDeck(){
		
	}
	
	private void shuffle(){
		Random rand = new Random();
		long seed = System.currentTimeMillis();
		rand.setSeed(seed);
		boolean[] shuffleNumber = new boolean[Data.DECK_SIZE];
		Card[] shuffledDeck = new Card[Data.DECK_SIZE];
		int num;
		for(int i = 0; i < Data.DECK_SIZE; i++){
			num = rand.nextInt(Data.DECK_SIZE);
			if(shuffleNumber[num]){
				while(shuffleNumber[num]){
					num = (num+1)%Data.DECK_SIZE;
				}
			}
			shuffledDeck[i] = deck[num];
			shuffleNumber[num] = true;
		}
		deck = shuffledDeck;
	}
	
	public Card next(){
		Card ret;
		if(index < Data.DECK_SIZE){
			ret = deck[index];
			index++;
			return ret;
		}
		return null;
	}
	
	public void setField(Field field){
		this.field = field;
	}
	
	public void paintComponent(Graphics g){
		switch(element){
		case SOUTH:
			box.draw(g, 1, 0);
			break;
		case NORTH:
			box.draw(g, 0, 0);
			break;
		default:
			break;
		}
	}

	public void setStatusPanel(StatusPanel pane) {
		statusPanel = pane;
	}
}
